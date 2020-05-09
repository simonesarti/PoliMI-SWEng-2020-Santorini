package it.polimi.ingsw.model;

import it.polimi.ingsw.messages.GameToPlayerMessages.NewBoardStateMessage;

public class BoardState {

    private TowerState[][] towerStates = new TowerState[5][5];

    public BoardState(){}

    public TowerState getTowerState(int x, int y) {
        return towerStates[x][y];
    }

    public void setTowerStates(int x, int y, TowerCell towerCell){
        towerStates[x][y]=new TowerState(towerCell);
    }


    //TODO x Oli CLI (poi cancellali)
    //almeno non escono righe lunghe 45KM
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

}
