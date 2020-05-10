package it.polimi.ingsw;

import it.polimi.ingsw.client.ClientSideConnection;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.cli.Cli;

import java.io.IOException;

public class ClientApp {

    public static void main(String[] args){

        Cli cli;
        ClientSideConnection clientSideConnection = new ClientSideConnection("127.0.0.1",12345);
        Thread t0;
        Thread t1;

        /////////per adesso istanzio direttamente CLI
        cli = new Cli(clientSideConnection);
        clientSideConnection.addObserver(cli);

        /*
        t0 = new Thread(clientSideConnection);
        t0.start();
        while(cli.canStart()==false){
            //do nothing
        }
        cli.run();


         */

        //ORDINE:
        //Istanzio Client
        //Chiedere gui o cli a utente
        //istanziare View (quindi o gui o cli) passandole il client nel costruttore
        //addObserver
        //client.run(verranno chieste info giocatore)
        //una volta finito lo scambio di informazioni iniziali, Cli.run()(e forse per Gui.run() sarà lo stesso?)



    }




}
