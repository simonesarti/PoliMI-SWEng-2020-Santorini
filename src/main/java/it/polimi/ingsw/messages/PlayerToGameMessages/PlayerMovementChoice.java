package it.polimi.ingsw.messages.PlayerToGameMessages;

import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.MoveData;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.VirtualView;

/**
 * PlayerMovementChoice message contains information about a specific move (which Player, which worker, which Towercell).
 * View is needed to report errors
 */
public class PlayerMovementChoice extends PlayerMessage {

    private final VirtualView virtualView;
    private final Player player;
    private final MoveData moveData;

    //chosenWorker must be 0 or 1
    public PlayerMovementChoice(VirtualView virtualView, Player player, MoveData moveData) {

        this.virtualView=virtualView;
        this.player = player;
        this.moveData=moveData;
    }

    public VirtualView getVirtualView() {
        return virtualView;
    }

    public Player getPlayer() {
        return player;
    }

    public int getChosenWorker() { return moveData.getChosenWorker();}

    public int[] getMovingTo() {
        return moveData.getMovingTo();
    }
}
