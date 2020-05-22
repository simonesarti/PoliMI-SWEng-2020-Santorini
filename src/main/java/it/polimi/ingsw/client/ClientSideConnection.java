package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.GameToPlayerMessages.Others.CloseConnectionMessage;
import it.polimi.ingsw.observe.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 * Is linked to a ServerSideConnection via socket. Receives messages and has async/sync send method
 */
public class ClientSideConnection extends Observable<Object> implements Runnable{

    private final String ip;
    private final int port;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    Socket socket;
    private boolean active;

    /**
     *
     * @param ip is the client ip address
     * @param port is the port on which client and server will communicate
     */
    public ClientSideConnection(String ip, int port){
        this.ip = ip;
        this. port = port;
        active=true;
    }

    /**
     * @return the state of the parameter Active
     */
    public synchronized boolean isActive(){
        return active;
    }

    /**
     * @param active is a flag which indicated if the client is active (running) or not
     */
    public synchronized void setActive(boolean active){
        this.active = active;
    }

    /**
     * this method sends the message given to the server through the socket, as long as the connection is active
     * @param message is the message object to be sent through the socket
     */
    public synchronized void send(Object message) {
        if(active){
            try {
                outputStream.reset();
                outputStream.writeObject(message);
                outputStream.flush();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

    }

    /**
     * this method closes the streams and the socket
     */
    public synchronized void closeConnection(){

        try {
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
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
    public void run(){

        try {

            socket = new Socket(ip, port);

            System.out.println("Connection to established");

            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());

            //now it keeps receiving messages while the connection stays active
            while (isActive()) {
                Object inputObject = inputStream.readObject();

                if(inputObject instanceof CloseConnectionMessage){
                    setActive(false);
                }else {
                    notify(inputObject);
                }

            }

        } catch ( Exception e) {

            System.err.println("Error!" + e.getMessage());
            e.printStackTrace();
            System.out.println("Connection closed from the client side");
            setActive(false);

        }finally{

            closeConnection();

        }
    }


//old version
/*
    public Thread asyncReadFromSocket(final ObjectInputStream socketIn, ClientSideConnection thisClientSideConnection){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        Object inputObject = socketIn.readObject();
                        thisClientSideConnection.notify(inputObject);

                    }
                } catch (Exception e){
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }
*/





}
