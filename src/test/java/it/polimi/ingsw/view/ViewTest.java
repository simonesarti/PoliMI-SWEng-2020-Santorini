package it.polimi.ingsw.view;

import it.polimi.ingsw.client.ClientSideConnection;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.ErrorMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.GameMessage;
import it.polimi.ingsw.view.cli.Cli;
import org.junit.jupiter.api.Test;

class ViewTest {

    View cli = new Cli(new ClientSideConnection("127.0.0.1",12345));


    @Test
    void updateTest(){


        cli.update(new ErrorMessage(GameMessage.nicknameTaken));


    }

}