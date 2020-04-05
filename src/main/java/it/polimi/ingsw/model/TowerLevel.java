package it.polimi.ingsw.model;

import it.polimi.ingsw.model.piece.Piece;

public class TowerLevel {

    private Worker worker;
    private Piece piece;

    public TowerLevel() {
        worker = null;
        piece = null;
    }

    public Piece getPiece(){
        return piece;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public void setPiece(Piece piece){ this.piece = piece; }

    public void workerMoved(){worker=null;}

    public void pieceWasRemoved(){piece=null;}

    /**
     * Checks if towerlevel is occupied by a worker. The towerlevel's attribute "worker" is either null or associated to
     * a worker
     * @return boolean
     */
    public boolean isOccupied() {
        return ((worker != null) || (piece != null));
    }
}