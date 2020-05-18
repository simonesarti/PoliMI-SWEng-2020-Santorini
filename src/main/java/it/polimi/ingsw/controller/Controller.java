package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.GameToPlayerMessages.Others.*;
import it.polimi.ingsw.messages.PlayerToGameMessages.CompleteMessages.*;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.observe.Observer;
import it.polimi.ingsw.server.ServerSideConnection;
import it.polimi.ingsw.view.VirtualView;

import java.util.ArrayList;

public class Controller implements Observer<PlayerMessage>{

    private final Model model;
    private final ArrayList<VirtualView> virtualViews=new ArrayList<>();

    /**
     * creates model passing the number of players
     * creates one virtualView for each player
     * every virtualView is added as model's observer, and controller is added as observer for every virtualView
     * assign colours to players
     * sends card choice message
     *
     * @param players
     * @param connections
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

        //assign colours to players
        model.assignColour(players);
        sendColoursAssigned();

        sendMessageToEveryone(new InfoMessage("Player "+virtualViews.get(0).getPlayer().getNickname()+ " will choose this match cards"));
        virtualViews.get(0).reportToClient(new PossibleCardsMessage(model.getCompleteDeck().getPresentGods(numberOfPlayers),numberOfPlayers));
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

        else if(message instanceof PlayerStartingPositionChoice){
            positionSelection((PlayerStartingPositionChoice)message);
        }

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
            message.getVirtualView().reportToClient(new ErrorMessage(GameMessage.eliminated));
            return;
        }

        if(model.isNotPlayerTurn(message.getPlayer())){
            message.getVirtualView().reportToClient(new ErrorMessage(GameMessage.wrongTurn));
            return;
        }

        if(model.getTurnInfo().getTurnHasEnded()){
            message.getVirtualView().reportToClient(new ErrorMessage(GameMessage.turnAlreadyEnded));
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
                    message.getVirtualView().reportToClient(new InfoMessage(nextStep));

                }

            //if check NOT ok, report error
            }else{

                message.getVirtualView().reportToClient(new ErrorMessage(checkResult));

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
            message.getVirtualView().reportToClient(new ErrorMessage(GameMessage.eliminated));
            return;
        }

        if (model.isNotPlayerTurn(message.getPlayer())) {
            message.getVirtualView().reportToClient(new ErrorMessage(GameMessage.wrongTurn));
            return;
        }

        if (model.getTurnInfo().getTurnHasEnded()) {
            message.getVirtualView().reportToClient(new ErrorMessage(GameMessage.turnAlreadyEnded));
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
                message.getVirtualView().reportToClient(new InfoMessage(nextStep));

            //if NOT build check ok
            } else {

                message.getVirtualView().reportToClient(new ErrorMessage(checkResult));

            }
        }
    }

    /**
     * Checks if it's player's turn, checks this player win conditions and next player lose conditions. Updates turn
     * @param message PlayerEndOfTurnChoice message
     */
    private synchronized void endTurn(PlayerEndOfTurnChoice message){

        //eliminated player can't execute this command
        if(model.isEliminated(message.getPlayer())){
            message.getVirtualView().reportToClient(new ErrorMessage(GameMessage.eliminated));
            return;
        }

        if(model.isNotPlayerTurn(message.getPlayer())){
            message.getVirtualView().reportToClient(new ErrorMessage(GameMessage.wrongTurn));
            return;
        }

        if(!model.getTurnInfo().getTurnCanEnd()){

            message.getVirtualView().reportToClient(new ErrorMessage(GameMessage.turnNotEnded));
            return;
        }

        model.updateTurn(getPlayers());

    }

    private synchronized void quitGame(PlayerQuitChoice message){

        //player can't quit if he isn't eliminated
        if(!model.isEliminated(message.getPlayer())){
            message.getVirtualView().reportToClient(new ErrorMessage(GameMessage.notEliminated));
            return;
        }

        message.getVirtualView().reportToClient(new InfoMessage(GameMessage.quit));
        model.removeObserver(message.getVirtualView());
        message.getVirtualView().leave();

    }

