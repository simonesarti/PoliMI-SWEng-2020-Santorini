package it.polimi.ingsw.messages.PlayerToGameMessages;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.VirtualView;

/**
 * End turn message
 * View attribute is needed to report errors
 *
 */
public class PlayerEndOfTurnChoice extends PlayerMessage {

    private final VirtualView virtualView;
    private final Player player;

    public PlayerEndOfTurnChoice(VirtualView virtualView, Player player) {
        this.virtualView=virtualView;
        this.player = player;
    }

    public VirtualView getVirtualView() {
        return virtualView;
    }

    public Player getPlayer() {
        return player;
    }

}
