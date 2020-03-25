package it.polimi.ingsw;

public enum Colour {

    WHITE, BLUE, GREY;

    public boolean isEnemy(Colour other) {

        if (this == other) {
            return false;
        } else {
            return true;
        }
    }
    
}
