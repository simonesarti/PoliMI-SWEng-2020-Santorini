package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.PlayerInfo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newFixedThreadPool(128);

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

    public void lobby(ServerSideConnection serverSideConnection, PlayerInfo playerInfo) {
    }

    public void deregisterConnection(ServerSideConnection serverSideConnection) {
    }
}

