package it.polimi.ingsw.model.strategy.losestrategy;

import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TurnInfo;

public interface LoseStrategy {

    boolean movementLoss(TurnInfo turnInfo, GameBoard gameBoard, Player player);

    boolean buildingLoss(TurnInfo turnInfo, GameBoard gameBoard, Player player);
}
