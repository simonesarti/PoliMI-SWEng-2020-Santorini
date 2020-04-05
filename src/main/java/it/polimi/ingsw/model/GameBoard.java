package it.polimi.ingsw.model;

/**
 * This class represents the gameboard used to play Santorini. The gamebord is composed by a 5x5 matrix of towerCells.
 *
 */
public class GameBoard {

    private TowerCell[][] towerCells = new TowerCell[5][5];

    /**
     * Builds the gameboard. Every "square" is associated with a towerCell object.
     */
    public GameBoard(){
        for(int j = 0; j<5; j++){
            for(int i = 0; i<5; i++){
                towerCells[j][i]=new TowerCell();
            }
        }
    }

    public TowerCell getTowerCell(int x, int y){ return towerCells[x][y];}

}
