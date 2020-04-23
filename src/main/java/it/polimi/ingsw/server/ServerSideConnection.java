package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.GameMessage;
import it.polimi.ingsw.messages.PlayerToGameMessages.PlayerMessage;
import it.polimi.ingsw.observe.Observable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ServerSideConnection extends Observable<PlayerMessage> implements Runnable {

    private Socket socket;
    private ObjectOutputStream outputStream;
    private Server server;
    private boolean active = true;

    public ServerSideConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    private synchronized boolean isActive(){
        return active;
    }

    public void asyncSend(final Object message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();
    }

    //TODO VERIFICARE VADA BENE ANCHE PER OGGETTI SERIALIZABILI
    private synchronized void send(Object message) {
        try {
            outputStream.reset();
            outputStream.writeObject(message);
            outputStream.flush();
        } catch(IOException e){
            System.err.println(e.getMessage());
        }

    }

    private void close() {
        closeConnection();
        System.out.println("Deregistering client...");
 //     server.deregisterConnection(this);
        System.out.println("client successfully deregistered!");
    }

    public synchronized void closeConnection() {
        send("Connection closed from server side");
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error while closing socket!");
        }
        active = false;
    }


    //TODO SERIALIZZAZIONE SIA A INVIARE CHE DESERIALIZZAZIONE IN RICEZIONE DA FARE

    @Override
    public void run() {
/*
        Scanner in;
        try{
            in = new Scanner(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            send(GameMessage.welcome);

            //receive serialized PlayerInfoMessage
            //TODO guardare come si deserializza

            server.lobby(this,//PlayerInfo);

            //continues to read inputs
            while(isActive()){
                //TODO lettura input e deserializzazione
                notify(//PlayerMessage);
            }

        } catch (IOException | NoSuchElementException e) {
            System.err.println("Error!" + e.getMessage());
        }finally{
            close();
       }
       */
    }

}
