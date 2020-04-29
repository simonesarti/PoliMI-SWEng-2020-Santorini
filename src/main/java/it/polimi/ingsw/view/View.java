package it.polimi.ingsw.view;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.messages.GameToPlayerMessages.NewBoardStateMessage;
import it.polimi.ingsw.messages.InfoMessage;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.DataMessage;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.observe.Observable;
import it.polimi.ingsw.observe.Observer;

//TODO da qualche parte ovviamente si dovrà chiamare View.addObserver(Client)

public abstract class View extends Observable<DataMessage> {

    private final Player player;

    public View(Player player){

        this.player = player;
    }


}
