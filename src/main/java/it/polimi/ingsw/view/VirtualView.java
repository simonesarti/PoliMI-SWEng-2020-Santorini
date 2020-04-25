package it.polimi.ingsw.view;

import it.polimi.ingsw.messages.GameMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.*;
import it.polimi.ingsw.messages.InfoMessage;
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

    private PlayerMessageReceiver playerMessageReceiver;

    //this class's update is triggered by ServerSideConnection reading a player messages and notifies the virtual view itself
    private class PlayerMessageReceiver implements Observer<DataMessage> {

        @Override
        public void update(DataMessage message) {
            notifyController(message);
        }

    }

    //CONSTRUCTORS

    //EMPTY CONSTRUCTOR FOR TESTING PURPOSES ONLY!

    public VirtualView(){

    }
    public VirtualView(Player player, ServerSideConnection c){
        this.player=player;
        this.connectionToClient =c;
        playerMessageReceiver=new PlayerMessageReceiver();
        //attach PlayerMessage Observer to ServerSideConnection object
        c.addObserver(playerMessageReceiver);
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
                reportInfo(new InfoMessage(GameMessage.lose));
            }else{
                reportInfo(new InfoMessage("Player "+((LoseMessage) message).getPlayer().getNickname()+ "lost\n"));
            }

        }else if( message instanceof WinMessage){

            if(((WinMessage)message).getPlayer()==this.getPlayer()){
                reportInfo(new InfoMessage(GameMessage.win));
            }else{
                reportInfo(new InfoMessage("Player "+((WinMessage) message).getPlayer().getNickname()+ "won\n"));
            }

        }else if(message instanceof QuitMessage){

            if(((QuitMessage)message).getPlayer()==this.getPlayer()){
                reportInfo(new InfoMessage(GameMessage.quit));
                connectionToClient.removeObserver(playerMessageReceiver);
                connectionToClient.deactivate();
            }
        }


    }
}
