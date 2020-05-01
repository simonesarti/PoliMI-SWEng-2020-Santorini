package it.polimi.ingsw.messages.GameToPlayerMessages;

import it.polimi.ingsw.model.Player;

public class NewTurnMessage extends NotifyMessages{

    private final Player player;

    public NewTurnMessage(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
