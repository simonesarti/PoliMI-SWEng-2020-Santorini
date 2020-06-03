package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.GameToPlayerMessages.Others.CloseConnectionMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.PlayerInfoRequest;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.DataMessage;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.PlayerInfo;
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
    private boolean alreadyEliminated;
    private boolean terminatedWithException;

    /**
     * @param socket is the socket used
     * @param server is the server object
     */
    public ServerSideConnection(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
        active=true;
        inUse=true;
        alreadyEliminated=false;
        terminatedWithException=false;
    }

    //getter

    private synchronized boolean isActive(){
        return active;
    }

    private synchronized boolean isInUse(){return inUse;}

    private synchronized boolean isAlreadyEliminated(){return alreadyEliminated;}

    private synchronized boolean hasTerminatedWithException(){return terminatedWithException;}


    //setter
    private synchronized void setNotActive(){active=false;}

    public synchronized void notInUse(){inUse=false;}

    public synchronized void markAsEliminated(){
        alreadyEliminated=true;
    }

    private synchronized void setTerminatedWithException(){terminatedWithException=true;}

    /**
     * method used to send an object to the client through socket. Sends only if the connection is still active
     * @param message is the object to send
     */
    public synchronized void send(Object message){

        if(isActive()){
            try {
                outputStream.reset();
                outputStream.writeObject(message);
                outputStream.flush();
            } catch (IOException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }

    }

    /**
     * unregister the match and closes the connection of the player for whom this method was called
     */
    public synchronized void closeMatch() {
        server.unregisterConnection(this);
        closeConnection();
    }

    /**
     * closes the streams and then the socket, only when the connection is still active and the client did not
     * terminated by exception (in that case the connection is already closed)
     */
    public synchronized void closeConnection() {

        if (!hasTerminatedWithException() && isActive()) {
            send(new CloseConnectionMessage());

            try {
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error while closing the streams!");
            }
        }

        try{
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while closing socket!");
        }

        setNotActive();
    }

    /**
     * run method of the thread, it keeps listening to messages recived by the client, and forwards them to the
     * virtualview of the player (except when the message contains the info used to add a player to the lobby).
     * Once the loop is broken, the connection gets closed
     */
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
            setTerminatedWithException();
        }finally{

            if(!isInUse() || isAlreadyEliminated()){
                closeConnection();
            }else{
                //when someone wins or with and exception
                closeMatch();
            }

        }
    }

}