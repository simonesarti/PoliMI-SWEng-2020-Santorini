package it.polimi.ingsw.messages.GameToPlayerMessages;

import it.polimi.ingsw.model.BoardState;
import it.polimi.ingsw.model.Player;

import java.io.Serializable;

public class NewBoardStateMessage extends NotifyMessages implements Serializable {

    private final BoardState boardState;

    public NewBoardStateMessage(BoardState boardState) {
        this.boardState = boardState;
    }

    public BoardState getBoardState() {
        return boardState;
    }

}

