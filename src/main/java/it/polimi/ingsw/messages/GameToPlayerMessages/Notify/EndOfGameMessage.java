package it.polimi.ingsw.messages.GameToPlayerMessages.Notify;

import it.polimi.ingsw.messages.GameToPlayerMessages.Notify.NotifyMessages;
import it.polimi.ingsw.model.Player;

public class EndOfGameMessage extends NotifyMessages {

    private final Player player;

    public EndOfGameMessage(Player player) {
        this.player=player;
    }

    public Player getPlayer() {
        return player;
    }

}