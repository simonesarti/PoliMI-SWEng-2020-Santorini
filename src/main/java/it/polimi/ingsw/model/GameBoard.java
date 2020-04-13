package it.polimi.ingsw.model;

import it.polimi.ingsw.model.piece.Dome;
import it.polimi.ingsw.model.piece.Level1Block;
import it.polimi.ingsw.model.piece.Level2Block;
import it.polimi.ingsw.model.piece.Level3Block;

/**
 * This class represents the gameboard used to play Santorini. The gamebord is composed by a 5x5 matrix of towerCells.
 *
 */
public class GameBoard {

    private TowerCell[][] towerCells = new TowerCell[5][5];

    /**
     * Builds the gameboard. Every "square" is associated with a towerCell object. Sets athenaPowerActive to "false"
     */
    public GameBoard(){
        for(int j = 0; j<5; j++){
            for(int i = 0; i<5; i++){
                towerCells[j][i]=new TowerCell();
            }
        }
    }

    public TowerCell getTowerCell(int x, int y){ return towerCells[x][y];}

    public void resetBoard(){

        for(int j=0; j<5;j++){
            for(int i=0;i<5;i++){
                towerCells[i][j].resetTower();
            }
        }
    }

    //ONLY FOR TESTS
    public void generateBoard(int[][] towers){

        for(int j=0;j<5;j++){
            for(int i=0;i<5;i++) {

                if (towers[i][j] == 0) {
                    //creates nothing
                } else if (towers[i][j] == 1) {
                    this.getTowerCell(j,i).getFirstNotPieceLevel().setPiece(new Level1Block());
                    this.getTowerCell(j,i).increaseTowerHeight();
                } else if (towers[i][j] == 2) {
                    this.getTowerCell(j,i).getFirstNotPieceLevel().setPiece(new Level1Block());
                    this.getTowerCell(j,i).increaseTowerHeight();
                    this.getTowerCell(j,i).getFirstNotPieceLevel().setPiece(new Level2Block());
                    this.getTowerCell(j,i).increaseTowerHeight();
                } else if (towers[i][j] == 3) {
                    this.getTowerCell(j,i).getFirstNotPieceLevel().setPiece(new Level1Block());
                    this.getTowerCell(j,i).increaseTowerHeight();
                    this.getTowerCell(j,i).getFirstNotPieceLevel().setPiece(new Level2Block());
                    this.getTowerCell(j,i).increaseTowerHeight();
                    this.getTowerCell(j,i).getFirstNotPieceLevel().setPiece(new Level3Block());
                    this.getTowerCell(j,i).increaseTowerHeight();
                } else if (towers[i][j] == 4) {
                    this.getTowerCell(j,i).getFirstNotPieceLevel().setPiece(new Level1Block());
                    this.getTowerCell(j,i).increaseTowerHeight();
                    this.getTowerCell(j,i).getFirstNotPieceLevel().setPiece(new Level2Block());
                    this.getTowerCell(j,i).increaseTowerHeight();
                    this.getTowerCell(j,i).getFirstNotPieceLevel().setPiece(new Level3Block());
                    this.getTowerCell(j,i).increaseTowerHeight();
                    this.getTowerCell(j,i).getFirstNotPieceLevel().setPiece(new Dome());
                    this.getTowerCell(j,i).increaseTowerHeight();
                    this.getTowerCell(j,i).checkCompletion();
                } else {
                    throw new IllegalArgumentException("HAI SBAGLIATO A METTERE I NUMERI");
                }
            }
        }
    }

}
