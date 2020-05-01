package it.polimi.ingsw.messages.PlayerToGameMessages.CompleteMessages;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.VirtualView;

public class PlayerMessage{

    private final VirtualView virtualView;
    private final Player player;

    public PlayerMessage(VirtualView virtualView, Player player) {
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
