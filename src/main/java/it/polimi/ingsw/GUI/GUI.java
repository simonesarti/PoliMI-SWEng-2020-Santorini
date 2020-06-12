package it.polimi.ingsw.GUI;

import it.polimi.ingsw.client.ClientSideConnection;
import it.polimi.ingsw.messages.GameToPlayerMessages.Notify.GameStartMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.Notify.NewBoardStateMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.ErrorMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.InfoMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.PlayerInfoRequest;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.PossibleCardsMessage;
import it.polimi.ingsw.view.View;

import javax.swing.*;

public class GUI extends View{

    private JFrame frame;
    private MainWindow mainWindow;
    private GuiController guiController;

    public GUI(ClientSideConnection connection){
        super(connection);
        SwingUtilities.invokeLater(this::createGUI);
    }

    private void createGUI(){

        guiController=new GuiController();
        guiController.setConnection(getClientSideConnection());

        frame =new MainFrame();
        mainWindow=new MainWindow(frame);
        frame.add(mainWindow.getMainPanel());
        frame.setVisible(true);

        guiController.setFrame(frame);

    }

    @Override
    public void handleNewBoardStateMessage(NewBoardStateMessage message) {
        guiController.setCurrentBoardState(message.getBoardState());
        if(guiController.gameHasStarted()){
            mainWindow.updateBoard(message.getBoardState());
        }
    }

    @Override
    public void handleInfoMessage(InfoMessage message) {
        JOptionPane.showMessageDialog(frame,message.getInfo(),"Info Message",JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void handleErrorMessage(ErrorMessage message) {
        JOptionPane.showMessageDialog(frame,message.getError(),"Error Message",JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void handlePlayerInfoRequest(PlayerInfoRequest message) {
        new PlayerInfoRequestDialog(message.isNicknameTaken(),guiController);
    }

    @Override
    public void handleCardMessageRequest(PossibleCardsMessage message) {
        new DivinityChoiceDialog(message.getGods(),message.getNumberOfChoices(),guiController);
    }

    @Override
    public void handleStartingPositionRequest() {
        new StartingPositionRequestDialog(guiController);
    }

    @Override
    public void handleGameStartMessage(GameStartMessage message) {
        guiController.setPlayerColor(message.getNicknames());
        mainWindow.setMatchGui(message.getDescriptions(),message.getNicknames(),guiController);
    }

    @Override
    public void handleCloseConnectionMessage() {
        JOptionPane.showMessageDialog(frame,"You have been disconnected","disconnection",JOptionPane.INFORMATION_MESSAGE);
    }

    //test
    public MainWindow getMainWindow() {
        return mainWindow;
    }
    public GuiController getGuiController() {
        return guiController;
    }
}

