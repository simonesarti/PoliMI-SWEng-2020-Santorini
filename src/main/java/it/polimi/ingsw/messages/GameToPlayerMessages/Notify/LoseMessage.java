package it.polimi.ingsw.messages.GameToPlayerMessages.Notify;

import it.polimi.ingsw.model.Player;

/**
 * This type of message is used by the model to communicate to the virtualView that the specified player lost
 */
public class LoseMessage extends NotifyMessages {

    private final Player player;

    public LoseMessage(Player player) {
        this.player=player;
    }

    public Player getPlayer() {
        return player;
    }

}
