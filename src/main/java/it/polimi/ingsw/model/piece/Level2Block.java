package it.polimi.ingsw.model.piece;

public class Level2Block extends Block {

    private static int piecesLeft=18;

    public Level2Block() {
        Level2Block.decreasePiecesNumber();
    }

    public static void decreasePiecesNumber(){
        piecesLeft--;
    }

    public static void increasePiecesNumber(){ piecesLeft++;}

    public static boolean areTherePiecesLeft(){
        return piecesLeft > 0;
    }
}
