package it.polimi.ingsw.messages.GameToPlayerMessages.Notify;

import it.polimi.ingsw.model.Player;

public class WinMessage extends NotifyMessages {

    private final Player player;

    public WinMessage(Player player) {
        this.player=player;
    }

    public Player getPlayer() {
        return player;
    }
}
