package it.polimi.ingsw.model;

import it.polimi.ingsw.messages.GameToPlayerMessages.Notify.*;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.GameMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.PossibleCardsMessage;
import it.polimi.ingsw.model.strategy.CheckSupportFunctions;
import it.polimi.ingsw.observe.Observable;

import java.util.ArrayList;

/**
 * This class contains an instance of Gameboard and schedules players' turns.
 * It has all the information regarding the state of the game
 */
public class Model extends Observable<NotifyMessages> {

    private final GameBoard gameboard;
    private final Deck completeDeck;
    private final Deck gameDeck;
    private final TurnInfo turnInfo;
    private Colour turn;
    private int playersLeft;
    private final boolean[] eliminated=new boolean[3];

//----------------------------------------------------------------------------------------------------------

    /**
     * the gameboard object and turnInfo object are instantiated.
     * Player red starts, therefore the turn is assigned to him
     * If there are only two players, the third inexisting player is set as already eliminated from the game
     * Complete deck is initialized so that it contains all the possible godCards, and the gameDeck is created
     * (it will contain the cards chosen for the game)
     * @param numberOfPlayers is the number of players in the game
     */
    public Model(int numberOfPlayers){
        gameboard = new GameBoard();
        turnInfo=new TurnInfo();
        turn=Colour.RED;

        playersLeft=numberOfPlayers;

        if(numberOfPlayers==2){
            eliminated[2]=true;
        }

        completeDeck =new Deck();
        completeDeck.fill();
        gameDeck=new Deck();

    }

    public GameBoard getGameBoard(){return gameboard;}

    public TurnInfo getTurnInfo(){return turnInfo;}

    public Deck getCompleteDeck() {
        return completeDeck;
    }
    public Deck getGameDeck() {
        return gameDeck;
    }

    /**
     * this function assigns colours to the players in the game
     * @param players is the list of player in the game
     */
    public void assignColour(ArrayList<Player> players){

        if(players.size()==2){
            assignColour2(players.get(0),players.get(1));
        }else{
            assignColour3(players.get(0),players.get(1),players.get(2));
        }
    }

    /**
     * this function assigns colours to the players passed, from the youngest to the oldest
     * @param player1 is the first player
     * @param player2 is the second player
     */
    private void assignColour2(Player player1, Player player2){

        if(player1.getBirthday().compareTo(player2.getBirthday())>=0){
            player1.setColour(Colour.RED);
            player2.setColour(Colour.BLUE);
        }else{
            player2.setColour(Colour.RED);
            player1.setColour(Colour.BLUE);
        }
    }

    /**
     * this function assigns colours to the players passed, from the youngest to the oldest
     * @param player1 is the first player
     * @param player2 is the second player
     * @param player3 is the third player
     */
    private void assignColour3(Player player1, Player player2,Player player3){

        if(player1.getBirthday().compareTo(player2.getBirthday()) >=0 &&
           player1.getBirthday().compareTo(player3.getBirthday()) >=0){

            player1.setColour(Colour.RED);

            if(player2.getBirthday().compareTo(player3.getBirthday())>=0) {
                player2.setColour(Colour.BLUE);
                player3.setColour(Colour.PURPLE);
            }else{
                player3.setColour(Colour.BLUE);
                player2.setColour(Colour.PURPLE);
            }

        }else if(player2.getBirthday().compareTo(player1.getBirthday())>=0 &&
                 player2.getBirthday().compareTo(player3.getBirthday())>=0){

            player2.setColour(Colour.RED);

            if(player1.getBirthday().compareTo(player3.getBirthday())>=0){
                player1.setColour(Colour.BLUE);
                player3.setColour(Colour.PURPLE);
            }else{
                player3.setColour(Colour.BLUE);
                player1.setColour(Colour.PURPLE);
            }
        }else{

            player3.setColour(Colour.RED);

            if(player1.getBirthday().compareTo(player2.getBirthday())>=0){
                player1.setColour(Colour.BLUE);
                player2.setColour((Colour.PURPLE));
            }else{
                player2.setColour(Colour.BLUE);
                player1.setColour(Colour.PURPLE);
            }
        }

    }

