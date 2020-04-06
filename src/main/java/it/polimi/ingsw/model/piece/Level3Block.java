package it.polimi.ingsw.model.piece;

public class Level3Block extends Block {

    private static int piecesLeft=19;

    public Level3Block() {}

    public void decreasePiecesNumber(){
        piecesLeft--;
    }

    public void increasePiecesNumber(){ piecesLeft++;}

    public boolean areTherePiecesLeft(){
        return piecesLeft > 0;
    }
}
