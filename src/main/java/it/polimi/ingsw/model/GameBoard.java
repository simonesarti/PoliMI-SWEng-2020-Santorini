package it.polimi.ingsw.model;

public class GameBoard {

    private TowerCell[][] towerCells = new TowerCell[5][5];

    public GameBoard(){
        for(int j = 0; j<5; j++){
            for(int i = 0; i<5; i++){
                towerCells[j][i]=new TowerCell();
            }
        }
    }

    public TowerCell getTowerCell(int x, int y){ return towerCells[x][y];}

}
