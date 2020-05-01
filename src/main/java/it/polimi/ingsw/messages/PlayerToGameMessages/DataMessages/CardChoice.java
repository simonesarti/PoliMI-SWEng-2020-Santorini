package it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages;

public class CardChoice extends DataMessage{

    private final String cardName;

    public CardChoice(String cardName) {
        this.cardName = cardName;
    }

    public String getCardName() {
        return cardName;
    }
}
