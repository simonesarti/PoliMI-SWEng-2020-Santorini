package it.polimi.ingsw.GUI;

import it.polimi.ingsw.client.ClientSideConnection;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Worker;

import java.util.ArrayList;

public class AppGUI {

    public static void main(String[] args){

        new GUI(new ClientSideConnection("127.0.0.1",12345));
        //new PlayerInfoRequestDialog(false);
/*
        ArrayList<String> names=new ArrayList<>();
        names.add("Apollo");
        names.add("Artemis");
        names.add("Athena");
        names.add("Atlas");
        names.add("Demeter");
        names.add("Hephaestus");
        names.add("Minotaur");
        names.add("Pan");
        names.add("Prometheus");
        new DivinityChoiceDialog(names,3);
*/
/*
        GameBoard gameBoard=new GameBoard();
        Worker worker0=new Worker(Colour.PURPLE,0);
        Worker worker1=new Worker(Colour.RED,1);
        gameBoard.getTowerCell(4,4).getFirstNotPieceLevel().setWorker(worker0);
        gameBoard.getTowerCell(3,3).getFirstNotPieceLevel().setWorker(worker1);
        new StartingPositionRequestDialog(gameBoard.getBoardState());
*/
    }
}
