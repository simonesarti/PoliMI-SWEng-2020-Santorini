package it.polimi.ingsw.messages.GameToPlayerMessages.Notify;

import it.polimi.ingsw.model.Player;

/**
 * This type of message is used by the model to communicate to the virtualView that the player has to select his starting position
 */
public class PositionRequestNotification extends NotifyMessages{

    private final Player player;

    public PositionRequestNotification(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
