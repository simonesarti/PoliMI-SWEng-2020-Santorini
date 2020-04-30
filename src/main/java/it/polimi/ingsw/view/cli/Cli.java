package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.client.ClientSideConnection;
import it.polimi.ingsw.messages.GameToPlayerMessages.NewBoardStateMessage;
import it.polimi.ingsw.view.View;

public class Cli extends View {

    private ClientSideConnection clientSideConnection;

    public Cli(ClientSideConnection clientSideConnection) {
        super(clientSideConnection);
    }

    public void showNewBoard(NewBoardStateMessage message){

        System.out.println("Mostro la board sulla Command Line");

    }




}
