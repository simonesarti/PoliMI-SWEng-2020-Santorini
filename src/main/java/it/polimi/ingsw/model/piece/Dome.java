package it.polimi.ingsw.model.piece;

public class Dome extends Piece {

    private static int piecesLeft=22;

    public Dome(){}

    public static void decreasePiecesNumber(){
        piecesLeft--;
    }
}
