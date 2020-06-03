package it.polimi.ingsw.messages.PlayerToGameMessages.CompleteMessages;

import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.CardChoice;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.VirtualView;

/**
 * message that contains the list of cards selected by the user
 */
public class PlayerCardChoice extends PlayerMessage {

    private final CardChoice cardChoice;

    public PlayerCardChoice(VirtualView virtualView, Player player, CardChoice cardChoice) {
        super(virtualView,player);
        this.cardChoice = cardChoice;
    }

    public String[] getCardNames(){
        return cardChoice.getCardNames();
    }
}
