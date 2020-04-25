package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.VirtualView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newFixedThreadPool(128);

    private List<PlayerConnection> twoPlayerWaitingList =new ArrayList<>();
    private List<PlayerConnection> threePlayerWaitingList =new ArrayList<>();
    private List<TwoPlayerGameConnection> twoPlayerGames = new ArrayList<>();
    private List<ThreePlayerGameConnection> threePlayerGames = new ArrayList<>();


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

            twoPlayerWaitingList.add(playerConnection);

            if(twoPlayerWaitingList.size()==2){

                ServerSideConnection c21 = twoPlayerWaitingList.get(0).getServerSideConnection();
                ServerSideConnection c22 = twoPlayerWaitingList.get(1).getServerSideConnection();

                Player player21=new Player(twoPlayerWaitingList.get(0).getPlayerInfo());
                Player player22=new Player(twoPlayerWaitingList.get(1).getPlayerInfo());

                VirtualView player21VirtualView=new VirtualView(player21,c21);
                VirtualView player22VirtualView=new VirtualView(player22,c22);

                Model model = new Model(2);
                Controller controller = new Controller(model);

                model.addObserver(player21VirtualView);
                model.addObserver(player22VirtualView);

                player21VirtualView.addObserver(controller);
                player22VirtualView.addObserver(controller);

                twoPlayerGames.add(new TwoPlayerGameConnection(c21,c22));

                twoPlayerWaitingList.clear();

            }

        }else{

            threePlayerWaitingList.add(playerConnection);

            if(threePlayerWaitingList.size()==3){

                ServerSideConnection c31 = threePlayerWaitingList.get(0).getServerSideConnection();
                ServerSideConnection c32 = threePlayerWaitingList.get(1).getServerSideConnection();
                ServerSideConnection c33 = threePlayerWaitingList.get(2).getServerSideConnection();

                Player player31 = new Player(threePlayerWaitingList.get(0).getPlayerInfo());
                Player player32 = new Player(threePlayerWaitingList.get(1).getPlayerInfo());
                Player player33 = new Player(threePlayerWaitingList.get(2).getPlayerInfo());

                VirtualView player31VirtualView = new VirtualView(player31,c31);
                VirtualView player32VirtualView = new VirtualView(player32,c32);
                VirtualView player33VirtualView = new VirtualView(player33,c33);

                Model model = new Model(3);
                Controller controller = new Controller(model);

                model.addObserver(player31VirtualView);
                model.addObserver(player32VirtualView);
                model.addObserver(player33VirtualView);

                player31VirtualView.addObserver(controller);
                player32VirtualView.addObserver(controller);
                player33VirtualView.addObserver(controller);

                threePlayerGames.add(new ThreePlayerGameConnection(c31,c32,c33));

                threePlayerWaitingList.clear();
            }
        }




    }

    public void deregisterConnection(ServerSideConnection serverSideConnection) {
    }
}