    private synchronized void cardSelection(PlayerCardChoice message) {

        if(message.getVirtualView().equals(virtualViews.get(0))){

            model.selectGameCards(message.getCardNames());
            sendMessageToEveryone(new InfoMessage("Player "+virtualViews.get(1).getPlayer().getNickname()+" will now choose his card"));
            virtualViews.get(1).reportToClient(new PossibleCardsMessage(model.getGameDeck().getGameGods(),1));
            return;
        }

        if(message.getVirtualView().equals(virtualViews.get(1))){

            //player 2 chooses his card
            model.chooseOwnCard(message.getPlayer(),message.getCardNames()[0]);
            sendMessageToEveryone(new InfoMessage("Player "+virtualViews.get(1).getPlayer().getNickname()+" chose "+virtualViews.get(1).getPlayer().getGodCard().getGodName()));

            if(virtualViews.size()==3){
                //selection reported and card list sent to player 3
                sendMessageToEveryone(new InfoMessage("Player "+virtualViews.get(2).getPlayer().getNickname()+" will now choose his card"));
                virtualViews.get(2).reportToClient(new PossibleCardsMessage(model.getGameDeck().getGameGods(),1));
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

    private synchronized void positionSelection(PlayerStartingPositionChoice message){

        String checkOk=model.checkStartingPlacement(message.getX1(),message.getY1(),message.getX2(),message.getY2());

        if(!checkOk.equals(GameMessage.placementOk)){
            message.getVirtualView().reportToClient(new ErrorMessage(checkOk));
            message.getVirtualView().reportToClient(new StartingPositionRequestMessage());

        }else{

            if(message.getPlayer().getColour()==Colour.WHITE){
                model.placeOnBoard(message.getPlayer(),message.getX1(),message.getY1(),message.getX2(),message.getY2());
                virtualViews.get(model.getIndexFromColour(getPlayers(),Colour.BLUE)).reportToClient(new StartingPositionRequestMessage());
                return;
            }

            if(message.getPlayer().getColour()==Colour.BLUE){
                model.placeOnBoard(message.getPlayer(),message.getX1(),message.getY1(),message.getX2(),message.getY2());
                if(virtualViews.size()==3){
                    virtualViews.get(model.getIndexFromColour(getPlayers(),Colour.GREY)).reportToClient(new StartingPositionRequestMessage());
                }else{
                    sendMessageToEveryone(new GameStartMessage());
                }
                return;
            }

            if(message.getPlayer().getColour()==Colour.GREY){
                model.placeOnBoard(message.getPlayer(),message.getX1(),message.getY1(),message.getX2(),message.getY2());
                sendMessageToEveryone(new GameStartMessage());
                return;
            }


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
                virtualView.reportToClient(message);
            }
        }
    }

    private void sendColoursAssigned(){
        StringBuilder s=new StringBuilder();
        for(VirtualView virtualView : virtualViews){
            s.append("Player ").append(virtualView.getPlayer().getNickname()).append(" will be colour ").append(virtualView.getPlayer().getColour().toString()).append("\n");
        }

        sendMessageToEveryone(new InfoMessage(s.toString()));
    }

    private void assignLastCard(){
        virtualViews.get(0).getPlayer().setGodCard(model.getGameDeck().getDeck().get(0));
        model.getGameDeck().getDeck().remove(0);
        sendMessageToEveryone(new InfoMessage("Therefore "+virtualViews.get(0).getPlayer().getGodCard().getGodName()+" has been assigned to "+virtualViews.get(0).getPlayer().getNickname()));
        declaration();

        int index=model.getIndexFromColour(getPlayers(), Colour.WHITE);
        sendMessageToEveryone(new InfoMessage("From the youngest player, you will be required to select the starting position of your workers"));
        virtualViews.get(index).reportToClient(new StartingPositionRequestMessage());
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
