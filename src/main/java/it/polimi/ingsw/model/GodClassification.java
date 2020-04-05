package it.polimi.ingsw.model;

/**
 * Enumeration representing the GodCard's classification (SIMPLE/ADVANCED)
 */
public enum GodClassification {

    SIMPLE, ADVANCED;

    /**
     * converts the string containing the gods' classification into the corresponding enumeration value
     *
     * @param classification the string representing the classification
     * @return the corresponding classification
     */
    public static GodClassification parseInput(String classification){
        return Enum.valueOf(GodClassification.class, classification.toUpperCase());
    }

}
