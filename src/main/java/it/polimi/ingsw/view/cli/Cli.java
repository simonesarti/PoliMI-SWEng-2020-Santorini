package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.client.ClientSideConnection;
import it.polimi.ingsw.messages.ErrorMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.NewBoardStateMessage;
import it.polimi.ingsw.messages.InfoMessage;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.View;

public class Cli extends View {



    private ClientSideConnection clientSideConnection;

    public Cli(ClientSideConnection clientSideConnection) {
        super(clientSideConnection);
    }


    @Override
    public void showInfo(InfoMessage message) {
        System.out.println("INFO: "+message.getInfo());
    }

    @Override
    public void showError(ErrorMessage message) {
        System.out.println("ERROR: "+message.getError());
    }

    @Override
    public void showGameBoard(NewBoardStateMessage message){

        System.out.println("Mostro la board sulla Command Line");


    }



    //almeno non escono righe lunghe 45KM nei controlli, e non ripetiamo codice

    private int getTowerHeight(NewBoardStateMessage message, int i, int j){
        return message.getBoardState().getTowerState(i,j).getTowerHeight();
    }

    private boolean isCompleted(NewBoardStateMessage message,int i,int j){
        return message.getBoardState().getTowerState(i,j).isCompleted();
    }

    private int getWorkerNumber(NewBoardStateMessage message,int i,int j){
        return message.getBoardState().getTowerState(i,j).getWorkerNumber();
    }

    private Colour getWorkerColour(NewBoardStateMessage message,int i,int j){
        return message.getBoardState().getTowerState(i,j).getWorkerColour();
    }




    //TODO da implementare e aggiungere tutti i parametri necessari, le metto tutte void e senza parametri

    //private qualcosa getANSIColour();


    //quadrato verde senza ninete dentro, 4 linee uniforme e 1 uniforme che riporta sopra
    private void printEmptyCell(){

        System.out.print(AnsiCode.BACKGROUND_GREEN);
        System.out.println("     " + AnsiCode.RESET);
        System.out.print(AnsiCode.BACKGROUND_GREEN);
        System.out.println("     " + AnsiCode.RESET);
        System.out.print(AnsiCode.BACKGROUND_GREEN);
        System.out.println("     " + AnsiCode.RESET);
        System.out.print(AnsiCode.BACKGROUND_GREEN);
        System.out.println("     " + AnsiCode.RESET);

    }

    //cupola: uniforme + 3strisce con cupola + uniforme che torna su
    private void printCellWithDome(){}

    //solo torre, no worker: 2 strisce uniformi + 1 striscia con testo del livello + 1 uniforme +1 uniforme che torna su
    private void printCellWithOnlyTower(){}

    //torre + worker : uniforme+testo+uniforme+testo+unforme che risale
    private void printCellWithWorker(){}



    //strisce

    //solo colore uniforme (va a capo stessa cella)
    private void printUniformStripe(String colour){

    }
    //con cupola (va a capo stessa cella)
    private void printDomeStripe(){}
    //uniforme con testo in mezzo (va a capo stessa cella)
    private void printTextStripe(){}
    //uniforme che riporta su il cursore
    private void printLastStripe(){}




}


