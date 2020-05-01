package it.polimi.ingsw.messages.PlayerToGameMessages.CompleteMessages;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.VirtualView;

public class PlayerQuitChoice extends PlayerMessage {

    public PlayerQuitChoice(VirtualView virtualView, Player player) {
        super(virtualView, player);
    }
}
