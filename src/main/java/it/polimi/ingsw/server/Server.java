package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
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

    /**
     * adds a playerConnection in a twoPlayerWaitingList or a threePlayerWaitingList
     * if a waiting-list is full it adds a twoPlayerGame/threePlayerGame to a list of matches
     * if a waiting list is full it also creates the Controller, starting the match
     * @param playerConnection contains the player info and his connection
     */
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

    /**
     * if a player's serverSideConnection gets unregistered, inUse becomes false and his
     * opponents' connections get closed
     * @param connection is the connection to search in the waiting lists or game lists
     */
    public synchronized void unregisterConnection(ServerSideConnection connection) {

        int index;

        index=getWaitingGroupIndex2(connection);
        if(index!=-1){
            twoPlayerWaitingList.remove(index);
            return;
        }

        index=getWaitingGroupIndex3(connection);
        if(index!=-1){
            threePlayerWaitingList.remove(index);
            return;
        }


        index=getMatchGroupIndex2(connection);
        if(index!=-1){

            for(int i=0;i<2;i++){
                twoPlayerGames.get(index).getConnection(i).notInUse();
                //closes opponent's connection
                if(twoPlayerGames.get(index).getConnection(i)!=connection){
                    twoPlayerGames.get(index).getConnection(i).closeConnection();
                }
            }

            twoPlayerGames.remove(index);
            return;
        }

        index=getMatchGroupIndex3(connection);
        if(index!=-1){


            for(int i=0;i<3;i++){
                threePlayerGames.get(index).getConnection(i).notInUse();
                //closes opponents' connections
                if(threePlayerGames.get(index).getConnection(i)!=connection){
                    threePlayerGames.get(index).getConnection(i).closeConnection();
                }
            }

            threePlayerGames.remove(index);
        }

        //else:
        //still in initial phase, just let it turn off

    }

    /**
     * returns connection's position's index in the twoPlayerWaitingList
     * @param connection is the connection to search
     * @return the index in the waiting list
     */
    private int getWaitingGroupIndex2(ServerSideConnection connection){

        for(int i=0;i<twoPlayerWaitingList.size();i++){
            if(twoPlayerWaitingList.get(i).getServerSideConnection().equals(connection)){
                return i;
            }
        }
        return -1;
    }

    /**
     * returns connection's position's index in the threePlayerWaitingList
     * @param connection is the connection to search
     * @return the index in the waiting list
     */
    private int getWaitingGroupIndex3(ServerSideConnection connection){

        for(int i=0;i<threePlayerWaitingList.size();i++){
            if(threePlayerWaitingList.get(i).getServerSideConnection().equals(connection)){
                return i;
            }
        }
        return -1;
    }

    /**
     * returns connection's position's index in the twoPlayerGames list
     * @param connection is the connection to search
     * @return the index in the game list
     */
    private int getMatchGroupIndex2(ServerSideConnection connection) {

        for (int i = 0; i < twoPlayerGames.size(); i++) {
            if (twoPlayerGames.get(i).getConnection(0).equals(connection) ||
                    twoPlayerGames.get(i).getConnection(1).equals(connection)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * returns connection's position's index in the threePlayerGames list
     * @param connection is the connection to search
     * @return the index in the game list
     */
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

    /**
     * returns true if a nickname is already used by a player in a waiting list
     * @param nickname to check
     * @param waitingList to search in
     * @return a boolean to say if the nickname was found
     */
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

