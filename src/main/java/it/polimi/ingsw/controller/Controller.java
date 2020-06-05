package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.GameToPlayerMessages.Others.GameMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.PossibleCardsMessage;
import it.polimi.ingsw.messages.PlayerToGameMessages.CompleteMessages.*;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.observe.Observer;
import it.polimi.ingsw.server.ServerSideConnection;
import it.polimi.ingsw.view.VirtualView;

import java.util.ArrayList;


/**
 * This class purpose is to implement the logic that allows a match to run. The actions performed are based on player commands,
 * received through the notify method of the player's virtualView. Once a command is verified to be legal, methods of the
 * class Model are called, to update the game status
 */
public class Controller implements Observer<PlayerMessage>{

    private final Model model;
    private final ArrayList<VirtualView> virtualViews=new ArrayList<>();

    /**
     * creates model, passing the number of players
     * creates one virtualView for each player
     * every virtualView is added as model's observer, and controller is added as observer for every virtualView
     * colours are assigned to the players
     * a card selection message is sent to to the first player
     *
     * @param players is the list of players in the game
     * @param connections is the list that contains the Connection objects associated to each player
     */
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

        //assign colours to players and notifies them
        model.assignColour(players);
        model.notifyInfoMessage(null,getColoursAssignedString());

        //notifies that the first player will chose the cards used
        model.notifyInfoMessage(null,"Player "+virtualViews.get(0).getPlayer().getNickname()+ " will choose this match cards");

