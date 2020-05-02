package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.GameToPlayerMessages.NewBoardStateMessage;
import it.polimi.ingsw.messages.InfoMessage;
import it.polimi.ingsw.messages.PlayerInfo;
import it.polimi.ingsw.observe.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.Scanner;
//TODO Il messaggio PlayerInfo lo inviamo nella fase di creazione del Client. Sempre in questa fase facciamo scegliere Cli o Gui e quindi istanziamo in base alla scelta

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


    /* HO INSERITO LA RICEZIONE DEI MESSAGGI NEL METODO RUN (UGUALE A ServerSideConnection). Questo lo tengo per sicurezza
    public Thread asyncReadFromSocket(final ObjectInputStream socketIn, ClientSideConnection thisClientSideConnection){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        Object inputObject = socketIn.readObject();
                        thisClientSideConnection.notify(inputObject);

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
 */

    public synchronized void closeConnection(){

        try {
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            socket.close();
        } catch (IOException e) {
            System.err.println("Error while closing socket!");
        }


    }
    public void asyncSend(final Object message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();

    }

    private synchronized void send(Object message) {
        try {
            outputStream.reset();
            outputStream.writeObject(message);
            outputStream.flush();
        } catch(IOException e){
            System.err.println(e.getMessage());
        }

    }


    public void run() {

        try {

            socket = new Socket(ip, port);
            System.out.println("Connection to established");
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            //ServerSideConnection sent welcome message

            ////////////////////////////////this clientSideConnection sends player info/////////////////////////////////

            //TODO mancano tutti gli errori se sbaglia a inserire un dato

            Scanner stdin = new Scanner(System.in);

            System.out.println("What's your nickname?");
            String nickname = stdin.nextLine();

            System.out.println("Insert birthday:");
            String birthdayString = stdin.nextLine();

            //need to create a new Calendar object with birthdayString data
            System.out.println("Insert number of players:");
            int numberOfPlayers = Integer.parseInt(stdin.nextLine());

            //sending PlayerInfo msg to serverSideConnection
            //asyncSend(new PlayerInfo(nickname,birthday,numberOfPlayers));

            stdin.close();

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////

            //now it keeps receiving messages while the connections stay active
            while (isActive()) {
                //probabilmente è inutile castare a Object ma per ora lo tengo per sicurezza
                notify((Object)inputStream.readObject());
            }
        } catch (IOException | NoSuchElementException | ClassNotFoundException e) {

            //TODO funziona settare a false nella catch? Copiato da esempio TrisDistr..

            //System.err.println("Error!" + e.getMessage());
            System.out.println("Connection closed from the client side");
            setActive(false);

        }finally{

            closeConnection();

        }
    }





    public synchronized boolean isActive(){
        return active;
    }
    public synchronized void setActive(boolean active){
        this.active = active;
    }
}
