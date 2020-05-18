package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.GameMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.InfoMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.PlayerInfoRequest;
import it.polimi.ingsw.model.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int PORT = 12345;
    private final ServerSocket serverSocket;
    private final ExecutorService executor = Executors.newFixedThreadPool(128);

    private final ArrayList<PlayerConnection> twoPlayerWaitingList =new ArrayList<>();
    private final ArrayList<PlayerConnection> threePlayerWaitingList =new ArrayList<>();
    private final ArrayList<TwoPlayerGameConnection> twoPlayerGames = new ArrayList<>();
    private final ArrayList<ThreePlayerGameConnection> threePlayerGames = new ArrayList<>();


    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    public void run(){
        while(true){
            try {
                Socket newSocket = serverSocket.accept();
                ServerSideConnection socketConnection = new ServerSideConnection(newSocket, this);
                executor.submit(socketConnection);
            } catch (IOException e) {
                System.out.println("ServerSideConnection Error!");
            }
        }
    }

    public synchronized void lobby(PlayerConnection playerConnection) {

        if(playerConnection.getPlayerInfo().getNumberOfPlayers()==2){

            if(nicknameAlreadyInUse(playerConnection.getPlayerInfo().getPlayerNickname(),twoPlayerWaitingList)){

                //if nickname already present, asks again
                playerConnection.getServerSideConnection().send(new PlayerInfoRequest(true));

            }else{
                twoPlayerWaitingList.add(playerConnection);

                if(twoPlayerWaitingList.size()==2){

                    ArrayList<ServerSideConnection> connections = new ArrayList<>();
                    ServerSideConnection c21 = twoPlayerWaitingList.get(0).getServerSideConnection();
                    ServerSideConnection c22 = twoPlayerWaitingList.get(1).getServerSideConnection();
                    connections.add(c21);
                    connections.add(c22);

                    ArrayList<Player> players= new ArrayList<>();
                    Player player21 = new Player(twoPlayerWaitingList.get(0).getPlayerInfo());
                    Player player22 = new Player(twoPlayerWaitingList.get(1).getPlayerInfo());
                    players.add(player21);
                    players.add(player22);

                    twoPlayerGames.add(new TwoPlayerGameConnection(c21,c22));
                    twoPlayerWaitingList.clear();


                    Controller controller=new Controller(players,connections);

                }

            }

        }else{

            if(nicknameAlreadyInUse(playerConnection.getPlayerInfo().getPlayerNickname(),threePlayerWaitingList)){

                //if nickname already present, asks again
                playerConnection.getServerSideConnection().send(new PlayerInfoRequest(true));

            }else{

                threePlayerWaitingList.add(playerConnection);

                if(threePlayerWaitingList.size()==3) {

                    ArrayList<ServerSideConnection> connections = new ArrayList<>();
                    ServerSideConnection c31 = threePlayerWaitingList.get(0).getServerSideConnection();
                    ServerSideConnection c32 = threePlayerWaitingList.get(1).getServerSideConnection();
                    ServerSideConnection c33 = threePlayerWaitingList.get(2).getServerSideConnection();
                    connections.add(c31);
                    connections.add(c32);
                    connections.add(c33);

                    ArrayList<Player> players = new ArrayList<>();
                    Player player31 = new Player(threePlayerWaitingList.get(0).getPlayerInfo());
                    Player player32 = new Player(threePlayerWaitingList.get(1).getPlayerInfo());
                    Player player33 = new Player(threePlayerWaitingList.get(2).getPlayerInfo());
                    players.add(player31);
                    players.add(player32);
                    players.add(player33);

                    threePlayerGames.add(new ThreePlayerGameConnection(c31, c32, c33));
                    threePlayerWaitingList.clear();

                    Controller controller = new Controller(players, connections);

                }
            }
        }

    }

    public synchronized void unregisterConnection(ServerSideConnection connection) {

        int index;

        index=getWaitingGroupIndex2(connection);
        if(index!=-1){
            connection.send(new InfoMessage(GameMessage.abandonedLobby));
            twoPlayerWaitingList.remove(index);
            return;
        }

        index=getWaitingGroupIndex3(connection);
        if(index!=-1){
            connection.send(new InfoMessage(GameMessage.abandonedLobby));
            threePlayerWaitingList.remove(index);
            return;
        }


        index=getMatchGroupIndex2(connection);
        if(index!=-1){
            twoPlayerGames.get(index).getConnection(0).notInUse();
            twoPlayerGames.get(index).getConnection(1).notInUse();
            twoPlayerGames.remove(index);
            return;
        }

        index=getMatchGroupIndex3(connection);
        if(index!=-1){
            index= getMatchGroupIndex3(connection);
            threePlayerGames.get(index).getConnection(0).notInUse();
            threePlayerGames.get(index).getConnection(1).notInUse();
            threePlayerGames.get(index).getConnection(2).notInUse();
            threePlayerGames.remove(index);
            return;
        }

        throw new IllegalArgumentException("Connection not found in any waiting list or match list");

    }


    private int getWaitingGroupIndex2(ServerSideConnection connection){

        for(int i=0;i<twoPlayerWaitingList.size();i++){
            if(twoPlayerWaitingList.get(i).getServerSideConnection().equals(connection)){
                return i;
            }
        }
        return -1;
    }
    private int getWaitingGroupIndex3(ServerSideConnection connection){

        for(int i=0;i<threePlayerWaitingList.size();i++){
            if(threePlayerWaitingList.get(i).getServerSideConnection().equals(connection)){
                return i;
            }
        }
        return -1;
    }
    private int getMatchGroupIndex2(ServerSideConnection connection) {

        for (int i = 0; i < twoPlayerGames.size(); i++) {
            if (twoPlayerGames.get(i).getConnection(0).equals(connection) ||
                    twoPlayerGames.get(i).getConnection(1).equals(connection)) {
                return i;
            }
        }
        return -1;
    }
    private int getMatchGroupIndex3(ServerSideConnection connection) {

        for(int i=0;i<threePlayerGames.size();i++){
            if(threePlayerGames.get(i).getConnection(0).equals(connection) ||
               threePlayerGames.get(i).getConnection(1).equals(connection) ||
               threePlayerGames.get(i).getConnection(2).equals(connection)) {
                return i;
            }
        }
        return -1;
    }

    private boolean nicknameAlreadyInUse(String nickname, ArrayList<PlayerConnection> waitingList){

        if(waitingList.isEmpty()){
            return false;
        }else{

            for(PlayerConnection opponent : waitingList){
                if(nickname.equals(opponent.getPlayerInfo().getPlayerNickname())){
                    return true;
                }
            }
        }
        return false;
    }


    //TESTING METHOD
    public void closeServerSocket() throws IOException {
        this.serverSocket.close();
    }

    public List<PlayerConnection> getTwoPlayerWaitingList() {
        return twoPlayerWaitingList;
    }

    public List<PlayerConnection> getThreePlayerWaitingList() {
        return threePlayerWaitingList;
    }

    public List<TwoPlayerGameConnection> getTwoPlayerGames() {
        return twoPlayerGames;
    }

    public List<ThreePlayerGameConnection> getThreePlayerGames() {
        return threePlayerGames;
    }
}

