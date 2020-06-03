package it.polimi.ingsw.messages.GameToPlayerMessages.Notify;

import it.polimi.ingsw.model.Player;

/**
 * This type of message is used by the model to communicate to the virtualView that the player made an error. It contains the
 * string with the description of the error
 */
public class ErrorMessageNotification extends NotifyMessages{

    private final Player player;
    private final String string;

    public ErrorMessageNotification(Player player, String string) {
        this.player = player;
        this.string = string;
    }

    public Player getPlayer() {
        return player;
    }

    public String getString() {
        return string;
    }
}
