package it.polimi.ingsw.messages.PlayerToGameMessages.CompleteMessages;

import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.BuildData;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.VirtualView;

/**
 * PlayerBuildChoice message contains information about a specific build (what player, what type of Piece, which worker and so on...)
 * View is needed to report errors
 */
public class PlayerBuildChoice extends PlayerMessage {

    private final BuildData buildData;


    public PlayerBuildChoice(VirtualView virtualView, Player player, BuildData buildData) {
        super(virtualView,player);
        this.buildData=buildData;
    }

    public int[] getBuildingInto() {
        return buildData.getBuildingInto();
    }

    public int getChosenWorker() { return buildData.getChosenWorker();}

    public String getPieceType(){
        return buildData.getPieceType();
    }
}

