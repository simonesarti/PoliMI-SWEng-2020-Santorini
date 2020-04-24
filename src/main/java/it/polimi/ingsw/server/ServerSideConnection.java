package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.GameMessage;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.PlayerInfo;
import it.polimi.ingsw.messages.PlayerToGameMessages.PlayerMessage;
import it.polimi.ingsw.observe.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;

public class ServerSideConnection extends Observable<PlayerMessage> implements Runnable {

    private Server server;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private boolean active = true;

    public ServerSideConnection(Socket socket, Server server) throws IOException {
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

    //TODO VERIFICARE VADA BENE ANCHE PER OGGETTI SERIALIZZABILI
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

        ObjectInputStream inputStream;


        try{
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            send(GameMessage.welcome);

            PlayerInfo playerInfo = (PlayerInfo) inputStream.readObject();

            server.lobby(this, playerInfo);

            //continues to read inputs

            while(isActive()){

                notify(//playerMessage
                );
            }

            //serialization adds ClessNotFoundException
        } catch (IOException | NoSuchElementException | ClassNotFoundException e) {
            System.err.println("Error!" + e.getMessage());
        }finally{
            close();
       }
    }

}
