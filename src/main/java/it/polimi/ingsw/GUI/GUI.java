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
    private LobbyPanel lobbyPanel;
    private MainWindow mainWindow;
    private GuiController guiController;

    public GUI(ClientSideConnection connection){
        super(connection);
        createGUI();
    }

    /**
     * Creates the base frame for the gui, and adds the Lobby panel.
     * It also creates the guiController object
     */
    private void createGUI(){

        guiController=new GuiController();
        guiController.setConnection(getClientSideConnection());

        frame =new MainFrame();

        lobbyPanel=new LobbyPanel(frame);
        frame.add(lobbyPanel);

        frame.setVisible(true);

        guiController.setFrame(frame);

    }

    /**
     * updates the state of the board maintained by the user's gui. If the game has already started, and
     * therefore the GameWindow has already been created, the board shown is updated.
     * @param message contains the new state of the board
     */
    @Override
    public void handleNewBoardStateMessage(NewBoardStateMessage message) {
        guiController.setCurrentBoardState(message.getBoardState());
        if(guiController.gameHasStarted()){
            mainWindow.updateBoard(message.getBoardState());
        }
    }

    /**
     * shows a JOptionPane containing the information received
     * @param message contains the information received
     */
    @Override
    public void handleInfoMessage(InfoMessage message) {
        JOptionPane.showMessageDialog(frame,message.getInfo(),"Info Message",JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * shows a JOptionPane containing the error received
     * @param message contains the error received
     */
    @Override
    public void handleErrorMessage(ErrorMessage message) {
        JOptionPane.showMessageDialog(frame,message.getError(),"Error Message",JOptionPane.ERROR_MESSAGE);
    }

    /**
     * shows the JDialog that asks for the user's information
     * @param message is the request
     */
    @Override
    public void handlePlayerInfoRequest(PlayerInfoRequest message) {
        new PlayerInfoRequestDialog(frame,message.isNicknameTaken(),guiController);
    }

    /**
     * shows the JDialog that asks to the user which card/s he wants to use
     * @param message contains the available cards
     */
    @Override
    public void handleCardMessageRequest(PossibleCardsMessage message) {
        new DivinityChoiceDialog(frame,message.getGods(),message.getNumberOfChoices(),guiController);
    }

    /**
     * shows the JDialog that asks for the user's starting positions
     */
    @Override
    public void handleStartingPositionRequest() {
        new StartingPositionRequestDialog(frame,guiController);
    }

    @Override
    public void handleGameStartMessage(GameStartMessage message) {
        guiController.setPlayerColor(message.getNicknames(),message.getColours());
        frame.getContentPane().removeAll();
        frame.repaint();
        frame.revalidate();
        mainWindow=new MainWindow(frame,message,guiController);
        frame.add(mainWindow.getMainPanel());
        frame.repaint();
        frame.revalidate();
    }

    /**
     * shows a JOptionPane that informs the player that he has been disconnected
     */
    @Override
    public void handleCloseConnectionMessage() {
        JOptionPane.showMessageDialog(frame,"You have been disconnected","Disconnection",JOptionPane.INFORMATION_MESSAGE);
    }

    //test
    public MainWindow getMainWindow() {
        return mainWindow;
    }
    public GuiController getGuiController() {
        return guiController;
    }
    public JFrame getFrame(){return frame;}
}

