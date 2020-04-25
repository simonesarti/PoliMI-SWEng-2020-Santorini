package it.polimi.ingsw.messages.GameToPlayerMessages;

import it.polimi.ingsw.model.Player;

public class QuitMessage extends NotifyMessages{

    private final Player player;

    public QuitMessage(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
