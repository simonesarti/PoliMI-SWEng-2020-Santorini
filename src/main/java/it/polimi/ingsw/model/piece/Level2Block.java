package it.polimi.ingsw.model.piece;

public class Level2Block extends Block {

    private static int piecesLeft=18;

    public Level2Block() { }

    public void decreasePiecesNumber(){
        piecesLeft--;
    }

    public void increasePiecesNumber(){ piecesLeft++;}

    public static boolean areTherePiecesLeft(){
        return piecesLeft > 0;
    }
}
