package it.polimi.ingsw.model;

import it.polimi.ingsw.model.piece.Dome;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TowerStateTest {

    TowerCell towerCell=new TowerCell();
    TowerState towerState;

    @Test
    void emptyCell(){
        towerState=new TowerState(towerCell);
        testValues(0,false,-1,null);
    }

    @Test
    void workerOnFloorLevel(){
        Worker worker=new Worker(Colour.RED,1);
        towerCell.getFirstNotPieceLevel().setWorker(worker);
        towerState=new TowerState(towerCell);
        testValues(0,false,2,Colour.RED);
    }

    @Test
    void domeOnFloorLevel(){
        towerCell.getFirstNotPieceLevel().setPiece(new Dome());
        towerCell.checkCompletion();
        towerCell.increaseTowerHeight();
        towerState=new TowerState(towerCell);
        testValues(1,true,-1,null);
    }


    private void testValues(int height,boolean completed, int wn, Colour wc){
        assertEquals(height,towerState.getTowerHeight());
        assertEquals(completed,towerState.isCompleted());
        assertEquals(wn,towerState.getWorkerNumber());
        assertEquals(wc,towerState.getWorkerColour());

    }


}