package it.polimi.ingsw.GUI;

import it.polimi.ingsw.GUI.messages.ActionMessage;
import it.polimi.ingsw.client.ClientSideConnection;
import it.polimi.ingsw.model.BoardState;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.observe.Observer;

import java.util.ArrayList;

public class GuiController implements Observer<ActionMessage> {

    private ClientSideConnection connection;
    private String nickname;
    private Colour playerColor;
    private BoardState currentBoardState;
    private boolean gameStarted;

    public GuiController() {
        setGameStarted(false);
    }

    public void setConnection(ClientSideConnection connection) {
        this.connection = connection;
    }

    public void setNickname(String nickname){
        this.nickname=nickname;
    }

    public void setPlayerColor(ArrayList<String> nicknames){

        for(int i=0;i<nicknames.size();i++){
            if(nickname.equals(nicknames.get(i))){

                if(i==0){
                    playerColor=Colour.RED;
                }else if(i==1){
                    playerColor=Colour.BLUE;
                }else{
                    playerColor=Colour.PURPLE;
                }
            }
        }

        setGameStarted(true);
    }

    public BoardState getCurrentBoardState() {
        return currentBoardState;
    }
    public void setCurrentBoardState(BoardState currentBoardState) {
        this.currentBoardState = currentBoardState;
    }

    public boolean gameHasStarted() {
        return gameStarted;
    }

    private void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public void send(Object message){
        connection.send(message);
    }


    @Override
    public void update(ActionMessage message) {
        System.out.println("azione eseguita");
    }
}
