package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.GameToPlayerMessages.Others.GameMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.InfoMessage;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.DataMessage;
import it.polimi.ingsw.messages.PlayerInfo;
import it.polimi.ingsw.observe.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerSideConnection extends Observable<DataMessage> implements Runnable {

    private final Server server;
    private final Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private boolean active;
    private boolean inUse;

    public ServerSideConnection(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
        active=true;
        inUse=true;
    }

    private synchronized boolean isActive(){
        return active;
    }

    public void deactivate(){
        active=false;
    }

    private synchronized boolean isInUse(){return inUse;}

    public void notInUse(){inUse=false;}

    public synchronized void send(Object message){

        try {
            outputStream.reset();
            outputStream.writeObject(message);
            outputStream.flush();
        } catch(IOException e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

    }

    private void close() {
        server.unregisterConnection(this);
        closeConnection();
    }

    public synchronized void closeConnection() {

        try {
            outputStream.close();
            inputStream.close();
        }catch (IOException e){
            e.printStackTrace();
            System.err.println("Error while closing the streams!");
        }

        try{
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while closing socket!");
        }
    }

    @Override
    public void run() {

        try{
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());

            //sends first message
            send(new InfoMessage(GameMessage.welcome));


            //reads player info and sends them to the server
            PlayerInfo playerInfo = (PlayerInfo) inputStream.readObject();
            server.lobby(new PlayerConnection(playerInfo,this));

            //continues to read input commands until the connections stay active, and notifies them to the virtualView

            while(isActive() && isInUse()){
                DataMessage dataMessage=(DataMessage)inputStream.readObject();
                notify(dataMessage);
            }


            //serialization adds ClassNotFoundException
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error, entered run Catch in ServerSideConnection:  " + e.getMessage());


        }finally{

            if(!isInUse()){
                closeConnection();
            }else{
                //when someone wins (active=false) or with and exception
                close();
            }

        }
    }

}