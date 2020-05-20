package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.GameToPlayerMessages.Others.PlayerInfoRequest;
import it.polimi.ingsw.messages.PlayerInfo;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.DataMessage;
import it.polimi.ingsw.observe.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ServerSideConnection extends Observable<DataMessage> implements Runnable {

    private final Server server;
    private final Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private boolean active;
    private boolean inUse;
    private boolean alreadyEliminated;

    public ServerSideConnection(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
        active=true;
        inUse=true;
        alreadyEliminated=false;
    }

    public synchronized boolean isActive(){
        return active;
    }

    public void deactivate(){
        active=false;
    }

    public synchronized boolean isInUse(){return inUse;}

    public void notInUse(){inUse=false;}

    public synchronized boolean isAlreadyEliminated(){return alreadyEliminated;}

    public void markAsEliminated(){
        alreadyEliminated=true;
    }

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

            socket.setKeepAlive(true);

            //sends first message
            send(new PlayerInfoRequest(false));

            while(isActive() && isInUse()){

                //until the connections stay active
                Object message=inputStream.readObject();

                //reads player info and sends them to the server
                if(message instanceof PlayerInfo){
                    server.lobby(new PlayerConnection((PlayerInfo)message,this));
                }
                //read input commands, and notifies them to the virtualView
                else{
                    notify((DataMessage)message);
                }

            }


            //serialization adds ClassNotFoundException
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error, entered run Catch in ServerSideConnection:  " + e.getMessage());
            e.printStackTrace();

        }finally{

            if(!isInUse() || isAlreadyEliminated()){
                closeConnection();
            }else{
                //when someone wins (active=false) or with and exception
                close();
            }

        }
    }

}