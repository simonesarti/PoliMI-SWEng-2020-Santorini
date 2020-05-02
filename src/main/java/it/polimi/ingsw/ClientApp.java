package it.polimi.ingsw;

import it.polimi.ingsw.client.ClientSideConnection;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.cli.Cli;

import java.io.IOException;

public class ClientApp {

    public static void main(String[] args){

        View view;
        ClientSideConnection clientSideConnection =new ClientSideConnection("127.0.0.1",12345);
        Thread t0 = new Thread(clientSideConnection);
        //ORDINE:
        //Istanzio Client
        //client.run (verranno chieste info giocatore)
        //Chiedere gui o cli a utente
        //istanziare View (quindi o gui o cli) passandole il client nel costruttore

        //running clientSideConnection
        t0.start();
        //per adesso istanzio direttamente CLI
        view = new Cli(clientSideConnection);




    }




}
