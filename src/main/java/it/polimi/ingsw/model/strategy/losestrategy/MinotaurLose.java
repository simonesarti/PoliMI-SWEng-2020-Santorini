package it.polimi.ingsw.model.strategy.losestrategy;

import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TurnInfo;

public class MinotaurLose implements LoseStrategy{

    @Override
    public boolean movementLoss(TurnInfo turnInfo,GameBoard gameBoard, Player player,int chosenWorker) {
        return false;
    }

    @Override
    public boolean buildingLoss(TurnInfo turnInfo, GameBoard gameBoard, Player player,int chosenWorker) {

        return (new BasicLose()).buildingLoss(turnInfo, gameBoard, player, chosenWorker);
    }
}