    /**
     * Checks that current player's colour is equals to current turn's colour or not
     * @param player is the player to test
     * @return boolean that says if it not that player's turn
     */
    public boolean isNotPlayerTurn(Player player){ return player.getColour() != turn;}

    public Colour getTurn(){
        return turn;
    }

    /**
     * updates the turn based on the order of the player, and resets turnInfo
     * @param players is the list of players in the game
    */
    public void updateTurn(ArrayList<Player> players) {

        updateTurnColour();

        turnInfo.turnInfoReset();

        notifyNewTurn(getPlayerFromColour(players,turn));
    }

    /**
     * updates the turn colour based on the players still in game
     */
    private void updateTurnColour(){

        switch(turn){

            case RED:
                if(!eliminated[1]) turn=Colour.BLUE;
                else if (!eliminated[2]) turn=Colour.PURPLE;
                else turn=Colour.RED;
                break;


            case BLUE:
                if(!eliminated[2]) turn=Colour.PURPLE;
                else if(!eliminated[0]) turn=Colour.RED;
                else turn=Colour.BLUE;
                break;

            case PURPLE:
                if(!eliminated[0]) turn=Colour.RED;
                else if(!eliminated[1]) turn=Colour.BLUE;
                else turn=Colour.PURPLE;
                break;

            default:
                break;
        }
    }

    /**
     * @param players is the list of player in the game
     * @param colour is the colour to search between the players' colours
     * @return the players whose colour corresponds to the one passed to the method
     */
    public Player getPlayerFromColour(ArrayList<Player> players, Colour colour) {

        for(Player player : players){
            if(player.getColour()==colour){
                return player;
            }
        }
        throw new IllegalStateException("INEXISTING PLAYER WITH SUCH COLOUR ASSOCIATED"+ colour);
    }

    /**
     * @param player is the player whose elimination state has to be checked
     * @return a boolean which says if the player is eliminated
     */
    public boolean isEliminated(Player player){
        return eliminated[player.getColour().ordinal()];
    }

    /**
     * marks the player passed as eliminated by changing the elimination status of his colour
     * @param player is the player to mark as eliminated
     */
    public void setEliminated(Player player){
        eliminated[player.getColour().ordinal()]=true;
        playersLeft--;
    }

    /**
     * removes the player's worker from the board, and calls setEliminated
     * @param player is the player to remove from the game
     */
    public void removeFromGame(Player player){
        int x1,y1,x2,y2;
        x1=player.getWorker(0).getCurrentPosition().getX();
        y1=player.getWorker(0).getCurrentPosition().getY();
        x2=player.getWorker(1).getCurrentPosition().getX();
        y2=player.getWorker(1).getCurrentPosition().getY();
        //removes workers from the towers they were standing on
        gameboard.getTowerCell(x1,y1).getFirstNotPieceLevel().workerMoved();
        gameboard.getTowerCell(x2,y2).getFirstNotPieceLevel().workerMoved();
        //BUT DOESN'T CHANGE THEIR INTERNAL COORDINATE BECAUSE THEY CAN'T MOVE AGAIN

        setEliminated(player);
    }

    public int getPlayersLeft() {
        return playersLeft;
    }

    /**
     * this method is called when only one player is left as a consequence of another player's elimination
     * @return the first not eliminated colour, following the colour order, which is the colour of the last player left
     */
    public Colour getWinnerColour(){

        if(!eliminated[0]) return Colour.RED;
        else if(!eliminated[1]) return Colour.BLUE;
        else return Colour.PURPLE;
    }

    /**
     * given the list of players, it return the winner and notifies the virtualviw with the win message and
     * the enOfGame message
     * @param players is the list of players
     */
    public void declareWinner(ArrayList<Player> players){
        Player winner=getPlayerFromColour(players,getWinnerColour());
        notifyVictory(winner);
        notifyGameEnd(winner);
    }

