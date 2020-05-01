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

        startGame();

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
            //DEBUG
            System.out.println("turno già completato, non puoi");
            return;
        }

        //CHECK LOSE
        if(message.getPlayer().getGodCard().getLoseStrategy().movementLoss(model.getTurnInfo(), model.getGameBoard(), message.getPlayer(), message.getChosenWorker())) {
            model.notifyNewBoardState();
            model.notifyLoss(message.getPlayer());
            model.removeFromGame(message.getPlayer());

            //DEBUG:
            System.out.println("sconfitta");

            //TODO vittoria per sconfitta altrui
            if(model.getPlayersLeft()==1){

                model.notifyVictory(getPlayerFromColour(model.getWinnerColour()));
                endGame();

            }else {
                model.updateTurn();
                model.notifyNewTurn(getPlayerFromColour(model.getTurn()));
            }

            return;

        //if the player hasn't lost
        }else {
            //EXECUTE MOVE CHECK
            checkResult = message.getPlayer().getGodCard().getMoveStrategy().checkMove(model.getTurnInfo(), model.getGameBoard(), message.getPlayer(), message.getChosenWorker(), message.getMovingTo());

            //if check ok, execute move and win
            if (checkResult.equals(GameMessage.moveOK)){

                //EXECUTE MOVE
                nextStep = message.getPlayer().getGodCard().getMoveStrategy().move(model.getTurnInfo(), model.getGameBoard(), message.getPlayer(), message.getChosenWorker(), message.getMovingTo());
                model.notifyNewBoardState();

                //EXECUTE WIN CHECK
                if (message.getPlayer().getGodCard().getWinStrategy().checkWin(message.getPlayer(), message.getChosenWorker())) {
                    model.notifyNewBoardState();
                    model.notifyVictory(message.getPlayer());
                    //TODO altro?
                    endGame();

                    //DEBUG:
                    System.out.println("vittoria");

                    return;
                }else{
                    message.getVirtualView().reportInfo(new InfoMessage(nextStep));

                    //DEBUG
                    System.out.println(nextStep);
                }

            //if check NOT ok, report error
            }else{

                message.getVirtualView().reportInfo(new InfoMessage(checkResult));

                //DEBUG
                System.out.println("errore nella check move "+checkResult);

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

            //DEBUG
            System.out.println("turno già finito, non puoi");
            return;
        }

        //CHECK LOSE
        if(message.getPlayer().getGodCard().getLoseStrategy().buildingLoss(model.getTurnInfo(), model.getGameBoard(), message.getPlayer(), message.getChosenWorker())){
            model.notifyNewBoardState();
            model.notifyLoss(message.getPlayer());
            model.removeFromGame(message.getPlayer());
            //...
            //TODO altro?

            //DEBUG:
            System.out.println("sconfitta");

            //TODO vittoria per sconfitta altrui
            if(model.getPlayersLeft()==1){
                //....
                model.notifyVictory(getPlayerFromColour(model.getWinnerColour()));
                endGame();

            }else {
                model.updateTurn();
                model.notifyNewTurn(getPlayerFromColour(model.getTurn()));
                //...
            }

            return;

        //if player hasn't lost'
        }else{
            //EXECUTE BUILD CHECK
            checkResult = message.getPlayer().getGodCard().getBuildStrategy().checkBuild(model.getTurnInfo(), model.getGameBoard(), message.getPlayer(), message.getChosenWorker(), message.getBuildingInto(), message.getPieceType());

            //if build check ok
            if (checkResult.equals(GameMessage.buildOK)) {

                //EXECUTE BUILD
                nextStep = message.getPlayer().getGodCard().getBuildStrategy().build(model.getTurnInfo(), model.getGameBoard(), message.getPlayer(), message.getChosenWorker(), message.getBuildingInto(), message.getPieceType());
                model.notifyNewBoardState();
                message.getVirtualView().reportInfo(new InfoMessage(nextStep));
                //TODO elimina questa println sotto

                //DEBUG:
                System.out.println("Ho eseguito una build correttamente");

            //if NOT build check ok
            } else {

                message.getVirtualView().reportInfo(new InfoMessage(checkResult));

                //DEBUG
                System.out.println("errore nella check build "+checkResult);
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

            //DEBUG
            System.out.println("turno incompleto");

            message.getVirtualView().reportInfo(new InfoMessage(GameMessage.turnNotEnded));
            return;
        }

        //DEBUG
        System.out.println("turno completato con successo");

        model.updateTurn();
        model.notifyNewTurn(getPlayerFromColour(model.getTurn()));
        //TODO altro?

    }

    //TODO
    private synchronized void quitGame(PlayerQuitChoice message){

        //player can't quit if he isn't eliminated
        if(!model.isEliminated(message.getPlayer())){
            message.getVirtualView().reportInfo(new InfoMessage(GameMessage.notEliminated));
            return;
        }

        model.notifyQuit(message.getPlayer());
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

    private VirtualView getVirtualViewFromPlayer(Player player){

        for(VirtualView virtualView : virtualViews){
            if(virtualView.getPlayer().equals(player)){
                return virtualView;
            }
        }
        throw new IllegalArgumentException("INEXISTING Virtualview given the player "+player.getNickname());

    }
    private Player getPlayerFromColour(Colour colour){

        for(VirtualView virtualView : virtualViews){
            if(virtualView.getPlayer().getColour()==colour){
                return virtualView.getPlayer();
            }
        }
        throw new IllegalArgumentException("INEXISTING player given the colour "+colour);
    }



    private void startGame(){

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

    //TODO cosa succede se ho già rimosso una VV da observer di model nella quit, remove(niente) = errore?
    private void endGame(){

        //remove every observers in MVC
        for(VirtualView virtualView: virtualViews){
            virtualView.removeObserver(this);
            //model.removeObserver(virtualView);
        }

    }




    //TODO rimuovere
    //TEST
    public Controller(Model model){
        super();
        this.model=model;

    }


}
