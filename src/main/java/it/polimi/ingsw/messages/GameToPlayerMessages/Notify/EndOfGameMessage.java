package it.polimi.ingsw.messages.GameToPlayerMessages.Notify;

import it.polimi.ingsw.model.Player;


/**
 * This type of message is used by the model to communicate to the virtualView the winner, so that he can close everyone's connection
 */
public class EndOfGameMessage extends NotifyMessages {

    private final Player player;

    public EndOfGameMessage(Player player) {
        this.player=player;
    }

    public Player getPlayer() {
        return player;
    }

}
