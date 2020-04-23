package it.polimi.ingsw.view;

import it.polimi.ingsw.messages.GameToPlayerMessages.LoseMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.NewBoardStateMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.NotifyMessages;
import it.polimi.ingsw.messages.GameToPlayerMessages.WinMessage;
import it.polimi.ingsw.messages.PlayerToGameMessages.PlayerMessage;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.observe.Observable;
import it.polimi.ingsw.observe.Observer;
import it.polimi.ingsw.server.ConnectionToClient;

public class VirtualView extends Observable<PlayerMessage> implements Observer<NotifyMessages> {

    private Player player;

    private ConnectionToClient connectionToClient;

    //this class's update is triggered by ServerSideConnection reading a player messages and notifies the virtual view itself
    private class PlayerMessageReceiver extends Observable<PlayerMessage> implements Observer<PlayerMessage> {

        @Override
        public void update(PlayerMessage message) {

            try{
                notifyController(message);
            }catch(IllegalArgumentException e){
                connectionToClient.asyncSend("unable to notify controller!");
            }
        }

    }


    public VirtualView(Player player, ConnectionToClient c){
        this.player=player;
        this.connectionToClient=c;
        //attach PlayerMessage Observer to ConnectionToClient interface
        c.addObserver(new PlayerMessageReceiver());
    }

    private void notifyController(PlayerMessage message){
        notify(message);
    }

    @Override
    public void update(NotifyMessages message) {

        if(message instanceof NewBoardStateMessage){

        }else if (message instanceof LoseMessage){

        }else if( message instanceof WinMessage){

        }


    }
}
