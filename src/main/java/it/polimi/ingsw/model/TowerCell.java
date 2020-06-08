package it.polimi.ingsw.model;

import it.polimi.ingsw.model.piece.Dome;

/**
 * This class represents the gameboard's towers, with 4 levels each
 */
public class TowerCell {

    private final TowerLevel[] towerLevels = new TowerLevel[4];
    private int towerHeight;
    private boolean towerCompleted;

    public TowerCell(){

        for(int i = 0; i<4; i++){
            towerLevels[i]=new TowerLevel();
        }

        towerHeight=0;
        towerCompleted=false;
    }

    /**
     * Checks if there is a dome on the TowerCell
     */
    public void checkCompletion(){

        for(TowerLevel level: towerLevels){
            if (level.getPiece() instanceof Dome) {
                towerCompleted = true;
                break;
            }
        }
    }

    public int getTowerHeight() {
        return towerHeight;
    }


    public boolean isTowerCompleted() {
        return towerCompleted;
    }

    public void increaseTowerHeight(){
        towerHeight++;
    }

    /**
     * Returns the first empty level
     */
    public TowerLevel getFirstNotPieceLevel(){
        if (towerHeight==4) throw new NullPointerException("MESSAGE: Towercell[4] doesn't exist");
        return towerLevels[towerHeight];}

    public TowerLevel getLevel(int n){
        return towerLevels[n];
    }

    /**
     * Checks if there is a worker on the TowerCell
     * @return boolean
     */
    public boolean hasWorkerOnTop() {
        if(towerCompleted){
            return false;
        }else {
            return getFirstNotPieceLevel().getWorker() != null;
        }
    }

}
