package it.polimi.ingsw.messages.GameToPlayerMessages.Notify;

import it.polimi.ingsw.messages.GameToPlayerMessages.Notify.NotifyMessages;
import it.polimi.ingsw.model.BoardState;

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

