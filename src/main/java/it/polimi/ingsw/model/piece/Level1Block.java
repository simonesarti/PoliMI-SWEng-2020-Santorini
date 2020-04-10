package it.polimi.ingsw.model.piece;

public class Level1Block extends Block {

    private static int piecesLeft=22;

    public Level1Block() {
        Level1Block.decreasePiecesNumber();
    }

    private static void decreasePiecesNumber(){
        piecesLeft--;
    }

    public static void increasePiecesNumber(){ piecesLeft++;}

    public static boolean areTherePiecesLeft(){
        return piecesLeft > 0;
    }


}
