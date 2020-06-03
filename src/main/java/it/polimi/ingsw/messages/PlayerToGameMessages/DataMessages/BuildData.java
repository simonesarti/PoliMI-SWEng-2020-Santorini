package it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages;

/**
 * contains the details regarding the build action requested
 */
public class BuildData extends DataMessage{

    private final int chosenWorker;
    private final int[] buildingInto = new int[2];
    private final String pieceType;

    public BuildData(int chosenWorker, int x, int y, String pieceType) {
        this.chosenWorker = chosenWorker;
        this.buildingInto[0]=x;
        this.buildingInto[1]=y;
        this.pieceType = pieceType;
    }

    public int getChosenWorker() {
        return chosenWorker;
    }

    public int[] getBuildingInto() {
        return buildingInto;
    }

    public String getPieceType() {
        return pieceType;
    }
}
