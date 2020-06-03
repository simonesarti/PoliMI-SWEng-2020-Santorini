package it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages;

/**
 * contains the card/s the player wants to use
 */
public class CardChoice extends DataMessage{

    private final String[] cardNames;

    public CardChoice(String[] cardNames) {
        this.cardNames = cardNames;
    }

    public String[] getCardNames() {
        return cardNames;
    }
}
