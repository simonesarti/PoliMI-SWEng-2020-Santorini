package it.polimi.ingsw.model;

import java.io.Serializable;

public class BoardState implements Serializable {

    private final TowerState[][] towerStates = new TowerState[5][5];

    public BoardState(){}

    public TowerState getTowerState(int x, int y) {
        return towerStates[x][y];
    }

    public void setTowerStates(int x, int y, TowerCell towerCell){
        towerStates[x][y]=new TowerState(towerCell);
    }


}
