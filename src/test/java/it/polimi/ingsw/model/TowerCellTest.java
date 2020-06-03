package it.polimi.ingsw.model;

import it.polimi.ingsw.model.piece.Dome;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TowerCellTest {

    TowerCell towerCell=new TowerCell();

    @Nested
    class completion{

        @Test
        void level0(){
            towerCell.getLevel(0).setPiece(new Dome());
            towerCell.checkCompletion();
            assertTrue(towerCell.isTowerCompleted());
        }

        @Test
        void level1(){
            towerCell.getLevel(1).setPiece(new Dome());
            towerCell.checkCompletion();
            assertTrue(towerCell.isTowerCompleted());
        }

        @Test
        void level2(){
            towerCell.getLevel(2).setPiece(new Dome());
            towerCell.checkCompletion();
            assertTrue(towerCell.isTowerCompleted());
        }

        @Test
        void level3(){
            towerCell.getLevel(3).setPiece(new Dome());
            towerCell.checkCompletion();
            assertTrue(towerCell.isTowerCompleted());
        }

    }
}