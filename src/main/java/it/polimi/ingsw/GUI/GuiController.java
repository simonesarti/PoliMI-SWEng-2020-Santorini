package it.polimi.ingsw.GUI;

import it.polimi.ingsw.GUI.messages.*;
import it.polimi.ingsw.client.ClientSideConnection;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.BuildData;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.EndChoice;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.MoveData;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.QuitChoice;
import it.polimi.ingsw.model.BoardState;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.observe.Observer;

import javax.swing.*;
import java.util.ArrayList;

public class GuiController implements Observer<ActionMessage> {

    private class Status{

        private PerformingAction currentAction;
        private int chosenWorker;
        private int towardsX;
        private int towardsY;

        public Status() {
            resetStatus(PerformingAction.UNSET);
        }

        public PerformingAction getCurrentAction() {
            return currentAction;
        }

        public int getChosenWorker() {
            return chosenWorker;
        }
        public void setChosenWorker(int chosenWorker) {
            this.chosenWorker = chosenWorker;
        }

        public int getTowardsX() {
            return towardsX;
        }
        public void setTowardsX(int towardsX) {
            this.towardsX = towardsX;
        }

        public int getTowardsY() {
            return towardsY;
        }
        public void setTowardsY(int towardsY) {
            this.towardsY = towardsY;
        }

        public void resetStatus(PerformingAction newAction){
            currentAction=newAction;
            chosenWorker=-1;
            towardsX=-1;
            towardsY=-1;
        }
    }
    private enum PerformingAction {
        MOVE,BUILD,UNSET
    }


    private ClientSideConnection connection;

    private String nickname;
    private Colour playerColor;
    private BoardState currentBoardState;
    private boolean gameStarted;

    private Status status;
    private JFrame frame;


    public GuiController() {
        setGameStarted(false);
    }

    public void setConnection(ClientSideConnection connection) {
        this.connection = connection;
    }

    public void setNickname(String nickname){
        this.nickname=nickname;
    }

    public void setPlayerColor(ArrayList<String> nicknames, ArrayList<Colour> colours){

        for(int i=0;i<nicknames.size();i++){
            if(nickname.equals(nicknames.get(i))){
                playerColor=colours.get(i);
            }
        }

        setGameStarted(true);
    }

    public BoardState getCurrentBoardState() {
        return currentBoardState;
    }
    public void setCurrentBoardState(BoardState currentBoardState) {
        this.currentBoardState = currentBoardState;
    }

    public boolean gameHasStarted() {
        return gameStarted;
    }
    private void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
        status=new Status();
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public void send(Object message){
        connection.send(message);
    }


    @Override
    public void update(ActionMessage message) {

        if(message instanceof ActionRequest){
            String s=((ActionRequest) message).getAction();
            switch(s){
                case "move"-> status.resetStatus(PerformingAction.MOVE);
                case "build"-> status.resetStatus(PerformingAction.BUILD);
                case "end"-> {
                    status.resetStatus(PerformingAction.UNSET);
                    connection.send(new EndChoice());
                }
                case "quit"-> {
                    status.resetStatus(PerformingAction.UNSET);
                    connection.send(new QuitChoice());
                }
            }
        }

        else if(message instanceof BoardSelection){

            int x=((BoardSelection) message).getX();
            int y=((BoardSelection) message).getY();

            if(status.getCurrentAction()==PerformingAction.UNSET){
                JOptionPane.showMessageDialog(frame,"You must select the action to perform before using the board","Error Message",JOptionPane.ERROR_MESSAGE);
            }else{
                //if worker is not assigned yet, it is the next thing to select
                if(status.getChosenWorker()==-1){
                    int w=checkOwnWorkerPosition(x,y);
                    if(w==-1){
                        JOptionPane.showMessageDialog(frame,"You must select a cell that contains one of your workers","Error Message",JOptionPane.ERROR_MESSAGE);
                    }else{
                        status.setChosenWorker(w);
                    }
                //if the worker has already been selected, coordinates are saved
                }else {
                    status.setTowardsX(x);
                    status.setTowardsY(y);

                    //if player was moving, move information is sent
                    if (status.getCurrentAction() == PerformingAction.MOVE) {
                        connection.send(new MoveData(status.getChosenWorker()-1, status.getTowardsX(), status.getTowardsY()));
                    }
                    //if player was building, requests what to build and then send
                    else {
                        String request="What piece type do you want to build?";
                        String title="Piece type selection";
                        String[] options={"Block","Dome"};
                        int choice=JOptionPane.showOptionDialog(frame,request,title,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
                        String pieceType;
                        if(choice==JOptionPane.YES_OPTION){
                            pieceType=options[0];
                        }else{
                            pieceType=options[1];
                        }
                        connection.send(new BuildData(status.getChosenWorker()-1,status.getTowardsX(),status.getTowardsY(),pieceType));
                    }
                    //reset after send
                    status.resetStatus(PerformingAction.UNSET);
                }
            }

        }

        else if(message instanceof PlayerPersonalData){
            connection.send(((PlayerPersonalData) message).getPlayerInfo());

        }

        else if(message instanceof SelectedGods){
            connection.send(((SelectedGods) message).getCardChoice());

        }

        else if(message instanceof StartingPlacement){
            connection.send(((StartingPlacement) message).getStartingPositionChoice());
        }
    }

    private int checkOwnWorkerPosition(int x, int y){

        Colour workerColourOnBoard=currentBoardState.getTowerState(x,y).getWorkerColour();

        if(workerColourOnBoard==null || workerColourOnBoard!=playerColor){
            return -1;
        }else{
            return currentBoardState.getTowerState(x,y).getWorkerNumber();
        }

    }

    //testing
    public void setPlayerColor(Colour playerColor) {
        this.playerColor = playerColor;
    }
}
