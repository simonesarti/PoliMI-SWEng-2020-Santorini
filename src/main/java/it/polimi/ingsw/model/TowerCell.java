package it.polimi.ingsw.model;

import it.polimi.ingsw.model.piece.Dome;

public class TowerCell {

    private TowerLevel[] towerLevels = new TowerLevel[4];
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

    public void decreaseTowerHeight(){
        towerHeight--;
    }

    public TowerLevel getFirstNotPieceLevel(){
        if (towerHeight==4) throw new NullPointerException("MESSAGE: Towercell[4] doesn't exist");
        return towerLevels[towerHeight];}

    public boolean hasWorkerOnTop() {return getFirstNotPieceLevel().getWorker()!=null;}

    public void resetTower() {

        for(int i=0;i<4;i++){
            towerLevels[i].workerMoved();
            towerLevels[i].pieceWasRemoved();
        }
    }
}
