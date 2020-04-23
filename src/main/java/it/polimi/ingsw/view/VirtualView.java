package it.polimi.ingsw.view;

import it.polimi.ingsw.messages.GameToPlayerMessages.LoseMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.NewBoardStateMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.NotifyMessages;
import it.polimi.ingsw.messages.GameToPlayerMessages.WinMessage;
import it.polimi.ingsw.messages.PlayerToGameMessages.PlayerMessage;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.observe.Observable;
import it.polimi.ingsw.observe.Observer;
import it.polimi.ingsw.server.ServerSideConnection;

public class VirtualView extends Observable<PlayerMessage> implements Observer<NotifyMessages> {

    //ATTRIBUTES

    private Player player;

    private ServerSideConnection connectionToClient;

    //this class's update is triggered by ServerSideConnection reading a player messages and notifies the virtual view itself
    private class PlayerMessageReceiver implements Observer<PlayerMessage> {

        @Override
        public void update(PlayerMessage message) {

            try{
                notifyController(message);
            }catch(IllegalArgumentException e){
                connectionToClient.asyncSend("unable to notify controller!");
            }
        }

    }


    //METHODS

    public VirtualView(Player player, ServerSideConnection c){
        this.player=player;
        this.connectionToClient =c;
        //attach PlayerMessage Observer to ServerSideConnection object
        c.addObserver(new PlayerMessageReceiver());
    }

    public Player getPlayer(){
        return player;
    }

    private void notifyController(PlayerMessage message){
        notify(message);
    }

    public void reportInfo(Object message){
        connectionToClient.asyncSend(message);
    }

    //TODO
    @Override
    public void update(NotifyMessages message) {

        if(message instanceof NewBoardStateMessage){

        }else if (message instanceof LoseMessage){

        }else if( message instanceof WinMessage){

        }


    }
}
