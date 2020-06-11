package it.polimi.ingsw.GUI;

import it.polimi.ingsw.model.BoardState;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Worker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuiControllerTest {

    @Test
    void setBoardState(){

        GuiController testController=new GuiController();

        assertNull(testController.getCurrentBoardState());

        GameBoard b1=new GameBoard();
        Worker worker0=new Worker(Colour.PURPLE,0);
        Worker worker1=new Worker(Colour.RED,1);
        b1.getTowerCell(4,4).getFirstNotPieceLevel().setWorker(worker0);
        b1.getTowerCell(3,3).getFirstNotPieceLevel().setWorker(worker1);
        BoardState bs1=b1.getBoardState();
        testController.setCurrentBoardState(bs1);

        assertEquals(bs1,testController.getCurrentBoardState());

        GameBoard b2=new GameBoard();
        Worker worker2=new Worker(Colour.PURPLE,0);
        Worker worker3=new Worker(Colour.RED,1);
        b2.getTowerCell(1,1).getFirstNotPieceLevel().setWorker(worker2);
        b2.getTowerCell(2,2).getFirstNotPieceLevel().setWorker(worker3);
        BoardState bs2=b2.getBoardState();
        testController.setCurrentBoardState(bs2);

        assertEquals(bs2,testController.getCurrentBoardState());
    }

}