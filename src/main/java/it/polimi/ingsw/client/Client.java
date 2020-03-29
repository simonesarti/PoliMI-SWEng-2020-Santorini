package it.polimi.ingsw.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {

    private String ip;
    private int port;



    public Client(String ip, int port){

        this.ip = ip;
        this. port = port;

    }


    public void run() throws IOException {

        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");
        //ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        //Scanner stdin = new Scanner(System.in);
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());

        socketOut.println("Giuseppe");
        socketOut.println("Voglio mandare questa frase");
        socketOut.flush();

        //socketOut.close(); TODO imparare a chiudere connessione




    }









}