    /**
     * checks if a player lost, in that case the player is removed from the game, then the elimination message and the
     * updated gamebard are sent to the players through the VirtualView
     * @param player is the player who is trying to move/build
     * @param chosenWorker is the worker number
     * @param phase is move/build, to select the right LoseStrategy for that player
     * @return a boolen which tells if the playerlost
     */
    public boolean performLoseCheck(Player player, int chosenWorker, String phase){

        boolean lost;

        if(phase.equals("move")){
            lost=player.getGodCard().getLoseStrategy().movementLoss(turnInfo, gameboard, player, chosenWorker);
        }else{
            lost=player.getGodCard().getLoseStrategy().buildingLoss(turnInfo, gameboard, player, chosenWorker);
        }

        if(lost){
            notifyLoss(player);
            removeFromGame(player);
            notifyNewBoardState();
        }

        return lost;

    }

    /**
     * checks if the player has won, based on his card's winStrategy. If he does, everyone is notified through the
     * VirtualView
     * @param player is the player's whose win condition must be checked
     * @param chosenWorker is the number of the worker which may have won
     * @return a boolean that tells if the player won or not
     */
    public boolean performWinCheck(Player player, int chosenWorker){

        if(player.getGodCard().getWinStrategy().checkWin(player,chosenWorker)){
            notifyVictory(player);
            notifyGameEnd(player);
            return true;
        }else{
            return false;
        }

    }

    /**
     * checks if the move requested is possible. The player's godCard move strategy check is called
     * @param player is the player who is trying to move
     * @param chosenWorker is the worker to be moved
     * @param movingTo is the cell in which the player wants to move
     * @return a string describing the error or "ok" if the move is allowed
     */
    public String performMoveCheck(Player player, int chosenWorker, int[] movingTo){

        return player.getGodCard().getMoveStrategy().checkMove(turnInfo,gameboard,player,chosenWorker,movingTo);
    }

    /**
     * the worker is moved by calling the player god's move strategy
     * @param player is the player who is trying to move
     * @param chosenWorker is the worker to be moved
     * @param movingTo is the cell in which the player wants to move
     * @return the string containing the next step in the player's turn
     */
    public String performMove(Player player, int chosenWorker, int[] movingTo){

        String result;
        result=player.getGodCard().getMoveStrategy().move(turnInfo,gameboard,player,chosenWorker,movingTo);
        notifyNewBoardState();
        return result;
    }

    /**
     * checks if the build requested is possible. The player's godCard build strategy check is called
     * @param player is the player who is trying to build
     * @param chosenWorker is the worker with which the player wants to build
     * @param buildingInto is the cell in which the player wants to build
     * @param pieceType is "block" or "dome"
     * @return a string describing the error or "ok" if the build is allowed
     */
    public String performBuildCheck(Player player, int chosenWorker, int[] buildingInto, String pieceType){

        return player.getGodCard().getBuildStrategy().checkBuild(turnInfo,gameboard,player,chosenWorker,buildingInto,pieceType);
    }

    /**
     * the worker builds by calling the player god's build strategy
     * @param player is the player who is trying to build
     * @param chosenWorker is the worker with which the player wants to build
     * @param buildingInto is the cell in which the player wants to build
     * @param pieceType is "block" or "dome"
     * @return the string containing the next step in the player's turn
     */
    public String performBuild(Player player, int chosenWorker, int[] buildingInto, String pieceType){

        String result;
        result=player.getGodCard().getBuildStrategy().build(turnInfo,gameboard,player,chosenWorker,buildingInto,pieceType);
        notifyNewBoardState();
        return result;

    }

    /**
     *this methods are the ones who notify the players' virtualviews, which are observing model
     */
    private void notifyGameEnd(Player player){notify(new EndOfGameMessage(player));}
    private void notifyVictory(Player player){
        notify(new WinMessage(player));
    }
    private void notifyNewTurn(Player player){ notify(new NewTurnMessage(player)); }
    private void notifyLoss(Player player){
        notify(new LoseMessage(player));
    }
    public void notifyNewBoardState(){
        notify(new NewBoardStateMessage(gameboard.getBoardState()));
    }

