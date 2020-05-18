package it.polimi.ingsw.client;

import it.polimi.ingsw.observe.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 * Is linked to a ServerSideConnection via socket. Receives messages and has async/sync send method
 */
public class ClientSideConnection extends Observable<Object> implements Runnable{

    private String ip;
    private int port;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    Socket socket;
    private boolean active;



    public ClientSideConnection(String ip, int port){
        this.ip = ip;
        this. port = port;
        active=true;
    }

    public synchronized boolean isActive(){
        return active;
    }
    public synchronized void setActive(boolean active){
        this.active = active;
    }

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

    /*
    public void asyncSend(final Object message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();

    }

     */


    public synchronized void send(Object message) {
        try {
            outputStream.reset();
            outputStream.writeObject(message);
            outputStream.flush();
        } catch(IOException e){
            System.err.println(e.getMessage());
        }

    }

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
                notify(inputObject);

            }



        } catch ( Exception e) {

            //TODO nell'esempio del TrisMvc toglie le print di errore e dello stacktrace.
            System.err.println("Error!" + e.getMessage());
            e.printStackTrace();
            System.out.println("Connection closed from the client side");
            setActive(false);

        }finally{

            closeConnection();

        }
    }









}
