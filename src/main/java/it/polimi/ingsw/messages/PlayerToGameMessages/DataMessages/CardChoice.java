package it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages;

public class CardChoice extends DataMessage{

    private final String[] cardNames;

    public CardChoice(String[] cardNames) {
        this.cardNames = cardNames;
    }

    public String[] getCardNames() {
        return cardNames;
    }
}
