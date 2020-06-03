package it.polimi.ingsw.messages.GameToPlayerMessages.Notify;

import it.polimi.ingsw.model.Player;

/**
 * This type of message is used by the model to communicate to the virtualView which players must now play, after the
 * previous turn ended
 */
public class NewTurnMessage extends NotifyMessages {

    private final Player player;

    public NewTurnMessage(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
