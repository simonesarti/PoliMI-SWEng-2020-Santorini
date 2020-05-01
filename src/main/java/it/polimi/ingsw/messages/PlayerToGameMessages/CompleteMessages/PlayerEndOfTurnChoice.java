package it.polimi.ingsw.messages.PlayerToGameMessages.CompleteMessages;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.VirtualView;

/**
 * End turn message
 * View attribute is needed to report errors
 *
 */
public class PlayerEndOfTurnChoice extends PlayerMessage {

    public PlayerEndOfTurnChoice(VirtualView virtualView, Player player) {
        super(virtualView, player);
    }
}
