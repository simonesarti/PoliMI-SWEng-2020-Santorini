package it.polimi.ingsw.view;

import java.io.PrintStream;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;


public class View extends Observable implements Runnable, Observer {

    //va bene solo per scrittura su CLI
    private Scanner scanner;
    private PrintStream outputStream;

    //stessa cosa
    public View(){
        scanner = new Scanner(System.in);
        outputStream = new PrintStream(System.out);
    }


    @Override
    public void run(){

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
