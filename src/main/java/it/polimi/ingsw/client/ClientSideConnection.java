package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.GameToPlayerMessages.NewBoardStateMessage;
import it.polimi.ingsw.messages.InfoMessage;
import it.polimi.ingsw.messages.PlayerInfo;
import it.polimi.ingsw.observe.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
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

            readAndSendPlayerInfo();

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////

            //now it keeps receiving messages while the connections stay active
            while (isActive()) {

                notify(inputStream.readObject());
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



    public boolean isDateValid(String date){

        String DATE_FORMAT = "dd-MM-yyyy";

        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

    }

    public void readAndSendPlayerInfo(){



        Scanner stdin = new Scanner(System.in);

        System.out.println("What's your nickname?");
        String nickname = stdin.nextLine();


        int month = 0;
        int day = 0;
        int year = 0;

        while (true) {
            try {
                System.out.println("Insert birthday day:");
                day = Integer.parseInt(stdin.nextLine());
                System.out.println("Insert birthday month:");
                month = Integer.parseInt(stdin.nextLine());
                System.out.println("Insert birthday year:");
                year = Integer.parseInt(stdin.nextLine());

                String dateString = day +"-"+month+"-"+year;
                if(isDateValid(dateString)){
                    break; // if no exceptions breaks out of loop
                }
                System.out.print("Not valid, try again");

            } catch (NumberFormatException e) {
                e.printStackTrace();
                System.out.print("Not a number, try again");
            }
        }
        //need to create a new Calendar object with birthdayString data
        int numberOfPlayers = 0;
        while (true) {
            try {
                System.out.println("Insert number of players:");
                numberOfPlayers = Integer.parseInt(stdin.nextLine());
                if(numberOfPlayers!=2 && numberOfPlayers!=3){
                    break;
                }
                System.out.print("Not valid, try again");
            } catch (NumberFormatException e) {
                System.out.print("Not a number, try again");
                e.printStackTrace();
            }
        }

        //sending PlayerInfo msg to serverSideConnection
        asyncSend(new PlayerInfo(nickname,new GregorianCalendar(month,day,year),numberOfPlayers));
        stdin.close();
    }

    public synchronized boolean isActive(){
        return active;
    }
    public synchronized void setActive(boolean active){
        this.active = active;
    }
}
