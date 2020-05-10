package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.GameToPlayerMessages.Others.ErrorMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.GameMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.InfoMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.PossibleCardsMessage;
import it.polimi.ingsw.messages.PlayerToGameMessages.CompleteMessages.*;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.observe.Observer;
import it.polimi.ingsw.server.ServerSideConnection;
import it.polimi.ingsw.view.VirtualView;

import java.util.ArrayList;

public class Controller implements Observer<PlayerMessage>{

    private final Model model;
    private final ArrayList<VirtualView> virtualViews=new ArrayList<>();

    public Controller(ArrayList<Player> players, ArrayList<ServerSideConnection> connections){

        int numberOfPlayers=players.size();

        //creates model passing the number of players
        model=new Model(numberOfPlayers);
        
        //creates one virtualView for each player
        for(int i=0;i<numberOfPlayers;i++){
            virtualViews.add(new VirtualView(players.get(i),connections.get(i)));
        }
        
        //every virtualView is added as model's observer, and controller is added as observer for every virtualView
        for (VirtualView virtualView : virtualViews) {
            model.addObserver(virtualView);
            virtualView.addObserver(this);
        }

        //assign colours to players
        model.assignColour(players);

        sendMessageToEveryone(new InfoMessage("Player "+virtualViews.get(0).getPlayer().getNickname()+ " will choose this match cards"));
        virtualViews.get(0).reportInfo(new PossibleCardsMessage(model.getSelectionDeck().getPresentGods(numberOfPlayers),numberOfPlayers));
    }

    /**
     * Checks if it's the player's turn and calls the player's GodCard's MoveStrategy methods;
     *
     * @param message oggetto-messaggio contentente le informazioni riguardanti lo spostamento
     */
    private synchronized void performMove(PlayerMovementChoice message) {

        String checkResult;
        String nextStep;

        //eliminated player can't execute this command
        if(model.isEliminated(message.getPlayer())){
            message.getVirtualView().reportInfo(new ErrorMessage(GameMessage.eliminated));
            return;
        }

        if(model.isNotPlayerTurn(message.getPlayer())){
            message.getVirtualView().reportInfo(new ErrorMessage(GameMessage.wrongTurn));
            return;
        }

        if(model.getTurnInfo().getTurnHasEnded()){
            message.getVirtualView().reportInfo(new ErrorMessage(GameMessage.turnAlreadyEnded));
            return;
        }

        //CHECK LOSE
        if(model.performLoseCheck(message.getPlayer(),message.getChosenWorker(),"move")){

            //TODO vittoria per sconfitta altrui
            if(model.getPlayersLeft()==1){

                model.declareWinner(getPlayers());
                endGame();

            }else {

                model.updateTurn(getPlayers());
            }

            return;

        //if the player hasn't lost
        }else{
            //EXECUTE MOVE CHECK
            checkResult=model.performMoveCheck(message.getPlayer(), message.getChosenWorker(), message.getMovingTo());

            //if check ok, execute move and win
            if (checkResult.equals(GameMessage.moveOK)){

                //EXECUTE MOVE
                nextStep = model.performMove(message.getPlayer(), message.getChosenWorker(), message.getMovingTo());

                //EXECUTE WIN CHECK
                if(model.performWinCheck(message.getPlayer(),message.getChosenWorker())){

                    endGame();

                    return;
                }else{
                    message.getVirtualView().reportInfo(new InfoMessage(nextStep));

                }

            //if check NOT ok, report error
            }else{

                message.getVirtualView().reportInfo(new ErrorMessage(checkResult));

            }
        }
    }

    /**
     *
     * Checks if it's the player's turn and calls the player's GodCard's BuildStrategy
     *
     * @param message messaggio di tipo PlayerBuildChoice
     */
    private synchronized void performBuild(PlayerBuildChoice message) {

        String checkResult;
        String nextStep;

        //eliminated player can't execute this command
        if(model.isEliminated(message.getPlayer())){
            message.getVirtualView().reportInfo(new ErrorMessage(GameMessage.eliminated));
            return;
        }

        if (model.isNotPlayerTurn(message.getPlayer())) {
            message.getVirtualView().reportInfo(new ErrorMessage(GameMessage.wrongTurn));
            return;
        }

        if (model.getTurnInfo().getTurnHasEnded()) {
            message.getVirtualView().reportInfo(new ErrorMessage(GameMessage.turnAlreadyEnded));
            return;

        }

        //CHECK LOSE
        if(model.performLoseCheck(message.getPlayer(),message.getChosenWorker(),"build")){

            //TODO vittoria per sconfitta altrui
            if(model.getPlayersLeft()==1){

                model.declareWinner(getPlayers());
                endGame();

            }else {

                model.updateTurn(getPlayers());
            }

            return;

        //if player hasn't lost'
        }else{
            //EXECUTE BUILD CHECK
            checkResult=model.performBuildCheck(message.getPlayer(), message.getChosenWorker(), message.getBuildingInto(), message.getPieceType());

            //if build check ok
            if (checkResult.equals(GameMessage.buildOK)) {

                //EXECUTE BUILD
                nextStep=model.performBuild(message.getPlayer(), message.getChosenWorker(), message.getBuildingInto(), message.getPieceType());
                message.getVirtualView().reportInfo(new InfoMessage(nextStep));

            //if NOT build check ok
            } else {

                message.getVirtualView().reportInfo(new ErrorMessage(checkResult));

            }
        }
    }

