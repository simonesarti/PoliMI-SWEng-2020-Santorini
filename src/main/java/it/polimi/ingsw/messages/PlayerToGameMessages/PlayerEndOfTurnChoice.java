package it.polimi.ingsw.messages.PlayerToGameMessages;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.View;

/**
 * End turn message
 * View attribute is needed to report errors
 *
 */
public class PlayerEndOfTurnChoice extends PlayerMessage {

    private final View view;
    private final Player player;

    public PlayerEndOfTurnChoice(View view, Player player) {
        this.view = view;
        this.player = player;
    }

    public View getView() {
        return view;
    }

    public Player getPlayer() {
        return player;
    }

}
