package it.polimi.ingsw.cli;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.cli.Cli;
import it.polimi.ingsw.supportClasses.FakeClientConnection;
import org.junit.jupiter.api.Test;

class CliTest {

    Cli cli = new Cli(new FakeClientConnection("127.0.0.1",12345));

    @Test
    void handleInput() {


        String[] s = {"move", "1", "2", "1"};
        cli.handleInput(s);
        s = new String[]{"build", "1", "2", "1","Dome"};
        cli.handleInput(s);
        s = new String[]{"end"};
        cli.handleInput(s);
        s = new String[]{"quit"};
        cli.handleInput(s);


    }
}