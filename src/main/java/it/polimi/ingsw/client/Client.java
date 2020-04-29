package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.GameToPlayerMessages.NotifyMessages;
import it.polimi.ingsw.messages.InfoMessage;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.DataMessage;
import it.polimi.ingsw.observe.Observer;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Receives NotifyMessages from connection
 */
public class Client implements Observer<DataMessage>{

    private String ip;
    private int port;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private boolean active;
    private View view;
    Socket socket;


    public Client(String ip, int port){
        this.ip = ip;
        this. port = port;
        active=true;

    }

    /**
     * Reads NotifyMessages from Socket and calls View's method(s?)
     * @param socketIn
     * @return
     */
    public Thread asyncReadFromSocket(final ObjectInputStream socketIn){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        Object inputObject = socketIn.readObject();
                        if(inputObject instanceof NotifyMessages){
                            System.out.println("Notify message arrived to client!");
                        } else if (inputObject instanceof InfoMessage){
                            System.out.println("InfoMessage arrived to client!");
                        } else {
                            throw new IllegalArgumentException();
                        }
                    }
                } catch (Exception e){
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    public void writeToSocket(Object message) throws IOException {

        outputStream.reset();
        outputStream.writeObject(message);
        outputStream.flush();

    }


    public void run() throws IOException{

        Socket socket = new Socket(ip, port);
        System.out.println("connection to established");
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        //TODO resto
    }


    @Override
    public void update(DataMessage message) {

        //TODO Calls writeToSocket

    }

    public synchronized boolean isActive(){
        return active;
    }
    public synchronized void setActive(boolean active){
        this.active = active;
    }
}
