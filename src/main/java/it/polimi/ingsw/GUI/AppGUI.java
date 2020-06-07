package it.polimi.ingsw.GUI;

import it.polimi.ingsw.client.ClientSideConnection;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Worker;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AppGUI {

    public static void main(String[] args) throws InterruptedException {

        ClientSideConnection connection=new ClientSideConnection("127.0.0.1",12345);
        GUI gui=new GUI(connection);

        TimeUnit.SECONDS.sleep(5);
        System.out.println("waited");

        GameBoard gameBoard=new GameBoard();
        Worker worker0=new Worker(Colour.PURPLE,0);
        Worker worker1=new Worker(Colour.RED,1);
        gameBoard.getTowerCell(4,4).getFirstNotPieceLevel().setWorker(worker0);
        gameBoard.getTowerCell(3,3).getFirstNotPieceLevel().setWorker(worker1);
        gui.getMainWindow().updateBoard(gameBoard.getBoardState());


        //new PlayerInfoRequestDialog(false,new GuiController(connection));
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
        new DivinityChoiceDialog(names,3,new GuiController(connection));
*/
        //STARTING POSITION REQUEST TEST
/*
        GameBoard gameBoard=new GameBoard();
        Worker worker0=new Worker(Colour.PURPLE,0);
        Worker worker1=new Worker(Colour.RED,1);
        gameBoard.getTowerCell(4,4).getFirstNotPieceLevel().setWorker(worker0);
        gameBoard.getTowerCell(3,3).getFirstNotPieceLevel().setWorker(worker1);
        new StartingPositionRequestDialog(gameBoard.getBoardState(),new GuiController(connection));
*/
/*
        //IMAGEBUTTON UPDATE
        ImageButton imageButton=new ImageButton();
        JFrame frame=new JFrame();
        JPanel panel=new JPanel();
        frame.setSize(100,100);
        panel.add(imageButton);
        frame.add(imageButton);
        imageButton.setOpaque(false);
        imageButton.setContentAreaFilled(false);
        frame.setVisible(true);
        TimeUnit.SECONDS.sleep(5);
        imageButton.setButtonImage(Images.getImage(Images.L0P));
*/


    }
}
