package it.polimi.ingsw.supportClasses;

import it.polimi.ingsw.messages.GameMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.LoseMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.NewBoardStateMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.NotifyMessages;
import it.polimi.ingsw.messages.GameToPlayerMessages.WinMessage;
import it.polimi.ingsw.messages.InfoMessage;
import it.polimi.ingsw.messages.PlayerToGameMessages.*;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.*;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.observe.Observable;
import it.polimi.ingsw.observe.Observer;
import it.polimi.ingsw.server.ServerSideConnection;
import it.polimi.ingsw.view.VirtualView;

/**
 * Integration tests need a VirtualView. This class is a "empty virtualview" for testing purposes
 */
public class EmptyVirtualView extends VirtualView implements Observer<NotifyMessages> {

    //ATTRIBUTES

    private Player player;

    private ServerSideConnection connectionToClient;

    //this class's update is triggered by ServerSideConnection reading a player messages and notifies the virtual view itself
    private class PlayerMessageReceiver implements Observer<DataMessage> {

        @Override
        public void update(DataMessage message) {
            System.out.println("VirtuaView's MessageReceiver's update() triggered");
        }

    }



    public Player getPlayer(){
        return player;
    }



    public void reportInfo(Object message){
        System.out.println("VirtuaView's reportInfo() triggered");
    }


    //TODO
    @Override
    public void update(NotifyMessages message) {

        if(message instanceof NewBoardStateMessage){
            System.out.println("VirtualView ha ricevuto NewBoardStateMessage");
            //reportInfo((NewBoardStateMessage)message);
        }else if (message instanceof LoseMessage){

            if(((LoseMessage) message).getPlayer()==this.getPlayer()){
                System.out.println("VirtualView ha ricevuto messaggio lose del player giocante");
                //reportInfo(new InfoMessage(GameMessage.lose));
            }else{
                System.out.println("VirtualView ha ricevuto messaggio lose di uno degli enemy del player giocante");
                //reportInfo(new InfoMessage("Player "+((LoseMessage) message).getPlayer().getNickname()+ "lost\n"));
            }

        }else if( message instanceof WinMessage){

            if(((WinMessage)message).getPlayer()==this.getPlayer()){
                System.out.println("VirtualView ha ricevuto messaggio win del player giocante");
                //reportInfo(new InfoMessage(GameMessage.win));
            }else{
                System.out.println("VirtualView ha ricevuto messaggio win di un enemy del player giocante");
                //reportInfo(new InfoMessage("Player "+((WinMessage) message).getPlayer().getNickname()+ "won\n"));
            }

        }


    }
}
