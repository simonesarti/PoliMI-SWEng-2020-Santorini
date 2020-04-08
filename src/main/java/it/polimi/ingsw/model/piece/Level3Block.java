package it.polimi.ingsw.model.piece;

public class Level3Block extends Block {

    private static int piecesLeft=19;

    public Level3Block() {
        Level3Block.decreasePiecesNumber();
    }

    public static void decreasePiecesNumber(){
        piecesLeft--;
    }

    public static void increasePiecesNumber(){ piecesLeft++;}

    public static boolean areTherePiecesLeft(){
        return piecesLeft > 0;
    }
}
