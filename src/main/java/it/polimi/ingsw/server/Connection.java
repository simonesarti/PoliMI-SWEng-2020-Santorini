package it.polimi.ingsw.server;

import it.polimi.ingsw.observe.Observable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Connection extends Observable<String> implements Runnable {


    private Socket socket;
    private ObjectOutputStream out;
    private Server server;

    private boolean active = true;

    public Connection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    private synchronized boolean isActive(){
        return active;
    }

    private synchronized void send(Object message) {
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch(IOException e){
            System.err.println(e.getMessage());
        }

    }

    public synchronized void closeConnection() {
        send("Connection closed!");
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        active = false;
    }

    public void asyncSend(final Object message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();
    }





    @Override
    public void run() {
        Scanner in;
        String name;
        try{
            in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            send("Welcome!\nWhat is your name?");
            String read = in.nextLine();
            name = read;
            System.out.println("NAME: "+name);
            //server.lobby(this, name);                                      TODO aggiungere lobby(Connection c, String name) e deRegisterConnection(Connection c)
            /*
            while(isActive()){

                read = in.nextLine();
                System.out.println(read);
                //notify(read);
            }
            */
            read = in.nextLine();
            System.out.println(read);

        } catch (IOException | NoSuchElementException e) {
            System.err.println("Error!" + e.getMessage());
        }finally{

            closeConnection(); //    TODO sostituirla con una close() che chiami questa e inoltre tolga la registrazione al client
        }

    }






}