    public void notifyInfoMessage(Player player, String string){notify(new InfoMessageNotification(player,string));}
    public void notifyErrorMessage(Player player, String string){notify(new ErrorMessageNotification(player,string));}
    public void notifyGameStart(ArrayList<Player> players){notify(new GameStartMessage(players));}
    public void notifyPositionRequest(Player player){notify(new PositionRequestNotification(player));}
    public void notifyCardRequest(Player player, PossibleCardsMessage message){notify(new PossibleCardsNotification(player,message));}

    /**
     * this method moves the selected gods from the main deck to the game deck.
     * check on name validity is done client-side
     * @param names is a string vector containing the names of the gods chosen by the player
     */
    public void selectGameCards(String[] names){

        for(String name : names){
            GodCard godCard= completeDeck.findCard(name);
            gameDeck.getDeck().add(godCard);
            completeDeck.getDeck().remove(godCard);
        }
    }

    /**
     * this method moves the removes the god from the main deck and assigns it to the player who requested it
     * check on name validity is done client-side
     * @param name is the god's name
     * @param player is the player who chose that god
     */
    public void chooseOwnCard(Player player, String name){

        GodCard godCard= gameDeck.findCard(name);
        player.setGodCard(godCard);
        gameDeck.getDeck().remove(godCard);
    }

    /**
     * return the index in the list of the player whose colour corresponds to the one passed
     * @param players is the list of players in the game
     * @param colour is the colour to search
     * @return the player's index
     */
    public int getIndexFromColour(ArrayList <Player> players, Colour colour){

        for(int i=0;i<players.size();i++){
            if(players.get(i).getColour()==colour){
                return i;
            }
        }

        throw new IllegalArgumentException("NO PLAYER WITH SUCH COLOUR FOUND TO GIVE INDEX "+colour);
    }

    /**
     * makes sure that the starting position selected is a valid one
     * @param x1 is the x coordinate of the worker 0
     * @param y1 is the y coordinate of the worker 0
     * @param x2 is the x coordinate of the worker 1
     * @param y2 is the y coordinate of the worker 1
     * @return a string containing the error, or "ok" if the positioning is possible
     */
    public String checkStartingPlacement(int x1,int y1,int x2,int y2){

        CheckSupportFunctions support=new CheckSupportFunctions();
        
        if(support.notInGameBoard(x1,y1) || support.notInGameBoard(x2,y2)){
            return GameMessage.startNotInGameboard;
        }
        if(support.notSameCoordinates(x1,y1,x2,y2)){
            return GameMessage.notOnEachOther;
        }
        
        if(support.occupiedTower(gameboard,x1,y1) || support.occupiedTower(gameboard,x2,y2)){
            return GameMessage.notOnOccupiedCell;
        }
        
        return GameMessage.placementOk;
    }

    /**
     * the workers are set on the gameboard. The new board state is then notified
     * @param player is the player who is positioning his workers
     * @param x1 is the x coordinate of the worker 0
     * @param y1 is the y coordinate of the worker 0
     * @param x2 is the x coordinate of the worker 1
     * @param y2 is the y coordinate of the worker 1
     */
    public void placeOnBoard(Player player, int x1, int y1, int x2, int y2){
        
        player.getWorker(0).setStartingPosition(x1,y1);
        player.getWorker(1).setStartingPosition(x2,y2);
        gameboard.getTowerCell(x1,y1).getFirstNotPieceLevel().setWorker(player.getWorker(0));
        gameboard.getTowerCell(x2,y2).getFirstNotPieceLevel().setWorker(player.getWorker(1));
        notifyNewBoardState();
    }





    //TEST METHODS

    /**
     * For testing purpose only
     * @param c turn's colour
     */
    public void setColour(Colour c){
        this.turn = c;
    }

    /**
     *  * For testing purpose only
     * @param i is the number of the player to set as eliminated
     */
    public void setEliminated(int i){eliminated[i]=true;}

}
