package it.polimi.ingsw.GUI.messages;

import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.CardChoice;

/**
 * message containing the list of gods selected by the player (can be only one)
 */
public class SelectedGods extends ActionMessage{

    private final CardChoice cardChoice;

    public SelectedGods(String[] choice) {
        cardChoice=new CardChoice(choice);
    }

    public CardChoice getCardChoice() {
        return cardChoice;
    }
}
