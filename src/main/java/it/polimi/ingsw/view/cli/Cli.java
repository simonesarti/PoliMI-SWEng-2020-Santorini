package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.messages.GameToPlayerMessages.NewBoardStateMessage;
import it.polimi.ingsw.view.View;

import java.io.IOException;

public class Cli extends View {

    private Client client;

    public Cli(Client client) {
        super(client);
    }

    public void showNewBoard(NewBoardStateMessage message){

        System.out.println("Mostro la board sulla Command Line");
    }




}
