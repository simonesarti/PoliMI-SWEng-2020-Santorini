package it.polimi.ingsw.model;

import java.util.Objects;

public class Position {

    private final int x;
    private final int y;
    private final int z;

    private Worker worker;
    private Piece piece;

    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Position getPosition() {
        return this;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public boolean isOccupied(){
        return ((worker == null) && (piece == null));
    }

    public void workerMoved(){
        this.worker=null;
    }

    public void towerPieceRemoved(){
        this.piece=null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x &&
                y == position.y &&
                z == position.z;
    }


}

