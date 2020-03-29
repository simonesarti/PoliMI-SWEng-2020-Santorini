package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientApp {



    public static void main(String[] args) throws IOException{

        Client client;

        try {
            client = new Client("127.0.0.1",12345);
            client.run();

        } catch (IOException e) {
            System.err.println("Errore inizializzazione client " + e.getMessage() + "!");
        }




    }




}
