package it.polimi.ingsw.model.strategy.losestrategy;

import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TurnInfo;

public class MinotaurLose implements LoseStrategy{

    @Override
    public int movementLoss(TurnInfo turnInfo,GameBoard gameBoard, Player player) {
        return 0;
    }

    @Override
    public boolean buildingLoss(TurnInfo turnInfo, GameBoard gameBoard, Player player) {
        return false;
    }
}
