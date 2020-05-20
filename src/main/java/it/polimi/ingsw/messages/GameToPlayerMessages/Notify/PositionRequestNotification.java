package it.polimi.ingsw.messages.GameToPlayerMessages.Notify;

import it.polimi.ingsw.model.Player;

public class PositionRequestNotification extends NotifyMessages{

    private final Player player;

    public PositionRequestNotification(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
