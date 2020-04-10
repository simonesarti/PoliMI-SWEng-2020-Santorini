package it.polimi.ingsw.model.strategy.losestrategy;

import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Player;

public class BasicLose implements LoseStrategy {


    @Override
    public boolean movementLoss(GameBoard gameBoard, Player player) {
        return false;
    }

    @Override
    public boolean buildingLoss(GameBoard gameBoard, Player player) {
        return false;
    }
}
