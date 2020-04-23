package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.PlayerToGameMessages.PlayerMessage;
import it.polimi.ingsw.observe.Observer;

public interface ConnectionToClient {

    void addObserver(Observer<PlayerMessage> observer);

    void removeObserver(Observer<PlayerMessage> observer);

    void asyncSend(Object message);

    void closeConnection();
}
