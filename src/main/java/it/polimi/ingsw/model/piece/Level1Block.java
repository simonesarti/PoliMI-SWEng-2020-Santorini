package it.polimi.ingsw.model.piece;

public class Level1Block extends Block {

    private static int piecesLeft=22;

    public Level1Block() {}

    public void decreasePiecesNumber(){
        piecesLeft--;
    }

    public void increasePiecesNumber(){ piecesLeft++;}

    public boolean areTherePiecesLeft(){
        return piecesLeft > 0;
    }


}
