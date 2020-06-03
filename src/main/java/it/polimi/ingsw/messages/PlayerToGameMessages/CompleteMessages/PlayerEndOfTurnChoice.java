package it.polimi.ingsw.messages.PlayerToGameMessages.CompleteMessages;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.VirtualView;

/**
 * End turn message
 */
public class PlayerEndOfTurnChoice extends PlayerMessage {

    public PlayerEndOfTurnChoice(VirtualView virtualView, Player player) {
        super(virtualView, player);
    }
}
