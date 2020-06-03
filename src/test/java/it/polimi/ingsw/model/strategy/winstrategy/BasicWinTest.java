package it.polimi.ingsw.model.strategy.winstrategy;

import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.PlayerInfo;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BasicWinTest {

    PlayerInfo playerInfo;
    Player player;
    WinStrategy win;

    @BeforeEach
    void init(){
        playerInfo=new PlayerInfo("simone",new GregorianCalendar(1998, Calendar.SEPTEMBER, 16),3);
        player=new Player(playerInfo);
        player.setColour(Colour.RED);
        player.getWorker(0).setStartingPosition(0,0);
        win=new BasicWin();
    }

    @Test
    void checkWin() {

        //no victory, jumps up from level 0 to 1
        player.getWorker(0).movedToPosition(1,0,1);
        assertFalse(win.checkWin(player,0));

        //no victory, jumps up from level 1 to 2
        player.getWorker(0).movedToPosition(2,0,2);
        assertFalse(win.checkWin(player,0));

        //VICTORY, jumps up from level 2 to 3
        player.getWorker(0).movedToPosition(3,0,3);
        assertTrue(win.checkWin(player,0));

        //no victory, jumps down
        player.getWorker(0).movedToPosition(2,0,2);
        assertFalse(win.checkWin(player,0));

        //VICTORY, jumps up from level 2 to 3
        player.getWorker(0).movedToPosition(3,0,3);
        assertTrue(win.checkWin(player,0));

        //no victory, jumps down
        player.getWorker(0).movedToPosition(2,0,1);
        assertFalse(win.checkWin(player,0));

        //no victory, jumps up from level 1 to 3 (not 2 to 3)
        player.getWorker(0).movedToPosition(3,0,3);
        assertFalse(win.checkWin(player,0));

        //no victory, jumps down
        player.getWorker(0).movedToPosition(2,0,0);
        assertFalse(win.checkWin(player,0));

        //no victory, stays on the same level
        player.getWorker(0).movedToPosition(3,0,0);
        assertFalse(win.checkWin(player,0));

        //no victory, stays on the same level
        player.getWorker(0).movedToPosition(2,0,1);
        player.getWorker(0).movedToPosition(3,0,1);
        assertFalse(win.checkWin(player,0));

        //no victory, stays on the same level
        player.getWorker(0).movedToPosition(2,0,2);
        player.getWorker(0).movedToPosition(3,0,2);
        assertFalse(win.checkWin(player,0));

        //no victory, stays on the same level
        player.getWorker(0).movedToPosition(2,0,3);
        player.getWorker(0).movedToPosition(3,0,3);
        assertFalse(win.checkWin(player,0));
    }

}