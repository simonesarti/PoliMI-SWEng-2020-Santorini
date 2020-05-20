package it.polimi.ingsw.messages.GameToPlayerMessages.Notify;

import it.polimi.ingsw.messages.GameToPlayerMessages.Others.PossibleCardsMessage;
import it.polimi.ingsw.model.Player;

public class PossibleCardsNotification extends NotifyMessages{

    private final Player player;
    private final PossibleCardsMessage possibleCardsMessage;

    public PossibleCardsNotification(Player player, PossibleCardsMessage possibleCardsMessage) {
        this.player = player;
        this.possibleCardsMessage = possibleCardsMessage;
    }

    public Player getPlayer() {
        return player;
    }

    public PossibleCardsMessage getPossibleCardsMessage() {
        return possibleCardsMessage;
    }
}
