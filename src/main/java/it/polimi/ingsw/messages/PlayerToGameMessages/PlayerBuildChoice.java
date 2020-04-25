package it.polimi.ingsw.messages.PlayerToGameMessages;

import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.BuildData;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.VirtualView;

/**
 * PlayerBuildChoice message contains information about a specific build (what player, what type of Piece, which worker and so on...)
 * View is needed to report errors
 */
public class PlayerBuildChoice extends PlayerMessage {

    private final VirtualView virtualView;
    private final Player player;
    private final BuildData buildData;


    public PlayerBuildChoice(VirtualView virtualView, Player player, BuildData buildData) {

        this.virtualView=virtualView;
        this.player = player;
        this.buildData=buildData;
    }

    public VirtualView getVirtualView(){ return virtualView;}

    public Player getPlayer() {
        return player;
    }

    public int[] getBuildingInto() {
        return buildData.getBuildingInto();
    }

    public int getChosenWorker() { return buildData.getChosenWorker();}

    public String getPieceType(){
        return buildData.getPieceType();
    }
}