    //TODO
    /**
     * Checks if it's player's turn, checks this player win conditions and next player lose conditions. Updates turn
     * @param message PlayerEndOfTurnChoice message
     */
    private synchronized void endTurn(PlayerEndOfTurnChoice message){

        //eliminated player can't execute this command
        if(model.isEliminated(message.getPlayer())){
            message.getVirtualView().reportInfo(new ErrorMessage(GameMessage.eliminated));
            return;
        }

        if(model.isNotPlayerTurn(message.getPlayer())){
            message.getVirtualView().reportInfo(new ErrorMessage(GameMessage.wrongTurn));
            return;
        }

        if(!model.getTurnInfo().getTurnCanEnd()){

            message.getVirtualView().reportInfo(new ErrorMessage(GameMessage.turnNotEnded));
            return;
        }
        model.updateTurn(getPlayers());

    }

    //TODO
    private synchronized void quitGame(PlayerQuitChoice message){

        //player can't quit if he isn't eliminated
        if(!model.isEliminated(message.getPlayer())){
            message.getVirtualView().reportInfo(new ErrorMessage(GameMessage.notEliminated));
            return;
        }

        message.getVirtualView().reportInfo(new InfoMessage(GameMessage.quit));
        message.getVirtualView().leave();
        model.removeObserver(message.getVirtualView());
    }


    /**
     * Invokes Controller's methods on the basis of message's subclass
     *
     * @param message PlayerMessage
     */
    @Override
    public void update(PlayerMessage message) {

        if(message instanceof PlayerMovementChoice){
            performMove((PlayerMovementChoice) message);
        }

        else if (message instanceof PlayerBuildChoice){
            performBuild((PlayerBuildChoice) message);
        }

        else if(message instanceof PlayerEndOfTurnChoice){
            endTurn((PlayerEndOfTurnChoice) message);
        }

        else if(message instanceof PlayerQuitChoice){
            quitGame((PlayerQuitChoice)message);

        }

        else if(message instanceof PlayerCardChoice){
            cardSelection((PlayerCardChoice)message);
        }

    }


    private ArrayList<Player> getPlayers(){

        ArrayList<Player> players=new ArrayList<>();

        for(VirtualView virtualView:virtualViews){
            players.add(virtualView.getPlayer());
        }
        return players;
    }

    private void sendMessageToEveryone(Object message){
        for(VirtualView virtualView : virtualViews){
            //sends only to people who are still in-game
            if(virtualView.isObservingModel()){
                virtualView.reportInfo(message);
            }
        }
    }

    private void endGame(){

        //removes every observer in MVC
        for(VirtualView virtualView: virtualViews){
            virtualView.removeObserver(this);
            if(virtualView.isObservingModel()) {
                model.removeObserver(virtualView);
            }
        }

    }







    //TODO WORK IN PROGRESS


    private synchronized void cardSelection(PlayerCardChoice message) {

        if(message.getVirtualView().equals(virtualViews.get(0))){

            model.selectGameCards(message.getCardNames());
            sendMessageToEveryone(new InfoMessage("Player "+virtualViews.get(1).getPlayer().getNickname()+" will now choose his card"));
            virtualViews.get(1).reportInfo(new PossibleCardsMessage(model.getGameDeck().getGameGods(),1));
            return;
        }

        if(message.getVirtualView().equals(virtualViews.get(1))){

            //player 2 chooses his card
            model.chooseOwnCard(message.getPlayer(),message.getCardNames()[0]);
            sendMessageToEveryone(new InfoMessage("Player "+virtualViews.get(1).getPlayer().getNickname()+" chose "+virtualViews.get(1).getPlayer().getGodCard().getGodName()));

            if(virtualViews.size()==3){
                //selection reported and card list sent to player 3
                sendMessageToEveryone(new InfoMessage("Player "+virtualViews.get(2).getPlayer().getNickname()+" will now choose his card"));
                virtualViews.get(2).reportInfo(new PossibleCardsMessage(model.getGameDeck().getGameGods(),1));
            }else{

                assignLastCard();
            }
            return;
        }

        if(message.getVirtualView().equals(virtualViews.get(2))){
            //player 3 chooses his card
            model.chooseOwnCard(message.getPlayer(),message.getCardNames()[0]);
            sendMessageToEveryone(new InfoMessage("Player "+virtualViews.get(2).getPlayer().getNickname()+" chose "+virtualViews.get(2).getPlayer().getGodCard().getGodName()));
            assignLastCard();
            return;
        }


    }

    private void assignLastCard(){
        virtualViews.get(0).getPlayer().setGodCard(model.getGameDeck().getDeck().get(0));
        sendMessageToEveryone(new InfoMessage("Therefore "+virtualViews.get(0).getPlayer().getGodCard().getGodName()+" has been assigned to "+virtualViews.get(0).getPlayer().getNickname()));
        declaration();
    }

    private void declaration() {

        StringBuilder s = new StringBuilder();
        s.append("THE CHOSEN CARDS FOR THIS MATCH ARE:\n\n");

        for (VirtualView virtualView : virtualViews) {
            s.append("PLAYER: ").append(virtualView.getPlayer().getNickname()).append("\n");
            s.append(virtualView.getPlayer().getGodCard().cardDeclaration());
        }
        sendMessageToEveryone(new InfoMessage(s.toString()));
    }













    //TEST FUNCTIONS
    public Model getModel(){return model;}
    public ArrayList<VirtualView> getVirtualViews(){return virtualViews;}

}
