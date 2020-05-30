package it.polimi.ingsw.model;

/**
 * Enumeration representing the three colours. Every player and worker have a colour. Every player has two workers of the same colour
 */
public enum Colour {

    RED, BLUE, PURPLE;

    /**
     * checks if another colour is different
     * @param other other colour
     * @return boolean
     */
    public boolean isEnemy(Colour other) {
        return this != other;
    }

}
