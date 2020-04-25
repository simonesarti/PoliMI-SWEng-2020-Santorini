package it.polimi.ingsw.client;

import it.polimi.ingsw.observe.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client extends Observable<Object> {

    private String ip;
    private int port;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private boolean active;

    public Client(String ip, int port){
        this.ip = ip;
        this. port = port;
        active=true;
    }

    public void run() throws IOException {

        Socket socket = new Socket(ip, port);
        System.out.println("connection to established");
        //TODO resto
    }



}
