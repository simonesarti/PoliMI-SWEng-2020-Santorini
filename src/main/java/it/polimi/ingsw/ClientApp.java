package it.polimi.ingsw;

import it.polimi.ingsw.GUI.GUI;
import it.polimi.ingsw.client.ClientSideConnection;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.cli.Cli;

import java.util.Scanner;

public class ClientApp {

    public static void main(String[] args){

        View view;
        ClientSideConnection clientSideConnection;
        Thread t0;
        Scanner stdin = new Scanner(System.in);


        String serverIP=null;
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

        String viewChoice;

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


        if(viewChoice.equals("cli")){
            view = new Cli(clientSideConnection);
        }else{
            view=new GUI(clientSideConnection);
        }

        clientSideConnection.addObserver(view);
        t0 = new Thread(clientSideConnection);
        t0.start();





    }




}
