package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.GameToPlayerMessages.NewBoardStateMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.NotifyMessages;
import it.polimi.ingsw.messages.InfoMessage;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.DataMessage;
import it.polimi.ingsw.observe.Observable;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
//TODO Il messaggio PlayerInfo lo inviamo nella fase di creazione del Client. Sempre in questa fase facciamo scegliere Cli o Gui e quindi istanziamo in base alla scelta

/**
 * Makes a receiver-Thread and is linked to a ServerSideConnection via socket
 */
public class Client extends Observable<Object> {

    private String ip;
    private int port;
    private ObjectOutputStream outputStream;
    private boolean active;



    public Client(String ip, int port){
        this.ip = ip;
        this. port = port;
        active=true;

    }

    /**
     * Reads Messages from Socket and calls View's method(s?)
     * @param socketIn
     * @return
     */
    public Thread asyncReadFromSocket(final ObjectInputStream socketIn, Client thisClient){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        Object inputObject = socketIn.readObject();
                        thisClient.notify(inputObject);

                        if(inputObject instanceof NewBoardStateMessage){
                            System.out.println("NewBoardStateMessage message arrived to client!");
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
        System.out.println("Connection to established");
        outputStream = new ObjectOutputStream(socket.getOutputStream());

        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

        try{
            Thread t0 = asyncReadFromSocket(inputStream, this);
            t0.join();
        } catch(InterruptedException | NoSuchElementException e){
            System.out.println("Connection closed from the client side");
        } finally {

            inputStream.close();
            outputStream.close();
            socket.close();
        }
    }


    public synchronized boolean isActive(){
        return active;
    }
    public synchronized void setActive(boolean active){
        this.active = active;
    }
}
