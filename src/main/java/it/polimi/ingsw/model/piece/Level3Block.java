package it.polimi.ingsw.model.piece;

public class Level3Block extends Block {

    private static int piecesLeft=19;

    public Level3Block() {}

    public static void decreasePiecesNumber(){
        piecesLeft--;
    }
}
