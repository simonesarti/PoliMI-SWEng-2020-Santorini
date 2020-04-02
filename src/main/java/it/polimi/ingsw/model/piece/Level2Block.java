package it.polimi.ingsw.model.piece;

public class Level2Block extends Block {

    private static int piecesLeft=18;

    public Level2Block() { }

    public static void decreasePiecesNumber(){
        piecesLeft--;
    }

}
