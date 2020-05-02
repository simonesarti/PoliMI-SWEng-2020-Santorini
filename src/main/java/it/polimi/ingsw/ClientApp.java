package it.polimi.ingsw;

import it.polimi.ingsw.client.ClientSideConnection;
import java.io.IOException;

public class ClientApp {

    public static void main(String[] args){

        ClientSideConnection clientSideConnection =new ClientSideConnection("127.0.0.1",12345);

        //ORDINE:
        //Istanzio Client
        //client.run (verranno chieste info giocatore)
        //Chiedere gui o cli a utente
        //istanziare View (quindi o gui o cli) passandole il client nel costruttore
/*
        try {
            clientSideConnection.run();
        } catch (IOException e) {
            System.err.println("An error occurred while the client was running" + e.getMessage());
            e.printStackTrace();
        }

*/


    }




}
