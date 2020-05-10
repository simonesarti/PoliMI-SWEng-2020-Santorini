package it.polimi.ingsw.messages.GameToPlayerMessages;

import it.polimi.ingsw.model.Player;

public class EndOfGameMessage extends NotifyMessages{

    private final Player player;

    public EndOfGameMessage(Player player) {
        this.player=player;
    }

    public Player getPlayer() {
        return player;
    }

}
