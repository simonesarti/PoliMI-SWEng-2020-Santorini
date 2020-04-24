package it.polimi.ingsw.view;

import it.polimi.ingsw.messages.GameToPlayerMessages.LoseMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.NewBoardStateMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.NotifyMessages;
import it.polimi.ingsw.messages.GameToPlayerMessages.WinMessage;
import it.polimi.ingsw.messages.PlayerToGameMessages.*;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.*;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.observe.Observable;
import it.polimi.ingsw.observe.Observer;
import it.polimi.ingsw.server.ServerSideConnection;

public class VirtualView extends Observable<PlayerMessage> implements Observer<NotifyMessages> {

    //ATTRIBUTES

    private Player player;

    private ServerSideConnection connectionToClient;

    //this class's update is triggered by ServerSideConnection reading a player messages and notifies the virtual view itself
    private class PlayerMessageReceiver implements Observer<DataMessage> {

        @Override
        public void update(DataMessage message) {
            notifyController(message);
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

    private void notifyController(DataMessage message){

        if(message instanceof MoveData){
            notify(new PlayerMovementChoice(this,player,(MoveData)message));
        }else if(message instanceof BuildData){
            notify(new PlayerBuildChoice(this,player,(BuildData)message));
        }else if(message instanceof EndChoice){
            notify(new PlayerEndOfTurnChoice(this,player));
        }else if(message instanceof QuitChoice){
            notify(new PlayerQuitChoice(this,player));
        }
    }

    public void reportInfo(Object message){
        connectionToClient.asyncSend(message);
    }

    //TODO
    @Override
    public void update(NotifyMessages message) {

        if(message instanceof NewBoardStateMessage){
            reportInfo((NewBoardStateMessage)message);
        }else if (message instanceof LoseMessage){

            if(((LoseMessage) message).getPlayer()==this.getPlayer()){

            }

        }else if( message instanceof WinMessage){

            if(((WinMessage)message).getPlayer()==this.getPlayer()){

            }else{

            }

        }


    }
}
