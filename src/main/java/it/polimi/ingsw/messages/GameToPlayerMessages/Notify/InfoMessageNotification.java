package it.polimi.ingsw.messages.GameToPlayerMessages.Notify;

import it.polimi.ingsw.model.Player;

/**
 * This type of message is used by the model to communicate to the virtualView an information, which has to be sent to the player
 * It contains the message string
 */
public class InfoMessageNotification extends NotifyMessages{

    private final Player player;
    private final String string;

    public InfoMessageNotification(Player player, String string) {
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
