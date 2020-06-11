package it.polimi.ingsw;

import it.polimi.ingsw.client.ClientSideConnection;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.cli.Cli;

import java.util.Scanner;

public class ClientApp {

//TODO CHIEDERE IP,CHIEDERE CLI/GUI E ASSEGNARE QUELLA GIUSTA
    public static void main(String[] args){

        View view;
        ClientSideConnection clientSideConnection;
        Thread t0;
        Thread t1;
        Scanner stdin = new Scanner(System.in);


        String serverIP = null;
        String[] tokens;
        boolean valid;
        //checking serverIp feasibility
        do{
            try {
                valid = true;
                System.out.println("Insert server ipV4:");
                serverIP = stdin.nextLine();
                
                tokens = serverIP.split("\\.");
                if(tokens.length != 4){
                    System.out.println("ipv4 must be composed by 4 integers:");
                    valid = false;
                }else{
                    Integer.parseInt(tokens[0]);
                    Integer.parseInt(tokens[1]);
                    Integer.parseInt(tokens[2]);
                    Integer.parseInt(tokens[3]);
                }



            } catch (NumberFormatException e) {
                System.out.println("Not an ipv4");
                valid = false;
            }
        }while(!valid);

        //Creating clientSideConnection
        clientSideConnection = new ClientSideConnection(serverIP,12345);


        String viewChoice = null;

        //User must choice cli or gui
        do{

            valid = true;
            System.out.println("Insert 'cli' for command-line version or 'gui' for graphical interface version:");
            viewChoice = stdin.nextLine();

            if(!viewChoice.equals("cli") && !viewChoice.equals("gui") ) {
                System.out.println("You must choose one of the options...");
                valid = false;
            }





        }while(!valid);

        //TODO se chiudo stdin dà errore. Lasciandola aperta funziona tutto. Mi sembra strano perché viene istanziata
        //TODO un'altra volta all'interno del costruttore di Cli. Per sicurezza magari chiediamo a un tutor

        if(viewChoice.equals("cli")){

            view = new Cli(clientSideConnection);
            clientSideConnection.addObserver(view);

            t0 = new Thread(clientSideConnection);
            t0.start();

            //ATTENZIONE: sia questo processo che il thread t0 vanno a guardare/cambiare canStart. Ho sincronizzato set/get sulla classe
            while(!view.getCanStart() && clientSideConnection.isActive()){
                //do nothing

            }
            t1 = new Thread(view);
            t1.start();


            try {
                t0.join();
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }else{
            //TODO fai partire gui
            //TODO ricorda di fare addObserver!
        }





    }




}
