package it.polimi.ingsw.GUI.messages;

import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.CardChoice;

public class SelectedGods extends ActionMessage{

    private final CardChoice cardChoice;

    public SelectedGods(String[] choice) {
        cardChoice=new CardChoice(choice);
    }

    public CardChoice getCardChoice() {
        return cardChoice;
    }
}
