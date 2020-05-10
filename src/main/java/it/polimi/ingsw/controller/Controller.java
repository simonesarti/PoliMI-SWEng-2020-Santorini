package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.messages.PlayerToGameMessages.CompleteMessages.*;
import it.polimi.ingsw.model.GodCard;
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
        virtualViews.get(0).reportInfo(new PossibleCardsMessage(model.getSelectionDeck().getPresentGods(),numberOfPlayers));
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
            //cardSelection((PlayerCardChoice)message);
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
/*
    public void startGame(){

        int numberOfPlayer=virtualViews.size();

        sendMessageToEveryone(virtualViews.get(0).getPlayer().getNickname()+" will choose the cards used in this match");

        virtualViews.get(0).reportInfo("select first card");
        while (model.getGameDeck().size()==0){
            //waiting for first card to be selected
        }

        virtualViews.get(0).reportInfo("select second card");
        while (model.getGameDeck().size()==1){
            //waiting for second card to be selected
        }

        if(numberOfPlayer==3){
            virtualViews.get(0).reportInfo("select third card");
            while (model.getGameDeck().size()==2){
                //waiting for third card to be selected
            }
        }

        virtualViews.get(1).reportInfo("chose your card");
        while (model.getGameDeck().size()==numberOfPlayer){
            //waiting for first card to be chosen
        }

        if(numberOfPlayer==3){
            virtualViews.get(2).reportInfo("chose your card");
            while (model.getGameDeck().size()==numberOfPlayer-1){
                //waiting for second card to be chosen
            }
        }

        //the only card left in gameDeck is assigned to player1
        virtualViews.get(0).getPlayer().setGodCard(model.getGameDeck().get(0));
        sendMessageToEveryone("Therefore "+model.getGameDeck().get(0).getGodName()+" has been assigned to "+virtualViews.get(0).getPlayer().getNickname());
        //and then removed from the list as all the others
        model.getGameDeck().remove(0);

        declaration();


    }

    private void declaration(){

        StringBuilder s = new StringBuilder();
        s.append("THE CHOSEN CARDS FOR THIS MATCH ARE:\n\n");

        for(VirtualView virtualView : virtualViews){
            s.append("PLAYER: ").append(virtualView.getPlayer().getNickname()).append("\n");
            s.append(virtualView.getPlayer().getGodCard().cardDeclaration());
        }
        sendMessageToEveryone(s.toString());

    }

    private synchronized void cardSelection(PlayerCardChoice message){

        //someone tries to chose a card while the cards are still being selected
        if(model.getGameDeck().size()<virtualViews.size() && !message.getVirtualView().equals(virtualViews.get(0))) {
            message.getVirtualView().reportInfo(new InfoMessage(virtualViews.get(0).getPlayer().getNickname() + " is still choosing the cards"));
            return;
        }

        if(model.getGameDeck().size()<virtualViews.size() && message.getVirtualView().equals(virtualViews.get(0))){
            boolean selectionResultOk;
            selectionResultOk = model.selectGameCard(message.getCardNames());
            if (!selectionResultOk) {
                message.getVirtualView().reportInfo(new InfoMessage(GameMessage.noSuchCardInSelectionDeck));
            }
            return;
        }

        if(model.getGameDeck().size()==virtualViews.size() && !message.getVirtualView().equals(virtualViews.get(1))){
            message.getVirtualView().reportInfo(new InfoMessage(virtualViews.get(0).getPlayer().getNickname() + " is still selecting his card"));
            return;
        }

        if(model.getGameDeck().size()==virtualViews.size() && message.getVirtualView().equals(virtualViews.get(1))) {
            boolean selectionResultOk;
            selectionResultOk = model.chooseCard(message.getPlayer(), message.getCardNames());
            if (!selectionResultOk) {
                message.getVirtualView().reportInfo(new InfoMessage(GameMessage.noSuchCardInGameDeck));
            } else {
                sendMessageToEveryone(message.getVirtualView().getPlayer().getNickname()+ "chose "+message.getVirtualView().getPlayer().getGodCard().getGodName());
            }
            return;
        }
    }
*/











    //TEST FUNCTIONS
    public Model getModel(){return model;}
    public ArrayList<VirtualView> getVirtualViews(){return virtualViews;}

}
