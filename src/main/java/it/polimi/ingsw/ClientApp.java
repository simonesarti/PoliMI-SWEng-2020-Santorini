package it.polimi.ingsw;

import it.polimi.ingsw.client.ClientSideConnection;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.cli.Cli;

import java.io.IOException;

public class ClientApp {

    public static void main(String[] args){

        View view;
        ClientSideConnection clientSideConnection = new ClientSideConnection("127.0.0.1",12345);

        /////////per adesso istanzio direttamente CLI
        view = new Cli(clientSideConnection);
        clientSideConnection.addObserver(view);

        try {
            clientSideConnection.run();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //ORDINE:
        //Istanzio Client
        //Chiedere gui o cli a utente
        //istanziare View (quindi o gui o cli) passandole il client nel costruttore
        //addObserver
        //client.run (verranno chieste info giocatore)







    }




}
