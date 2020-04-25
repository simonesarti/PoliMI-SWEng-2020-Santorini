package it.polimi.ingsw.view;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.messages.GameToPlayerMessages.NewBoardStateMessage;
import it.polimi.ingsw.messages.InfoMessage;
import it.polimi.ingsw.observe.Observer;

public class View{

    private Client client;

    private GameMessageReceiver gameMessageReceiver;

    private class GameMessageReceiver implements Observer<Object>{

        @Override
        public void update(Object message) {

            if(message instanceof InfoMessage){

            }else if(message instanceof NewBoardStateMessage){

            }
        }
    }

    public View(Client client){
        this.client=client;
    }

}
