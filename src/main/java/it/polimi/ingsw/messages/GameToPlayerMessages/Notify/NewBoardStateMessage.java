package it.polimi.ingsw.messages.GameToPlayerMessages.Notify;

import it.polimi.ingsw.model.BoardState;

import java.io.Serializable;

/**
 * This type of message is used by the model to communicate to the virtualView, which then forwards it to client, the new
 * gameboard state. It contains a BoardState object which represents the new state of the board
 */
public class NewBoardStateMessage extends NotifyMessages implements Serializable {

    private final BoardState boardState;

    public NewBoardStateMessage(BoardState boardState) {
        this.boardState = boardState;
    }

    public BoardState getBoardState() {
        return boardState;
    }

}

