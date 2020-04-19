package it.polimi.ingsw.view;

import it.polimi.ingsw.messages.GameToPlayerMessages.NotifyMessages;
import it.polimi.ingsw.messages.PlayerToGameMessages.PlayerMessage;
import it.polimi.ingsw.observe.Observable;
import it.polimi.ingsw.observe.Observer;

public class VirtualView extends Observable<PlayerMessage> implements Observer<NotifyMessages> {


    @Override
    public void update(NotifyMessages message) {

    }
}
