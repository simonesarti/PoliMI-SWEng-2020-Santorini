package it.polimi.ingsw.GUI;

import it.polimi.ingsw.client.ClientSideConnection;
import it.polimi.ingsw.model.BoardState;
import it.polimi.ingsw.model.Colour;

import java.util.ArrayList;

public class GuiController {

    private final ClientSideConnection connection;
    private String nickname;
    private Colour playerColor;
    private BoardState currentBoardState;

    public GuiController(ClientSideConnection connection) {
        this.connection = connection;
    }

    public void setNickname(String nickname){
        this.nickname=nickname;
    }

    public Colour getPlayerColor() {
        return playerColor;
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
    }

    public BoardState getCurrentBoardState() {
        return currentBoardState;
    }
    public void setCurrentBoardState(BoardState currentBoardState) {
        this.currentBoardState = currentBoardState;
    }

    public void send(Object message){
        connection.send(message);
    }
}
