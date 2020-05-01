package it.polimi.ingsw.messages.PlayerToGameMessages;

import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.CardChoice;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.VirtualView;

public class PlayerCardChoice extends PlayerMessage{

    private final VirtualView virtualView;
    private final Player player;
    private final CardChoice cardChoice;

    public PlayerCardChoice(VirtualView virtualView, Player player, CardChoice cardChoice) {
        this.virtualView = virtualView;
        this.player = player;
        this.cardChoice = cardChoice;
    }

    public VirtualView getVirtualView() {
        return virtualView;
    }

    public Player getPlayer() {
        return player;
    }

    public String getCardName(){
        return cardChoice.getCardName();
    }
}
