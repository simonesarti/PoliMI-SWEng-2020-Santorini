package it.polimi.ingsw.messages.PlayerToGameMessages.CompleteMessages;

import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.StartingPositionChoice;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.VirtualView;

/**
 * message that contains the starting position in which the player wants to put his workers
 */
public class PlayerStartingPositionChoice extends PlayerMessage{

    private final StartingPositionChoice choice;

    public PlayerStartingPositionChoice(VirtualView virtualView, Player player, StartingPositionChoice choice) {
        super(virtualView, player);
        this.choice=choice;
    }

    public int getX1(){
        return choice.getX1();
    }

    public int getY1(){
        return choice.getY1();
    }

    public int getX2(){
        return choice.getX2();
    }

    public int getY2(){
        return choice.getY2();
    }
}