        model.notifyCardRequest(virtualViews.get(0).getPlayer(),new PossibleCardsMessage(model.getCompleteDeck().getPresentGods(numberOfPlayers),numberOfPlayers));
    }   

    /**
     * Invokes one of Controller's methods based on the message's subclass
     *
     * @param message PlayerMessage is the message containing the action the player wants to perform
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

        else if(message instanceof PlayerStartingPositionChoice){
            positionSelection((PlayerStartingPositionChoice)message);
        }

    }

    /**
     * makes sure the player who sent the command is allowed to move, if not, an error message is notified.
     * The same thing happens if the player is allowed to move but the move is not permitted
     * If the player cannot move, he is eliminated from the match, and this could cause his/her opponent to win
     * After a successful move, the win condition is verified. If true, the match ends.
     * If the player didn't win, the next step in the turn is notified to him/her
     * @param message contains the data regarding the which worker to move and where
     */
    private synchronized void performMove(PlayerMovementChoice message) {

        String checkResult;
        String nextStep;

        //eliminated player can't execute this command
        if(model.isEliminated(message.getPlayer())){
            model.notifyErrorMessage(message.getPlayer(),GameMessage.eliminated);
            return;
        }

        if(model.isNotPlayerTurn(message.getPlayer())){
            model.notifyErrorMessage(message.getPlayer(),GameMessage.wrongTurn);
            return;
        }

        if(model.getTurnInfo().getTurnHasEnded()){
            model.notifyErrorMessage(message.getPlayer(),GameMessage.turnAlreadyEnded);
            return;
        }

        //CHECK LOSE
        if(model.performLoseCheck(message.getPlayer(),message.getChosenWorker(),"move")){

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
                    model.notifyInfoMessage(message.getPlayer(),nextStep);

                }

            //if check NOT ok, report error
            }else{
                model.notifyErrorMessage(message.getPlayer(),checkResult);

            }
        }
    }

    /**
     * makes sure the player who sent the command is allowed to move, if not, an error message is notified.
     * The same thing happens if the player is allowed to build but the action is not permitted
     * If the player cannot build, he is eliminated from the match, and this could cause his/her opponent to win
     * After a successful build, The next step in the turn is the notified to him/her
     * @param message contains the data regarding the which worker to build with and where to build
     */
    private synchronized void performBuild(PlayerBuildChoice message) {

        String checkResult;
        String nextStep;

        //eliminated player can't execute this command
        if(model.isEliminated(message.getPlayer())){
            model.notifyErrorMessage(message.getPlayer(),GameMessage.eliminated);
            return;
        }

        if (model.isNotPlayerTurn(message.getPlayer())) {
            model.notifyErrorMessage(message.getPlayer(),GameMessage.wrongTurn);
            return;
        }

        if (model.getTurnInfo().getTurnHasEnded()) {
            model.notifyErrorMessage(message.getPlayer(),GameMessage.turnAlreadyEnded);
            return;

        }

        //CHECK LOSE
        if(model.performLoseCheck(message.getPlayer(),message.getChosenWorker(),"build")){

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
                model.notifyInfoMessage(message.getPlayer(),nextStep);

            //if NOT build check ok
            } else {
                model.notifyErrorMessage(message.getPlayer(),checkResult);

            }
        }
    }

    /**
     * Checks that the player can end is turn, if not, an error message is notified
     * Updates the turn
     * @param message represent the player's will to end his/her turn
     */
    private synchronized void endTurn(PlayerEndOfTurnChoice message){

        //eliminated player can't execute this command
        if(model.isEliminated(message.getPlayer())){
            model.notifyErrorMessage(message.getPlayer(),GameMessage.eliminated);
            return;
        }

        if(model.isNotPlayerTurn(message.getPlayer())){
            model.notifyErrorMessage(message.getPlayer(),GameMessage.wrongTurn);
            return;
        }

        if(!model.getTurnInfo().getTurnCanEnd()){
            model.notifyErrorMessage(message.getPlayer(),GameMessage.turnNotEnded);
            return;
        }

        model.updateTurn(getPlayers());

    }


    /**
     * checks if the player is eliminated, only in that case he is allowed to quit
     * Removes the player from the game. It causes the player to disconnect and the observer objects to detach
     * @param message represent the player's will to quit from the game
     */
    private synchronized void quitGame(PlayerQuitChoice message){

        //player can't quit if he isn't eliminated
        if(!model.isEliminated(message.getPlayer())){
            model.notifyErrorMessage(message.getPlayer(),GameMessage.notEliminated);
            return;
        }

        model.notifyInfoMessage(message.getPlayer(),GameMessage.quit);
        model.removeObserver(message.getVirtualView());
        message.getVirtualView().leave();

    }


    /**
     * This methods is responsible for the cards' selection. It calls the model's methods responsible for the selection
     * of the correct card from the game deck, then the selected card is assigned to the player. After a card has been chosen, a
     * selection request is sent to the next player. The last Player's card is assigned automatically.
     * In the end, the starting position request is sent to the youngest player
     *
     * @param message contains the card chosen by the player
     */
    private synchronized void cardSelection(PlayerCardChoice message) {

        if(message.getVirtualView().equals(virtualViews.get(0))){

            model.selectGameCards(message.getCardNames());
            model.notifyInfoMessage(null,"Player "+virtualViews.get(1).getPlayer().getNickname()+" will now choose his card");
            model.notifyCardRequest(virtualViews.get(1).getPlayer(),new PossibleCardsMessage(model.getGameDeck().getGameGods(),1));
            return;
        }

        if(message.getVirtualView().equals(virtualViews.get(1))){

            //player 2 chooses his card
            model.chooseOwnCard(message.getPlayer(),message.getCardNames()[0]);
            model.notifyInfoMessage(null,"Player "+virtualViews.get(1).getPlayer().getNickname()+" chose "+virtualViews.get(1).getPlayer().getGodCard().getGodName());

            if(virtualViews.size()==3){
                //selection reported and card list sent to player 3
                model.notifyInfoMessage(null,"Player "+virtualViews.get(2).getPlayer().getNickname()+" will now choose his card");
                model.notifyCardRequest(virtualViews.get(2).getPlayer(),new PossibleCardsMessage(model.getGameDeck().getGameGods(),1));

            }else{

                assignLastCard();
            }
            return;
        }

        if(message.getVirtualView().equals(virtualViews.get(2))){
            //player 3 chooses his card
            model.chooseOwnCard(message.getPlayer(),message.getCardNames()[0]);
            model.notifyInfoMessage(null,"Player "+virtualViews.get(2).getPlayer().getNickname()+" chose "+virtualViews.get(2).getPlayer().getGodCard().getGodName());
            assignLastCard();
            return;
        }


    }

    /**
     * Checks if the position can be selected, in that case the model is updated and a new request
     * is sent to the next player. In the opposite case, an error message is notified and a new request is sent.
     * @param message contains the starting position of the player's two workers
     */
    private synchronized void positionSelection(PlayerStartingPositionChoice message){

        String checkOk=model.checkStartingPlacement(message.getX1(),message.getY1(),message.getX2(),message.getY2());

        if(!checkOk.equals(GameMessage.placementOk)){
            model.notifyErrorMessage(message.getPlayer(),checkOk);
            model.notifyPositionRequest(message.getPlayer());

        }else{

            Player nextPlayer;

            if(message.getPlayer().getColour()==Colour.RED){
                model.placeOnBoard(message.getPlayer(),message.getX1(),message.getY1(),message.getX2(),message.getY2());

                nextPlayer=virtualViews.get(model.getIndexFromColour(getPlayers(),Colour.BLUE)).getPlayer();
                model.notifyInfoMessage(null,"Now Player "+model.getPlayerFromColour(getPlayers(),Colour.BLUE).getNickname()+" will choose");
                model.notifyPositionRequest(nextPlayer);
                return;
            }

            if(message.getPlayer().getColour()==Colour.BLUE){
                model.placeOnBoard(message.getPlayer(),message.getX1(),message.getY1(),message.getX2(),message.getY2());
                if(virtualViews.size()==3){
                    nextPlayer=virtualViews.get(model.getIndexFromColour(getPlayers(),Colour.PURPLE)).getPlayer();
                    model.notifyInfoMessage(null,"Now Player "+model.getPlayerFromColour(getPlayers(),Colour.PURPLE).getNickname()+" will choose");
                    model.notifyPositionRequest(nextPlayer);
                }else{
                    model.notifyGameStart(getPlayers());
                }
                return;
            }

            if(message.getPlayer().getColour()==Colour.PURPLE){
                model.placeOnBoard(message.getPlayer(),message.getX1(),message.getY1(),message.getX2(),message.getY2());
                model.notifyGameStart(getPlayers());
                return;
            }


        }


    }


    /**
     * This method uses the VirtualView list to get the players in the match
     * @return the list of players in the match
     */
    private ArrayList<Player> getPlayers(){

        ArrayList<Player> players=new ArrayList<>();

        for(VirtualView virtualView:virtualViews){
            players.add(virtualView.getPlayer());
        }
        return players;
    }

    /**
     * @return a string which tells the player-color association
     */
    private String getColoursAssignedString(){
        StringBuilder s=new StringBuilder();
        for(VirtualView virtualView : virtualViews){
            s.append("Player ").append(virtualView.getPlayer().getNickname()).append(" will be colour ").append(virtualView.getPlayer().getColour().toString()).append("\n");
        }

        return s.toString();
    }

    /**
     * This method is used by the CardSelection method to assign the last card to the last player, and to send the first
     * starting position request
     */
    private void assignLastCard(){
        virtualViews.get(0).getPlayer().setGodCard(model.getGameDeck().getDeck().get(0));
        model.getGameDeck().getDeck().remove(0);
        model.notifyInfoMessage(null,"Therefore "+virtualViews.get(0).getPlayer().getGodCard().getGodName()+" has been assigned to "+virtualViews.get(0).getPlayer().getNickname());

        declaration();
        model.notifyNewBoardState();

        int index=model.getIndexFromColour(getPlayers(), Colour.RED);
        model.notifyInfoMessage(null,"From the youngest player, you will be required to select the starting position of your workers.\nPlayer "+model.getPlayerFromColour(getPlayers(),Colour.RED).getNickname()+" will start");
        model.notifyPositionRequest(virtualViews.get(index).getPlayer());
    }

    /**
     * creates a string which groups the descriptions of the GodCards used in the game
     */
    private void declaration() {

        StringBuilder s = new StringBuilder();
        s.append("THE CHOSEN CARDS FOR THIS MATCH ARE:\n\n");

        for (VirtualView virtualView : virtualViews) {
            s.append("PLAYER: ").append(virtualView.getPlayer().getNickname()).append("\n");
            s.append(virtualView.getPlayer().getGodCard().cardDeclaration());
        }
        model.notifyInfoMessage(null,s.toString());
    }

    /**
     * detaches all the observers left, because the match has ended.
     */
    private void endGame(){

        //removes every observer in MVC
        for(VirtualView virtualView: virtualViews){
            virtualView.removeObserver(this);
            if(virtualView.isObservingModel()) {
                model.removeObserver(virtualView);
                virtualView.stopObservingModel();
            }
        }

    }















    //TEST FUNCTIONS
    public Model getModel(){return model;}
    public ArrayList<VirtualView> getVirtualViews(){return virtualViews;}

}
