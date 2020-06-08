package it.polimi.ingsw.model;

import it.polimi.ingsw.model.piece.Piece;

/**
 * This class represents the levels of the towers
 */
public class TowerLevel {

    private Worker worker;
    private Piece piece;

    public TowerLevel() {
        worker = null;
        piece = null;
    }

    public Worker getWorker(){return worker;}

    /**
     * Returns the piece type of the selected level
     */
    public Piece getPiece(){
        return piece;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public void setPiece(Piece piece){ this.piece = piece; }

    /**
     * worker attribute becomes null when the worker moves from the level
     */
    public void workerMoved(){worker=null;}

    /**
     * piece attribute becomes null when the piece is removed
     */
    public void pieceWasRemoved(){piece=null;}
}