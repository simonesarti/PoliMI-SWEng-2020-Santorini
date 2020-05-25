package it.polimi.ingsw.GUI;

import it.polimi.ingsw.client.ClientSideConnection;
import it.polimi.ingsw.messages.GameToPlayerMessages.Notify.NewBoardStateMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.ErrorMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.InfoMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.PlayerInfoRequest;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.PossibleCardsMessage;
import it.polimi.ingsw.view.View;

import javax.swing.*;

public class GUI extends View{

    private JFrame mainFrame;
    private JPanel mainPanel;
    private JPanel gamePanel;

    public GUI(ClientSideConnection connection){
        super(connection);
        SwingUtilities.invokeLater(this::createGUI);
    }

    private void createGUI(){
        mainFrame=new MainFrame();
        mainPanel=new MainPanel(getClientSideConnection());
        mainFrame.add(mainPanel);
    }

    private void createGamePanel(){
        gamePanel=new GamePanel(getClientSideConnection());
    }

    private void switchToGamePanel(){
        mainFrame.remove(mainPanel);
        mainFrame.add(gamePanel);
    }




    @Override
    public void handleNewBoardStateMessage(NewBoardStateMessage message) {
    }

    @Override
    public void handleInfoMessage(InfoMessage message) {

    }

    @Override
    public void handleErrorMessage(ErrorMessage message) {

    }

    @Override
    public void handlePlayerInfoRequest(PlayerInfoRequest message) {

    }

    @Override
    public void handleCardMessageRequest(PossibleCardsMessage message) {

    }

    @Override
    public void handleStartingPositionRequest() {

    }

    @Override
    public void handleCloseConnectionMessage() {

    }

    @Override
    public void run() {

    }

}

