package it.polimi.ingsw.GUI.messages;

import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.StartingPositionChoice;

/**
 * message containing the coordinates of the cells in which the player wants to place his workers at
 * the beginning of the game
 */
public class StartingPlacement extends ActionMessage{

    private final StartingPositionChoice startingPositionChoice;

    public StartingPlacement(int x1, int y1, int x2, int y2) {
        startingPositionChoice=new StartingPositionChoice(x1,y1,x2,y2);
    }

    public StartingPositionChoice getStartingPositionChoice() {
        return startingPositionChoice;
    }
}
