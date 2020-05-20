package it.polimi.ingsw.messages.GameToPlayerMessages.Notify;

import it.polimi.ingsw.model.Player;

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
