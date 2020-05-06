package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.messages.PlayerToGameMessages.CompleteMessages.*;
import it.polimi.ingsw.model.Colour;
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
            message.getVirtualView().reportInfo(new InfoMessage(GameMessage.eliminated));
            return;
        }

        if(model.isNotPlayerTurn(message.getPlayer())){
            message.getVirtualView().reportInfo(new InfoMessage(GameMessage.wrongTurn));
            return;
        }

        if(model.getTurnInfo().getTurnHasEnded()){
            message.getVirtualView().reportInfo(new InfoMessage(GameMessage.turnAlreadyEnded));
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

                message.getVirtualView().reportInfo(new InfoMessage(checkResult));

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
            message.getVirtualView().reportInfo(new InfoMessage(GameMessage.eliminated));
            return;
        }

        if (model.isNotPlayerTurn(message.getPlayer())) {
            message.getVirtualView().reportInfo(new InfoMessage(GameMessage.wrongTurn));
            return;
        }

        if (model.getTurnInfo().getTurnHasEnded()) {
            message.getVirtualView().reportInfo(new InfoMessage(GameMessage.turnAlreadyEnded));
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

                message.getVirtualView().reportInfo(new InfoMessage(checkResult));

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
            message.getVirtualView().reportInfo(new InfoMessage(GameMessage.eliminated));
            return;
        }

        if(model.isNotPlayerTurn(message.getPlayer())){
            message.getVirtualView().reportInfo(new InfoMessage(GameMessage.wrongTurn));
            return;
        }

        if(!model.getTurnInfo().getTurnCanEnd()){

            message.getVirtualView().reportInfo(new InfoMessage(GameMessage.turnNotEnded));
            return;
        }
        model.updateTurn(getPlayers());

    }

    //TODO
    private synchronized void quitGame(PlayerQuitChoice message){

        //player can't quit if he isn't eliminated
        if(!model.isEliminated(message.getPlayer())){
            message.getVirtualView().reportInfo(new InfoMessage(GameMessage.notEliminated));
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

    }

    private ArrayList<Player> getPlayers(){

        ArrayList<Player> players=new ArrayList<>();

        for(VirtualView virtualView:virtualViews){
            players.add(virtualView.getPlayer());
        }
        return players;
    }

    private void declaration(){

        StringBuilder s = new StringBuilder();
        s.append("THE CHOSEN CARDS FOR THIS MATCH ARE:\n\n");
        for(GodCard godCard : model.getGameDeck()){
            s.append(godCard.cardDeclaration());
        }

        for(VirtualView virtualView : virtualViews){
            virtualView.reportInfo(new InfoMessage(s.toString()));
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
    public void startGame(){

        int numberOfPlayer=virtualViews.size();

        //mancano controlli di validità della scelta iniziale e della selezione di carte già selezionate dopo

        virtualViews.get(0).reportInfo("card selection1");
        while (model.getGameDeck().size()==0){
            //waiting for first card to be selected
        }

        virtualViews.get(0).reportInfo("card selection2");
        while (model.getGameDeck().size()==1){
            //waiting for second card to be selected
        }

        if(numberOfPlayer==3){
            virtualViews.get(0).reportInfo("card selection3");
            while (model.getGameDeck().size()==2){
                //waiting for third card to be selected
            }
        }

        virtualViews.get(1).reportInfo("chose your godCard");
        //while di controllo selezione

        if(numberOfPlayer==3){
            virtualViews.get(2).reportInfo("chose your godCard");
            //while di controllo selezione
        }

        //assegnazione carta rimasta



        //declaration();
    }

    private VirtualView getVVFromPlayer(Player player){

        for(VirtualView virtualView:virtualViews){
            if(virtualView.getPlayer().equals(player)){
                return virtualView;
            }
        }
        throw new IllegalStateException("INEXISTING VV given player");
    }





    //TEST FUNCTIONS
    public Model getModel(){return model;}
    public ArrayList<VirtualView> getVirtualViews(){return virtualViews;}

}
