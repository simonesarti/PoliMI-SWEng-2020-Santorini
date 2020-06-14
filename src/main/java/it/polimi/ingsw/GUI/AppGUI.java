package it.polimi.ingsw.GUI;

import it.polimi.ingsw.client.ClientSideConnection;
import it.polimi.ingsw.messages.GameToPlayerMessages.Notify.GameStartMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.PlayerInfoRequest;
import it.polimi.ingsw.model.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AppGUI {

    public static void main(String[] args) throws InterruptedException {

        //STARTING PANEL TEST
        ClientSideConnection connection=new ClientSideConnection("127.0.0.1",12345);
        GUI gui=new GUI(connection);
        gui.handlePlayerInfoRequest(new PlayerInfoRequest(true));

/*
        //MAIN PANEL TEST
        JFrame jFrame=new MainFrame();

        ArrayList<String> cards=new ArrayList<>();
        ArrayList<String> nicknames=new ArrayList<>();
        ArrayList<Colour> colours=new ArrayList<>();
        cards.add("Athena");
        cards.add("Artemis");
        nicknames.add("Oli");
        nicknames.add("Ale");
        colours.add(Colour.BLUE);
        colours.add(Colour.RED);
        GameStartMessage message=new GameStartMessage(nicknames,colours,cards);

        GuiController testController=new GuiController();
        //change the name to test different workers selectable
        testController.setNickname("Ale");
        testController.setPlayerColor(message.getNicknames(),message.getColours());

        GameBoard gameBoard=new GameBoard();
        Worker worker0=new Worker(Colour.BLUE,0);
        gameBoard.getTowerCell(2,2).getFirstNotPieceLevel().setWorker(worker0);
        Worker worker3=new Worker(Colour.RED,0);
        gameBoard.getTowerCell(3,1).getFirstNotPieceLevel().setWorker(worker3);
        Worker worker4=new Worker(Colour.PURPLE,0);
        gameBoard.getTowerCell(0,2).getFirstNotPieceLevel().setWorker(worker4);
        testController.setCurrentBoardState(gameBoard.getBoardState());

        MainWindow mainWindow=new MainWindow(jFrame,message,testController);
        jFrame.add(mainWindow.getMainPanel());
        jFrame.setVisible(true);
*/
/*
        TimeUnit.SECONDS.sleep(5);
        System.out.println("waited");

        GameBoard gameBoard2=new GameBoard();
        Worker worker1=new Worker(Colour.PURPLE,0);
        Worker worker2=new Worker(Colour.RED,1);
        gameBoard2.getTowerCell(4,4).getFirstNotPieceLevel().setWorker(worker1);
        gameBoard2.getTowerCell(3,3).getFirstNotPieceLevel().setWorker(worker2);
        BoardState boardState=gameBoard2.getBoardState();
        mainWindow.updateBoard(boardState);
*/
        //new PlayerInfoRequestDialog(false,new GuiController());
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
        new DivinityChoiceDialog(names,3,new GuiController());
*/

        //STARTING POSITION REQUEST TEST
/*
        GuiController testController=new GuiController();
        GameBoard gameBoard=new GameBoard();
        Worker worker0=new Worker(Colour.PURPLE,0);
        Worker worker1=new Worker(Colour.RED,1);
        gameBoard.getTowerCell(4,4).getFirstNotPieceLevel().setWorker(worker0);
        gameBoard.getTowerCell(3,3).getFirstNotPieceLevel().setWorker(worker1);
        testController.setCurrentBoardState(gameBoard.getBoardState());
        new StartingPositionRequestDialog(testController);
*/

        //new HowToUseGuiDialog();

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
        TimeUnit.SECONDS.sleep(3);
        imageButton.setButtonImage(Images.getImage(Images.L0P));
        TimeUnit.SECONDS.sleep(3);
        imageButton.setButtonImage(null);

*/




    }
}
