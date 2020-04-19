package it.polimi.ingsw.view;

import it.polimi.ingsw.messages.GameToPlayerMessages.LoseMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.NewBoardStateMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.NotifyMessages;
import it.polimi.ingsw.messages.GameToPlayerMessages.WinMessage;
import it.polimi.ingsw.messages.PlayerToGameMessages.PlayerMessage;
import it.polimi.ingsw.observe.Observable;
import it.polimi.ingsw.observe.Observer;

public class VirtualView extends Observable<PlayerMessage> implements Observer<NotifyMessages> {




    @Override
    public void update(NotifyMessages message) {

        if(message instanceof NewBoardStateMessage){

        }else if (message instanceof LoseMessage){

        }else if( message instanceof WinMessage){

        }


    }
}
