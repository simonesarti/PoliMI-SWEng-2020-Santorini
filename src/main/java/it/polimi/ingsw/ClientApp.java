package it.polimi.ingsw;

import it.polimi.ingsw.client.ClientSideConnection;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.cli.Cli;

import java.io.IOException;

public class ClientApp {

    //TODO IMPORTANTE: alla fine della fase di preparazione pre-partita si deve settare view.canStart a true
    public static void main(String[] args){

        View view;
        ClientSideConnection clientSideConnection = new ClientSideConnection("127.0.0.1",12345);
        Thread t0;
        Thread t1;

        /////////per adesso istanzio direttamente CLI
        view = new Cli(clientSideConnection);
        clientSideConnection.addObserver(view);


        System.out.println("Sono nella clientApp prima di tutto, view.getCanStart: "+ view.getCanStart());

        t0 = new Thread(clientSideConnection);
        t0.start();
        //ATTENZIONE: sia questo processo che il thread t0 vanno a guardare/cambiare canStart. Ho sincronizzato set/get sulla classe
        while(view.getCanStart()==false){
            //do nothing

        }
        System.out.println("Ho superato while getCanstart false");
        t1 = new Thread(view);
        t1.start();
        System.out.println("Ho superato start t1");

        try {
            t0.join();
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //ORDINE:
        //Istanzio Client
        //Chiedere gui o cli a utente
        //istanziare View (quindi o gui o cli) passandole il client nel costruttore
        //addObserver
        //client.run(verranno chieste info giocatore)
        //una volta finito lo scambio di informazioni iniziali, Cli.run()(e forse per Gui.run() sarà lo stesso?)



    }




}
