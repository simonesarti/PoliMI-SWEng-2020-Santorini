package it.polimi.ingsw.model;

/**
 * Enumeration representing the three colours. Every player's workers have one colour.
 */
public enum Colour {

    WHITE, BLUE, GREY;

    /**
     * checks if another colour is different
     * @param other other colour
     * @return boolean
     */
    public boolean isEnemy(Colour other) {

        if (this == other) {
            return false;
        } else {
            return true;
        }
    }

}
