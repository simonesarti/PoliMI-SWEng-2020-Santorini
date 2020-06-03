package it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages;

/**
 * contains the details regarding the move action requested
 */
public class MoveData extends DataMessage{

    private final int chosenWorker;
    private final int[] movingTo = new int[2];

    //chosenWorker must be 0 or 1
    public MoveData(int chosenWorker, int x, int y) {

        this.chosenWorker = chosenWorker;
        this.movingTo[0]=x;
        this.movingTo[1]=y;
    }

    public int getChosenWorker() {
        return chosenWorker;
    }

    public int[] getMovingTo() {
        return movingTo;
    }
}
