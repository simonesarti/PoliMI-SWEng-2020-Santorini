package it.polimi.ingsw.model.strategy.losestrategy;

import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Player;

public interface LoseStrategy {

    boolean movementLoss(GameBoard gameBoard, Player player);

    boolean buildingLoss(GameBoard gameBoard, Player player);
}
