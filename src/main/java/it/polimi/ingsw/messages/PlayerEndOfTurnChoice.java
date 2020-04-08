package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.View;

public class PlayerEndOfTurnChoice extends Message{

    private final View view;
    private final Player player;
    private final String end;

    public PlayerEndOfTurnChoice(View view, Player player) {
        this.view = view;
        this.player = player;
        this.end = "endOfTurn";
    }

    public View getView() {
        return view;
    }

    public Player getPlayer() {
        return player;
    }

    public String getEnd() {
        return end;
    }
}
