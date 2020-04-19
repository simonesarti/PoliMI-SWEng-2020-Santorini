package it.polimi.ingsw.messages.GameToPlayerMessages;

import it.polimi.ingsw.model.BoardState;
import it.polimi.ingsw.model.Player;

public class NewBoardStateMessage extends NotifyMessages{

    private final BoardState boardState;
    private final Player player;

    public NewBoardStateMessage(BoardState boardState, Player player) {
        this.boardState = boardState;
        this.player=player;
    }

    public BoardState getBoardState() {
        return boardState;
    }

    public Player getPlayer() {
        return player;
    }
}

