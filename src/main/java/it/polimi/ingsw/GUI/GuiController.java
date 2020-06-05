package it.polimi.ingsw.GUI;

import it.polimi.ingsw.client.ClientSideConnection;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.ClientViewSupportFunctions;

import java.util.ArrayList;

public class GuiController {

    private final ClientSideConnection connection;
    private String nickname;
    private Colour playerColor;

    public GuiController(ClientSideConnection connection) {
        this.connection = connection;
    }

    public void setNickname(String nickname){
        this.nickname=nickname;
    }

    public void setPlayerColor(ArrayList<String> nickname){

    }
}
