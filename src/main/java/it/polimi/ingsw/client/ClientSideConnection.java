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
public class ClientSideConnection extends Observable<Object>{

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

    public void run() throws IOException{

        try {

            socket = new Socket(ip, port);
            System.out.println("Connection to established");
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());

            //ServerSideConnection sent welcome message

            //reads welcome message
            Object welcomeMessage=inputStream.readObject();
            notify(welcomeMessage);

            //TODO visto che dipende da CLI o GUI. possiamo averla come invocazione a seguito della notify(welcomeMessage)
            //sends player info
            asyncSend(createPlayerInfo());


            //now it keeps receiving messages while the connections stay active

            Thread t0 = asyncReadFromSocket(inputStream,this);
            t0.join();


        } catch (NoSuchElementException | ClassNotFoundException | InterruptedException e) {

            //TODO funziona settare a false nella catch? Copiato da esempio TrisDistr..
            e.printStackTrace();
            System.err.println("Error!" + e.getMessage());
            System.out.println("Connection closed from the client side");

        }finally{

            closeConnection();

        }
    }


    //TODO va bene in generale (se testata e funziona), forse metterla in una classe d'appoggio per
    //TODO poterla condividere tra CLI e GUI?
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

    //TODO va bene per la CLI
    public PlayerInfo createPlayerInfo(){

        Scanner stdin = new Scanner(System.in);

        System.out.println("What's your nickname?");
        String nickname = stdin.nextLine();

        int day=0;
        int month=0;
        int year=0;
        boolean validDate=false;
        do{
            try {
                System.out.println("Insert birthday day:");
                day = Integer.parseInt(stdin.nextLine());
                System.out.println("Insert birthday month:");
                month = Integer.parseInt(stdin.nextLine());
                System.out.println("Insert birthday year:");
                year = Integer.parseInt(stdin.nextLine());

                String dateString = day +"-"+month+"-"+year;
                validDate=isDateValid(dateString);
                if(!validDate){System.out.print("Not valid, try again");}

            } catch (NumberFormatException e) {
                //TODO non posso stampare la stacktrace su GUI, e in realtà non la voglio vedere nenache su CLI
                e.printStackTrace();
                System.out.print("Not a number, try again");
            }
        }while(!validDate);

        //need to create a new Calendar object with birthdayString data
        int numberOfPlayers = 0;
        boolean validNumberOfPlayers=false;
        do{
            try {
                System.out.println("Insert number of players:");
                numberOfPlayers = Integer.parseInt(stdin.nextLine());
                if(numberOfPlayers!=2 && numberOfPlayers!=3){
                    System.out.print("Not valid, try again");
                }else{
                    validNumberOfPlayers=true;
                }

            } catch (NumberFormatException e) {
                //TODO non posso stampare la stacktrace su GUI, e in realtà non la voglio vedere nenache su CLI
                System.out.print("Not a number, try again");
                e.printStackTrace();
            }
        }while(!validNumberOfPlayers);

        stdin.close();
        return (new PlayerInfo(nickname,new GregorianCalendar(year,month-1,day),numberOfPlayers));
    }


}
