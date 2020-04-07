package it.polimi.ingsw.model.piece;

public class Dome extends Piece {

    private static int piecesLeft=22;

    public Dome(){}

    public void decreasePiecesNumber(){
        piecesLeft--;
    }

    public static boolean areTherePiecesLeft(){
        return piecesLeft > 0;
    }
}
