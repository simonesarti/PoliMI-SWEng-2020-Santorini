package it.polimi.ingsw.model;

import it.polimi.ingsw.messages.GameToPlayerMessages.Notify.*;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.GameMessage;
import it.polimi.ingsw.model.strategy.CheckSupportFunctions;
import it.polimi.ingsw.observe.Observable;

import java.util.ArrayList;

/**
 * This class contains an instance of Gameboard and schedules players' turns
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

    public Model(int numberOfPlayers){
        gameboard = new GameBoard();
        turnInfo=new TurnInfo();
        turn=Colour.WHITE;

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

    public void assignColour(ArrayList<Player> players){

        if(players.size()==2){
            assignColour2(players.get(0),players.get(1));
        }else{
            assignColour3(players.get(0),players.get(1),players.get(2));
        }
    }
    
    private void assignColour2(Player player1, Player player2){

        if(player1.getBirthday().compareTo(player2.getBirthday())>=0){
            player1.setColour(Colour.WHITE);
            player2.setColour(Colour.BLUE);
        }else{
            player2.setColour(Colour.WHITE);
            player1.setColour(Colour.BLUE);
        }
    }

    private void assignColour3(Player player1, Player player2,Player player3){

        if(player1.getBirthday().compareTo(player2.getBirthday()) >=0 &&
           player1.getBirthday().compareTo(player3.getBirthday()) >=0){

            player1.setColour(Colour.WHITE);

            if(player2.getBirthday().compareTo(player3.getBirthday())>=0) {
                player2.setColour(Colour.BLUE);
                player3.setColour(Colour.GREY);
            }else{
                player3.setColour(Colour.BLUE);
                player2.setColour(Colour.GREY);
            }

        }else if(player2.getBirthday().compareTo(player1.getBirthday())>=0 &&
                 player2.getBirthday().compareTo(player3.getBirthday())>=0){

            player2.setColour(Colour.WHITE);

            if(player1.getBirthday().compareTo(player3.getBirthday())>=0){
                player1.setColour(Colour.BLUE);
                player3.setColour(Colour.GREY);
            }else{
                player3.setColour(Colour.BLUE);
                player1.setColour(Colour.GREY);
            }
        }else{

            player3.setColour(Colour.WHITE);

            if(player1.getBirthday().compareTo(player2.getBirthday())>=0){
                player1.setColour(Colour.BLUE);
                player2.setColour((Colour.GREY));
            }else{
                player2.setColour(Colour.BLUE);
                player1.setColour(Colour.GREY);
            }
        }

    }

    /**
     * Checks that current player's colour is equals to current turn's colour
     * @param player
     * @return boolean
     */
    public boolean isNotPlayerTurn(Player player){ return player.getColour() != turn;}

    public Colour getTurn(){
        return turn;
    }

    /**
     * updates the turn based on the order of the player, and resets turnInfo
    */
    public void updateTurn(ArrayList<Player> players) {

        updateTurnColour();

        turnInfo.turnInfoReset();

        notifyNewTurn(getPlayerFromColour(players,turn));
    }

    private void updateTurnColour(){

        switch(turn){

            case WHITE:
                if(!eliminated[1]) turn=Colour.BLUE;
                else if (!eliminated[2]) turn=Colour.GREY;
                else turn=Colour.WHITE;
                break;


            case BLUE:
                if(!eliminated[2]) turn=Colour.GREY;
                else if(!eliminated[0]) turn=Colour.WHITE;
                else turn=Colour.BLUE;
                break;

            case GREY:
                if(!eliminated[0]) turn=Colour.WHITE;
                else if(!eliminated[1]) turn=Colour.BLUE;
                else turn=Colour.GREY;
                break;

            default:
                break;
        }
    }

    public Player getPlayerFromColour(ArrayList<Player> players, Colour colour) {

        for(Player player : players){
            if(player.getColour()==colour){
                return player;
            }
        }
        throw new IllegalStateException("INEXISTING PLAYER WITH SUCH COLOUR ASSOCIATED"+ colour);
    }

    public boolean isEliminated(Player player){
        return eliminated[player.getColour().ordinal()];
    }

    public void setEliminated(Player player){
        eliminated[player.getColour().ordinal()]=true;
    }

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
        playersLeft--;
    }

    public int getPlayersLeft() {
        return playersLeft;
    }

    public Colour getWinnerColour(){

        if(!eliminated[0]) return Colour.WHITE;
        else if(!eliminated[1]) return Colour.BLUE;
        else return Colour.GREY;
    }

    public void declareWinner(ArrayList<Player> players){
        Player winner=getPlayerFromColour(players,getWinnerColour());
        notifyVictory(winner);
        notifyGameEnd(winner);
    }

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

    public boolean performWinCheck(Player player, int chosenWorker){

        if(player.getGodCard().getWinStrategy().checkWin(player,chosenWorker)){
            notifyVictory(player);
            notifyGameEnd(player);
            return true;
        }else{
            return false;
        }

    }

    public String performMoveCheck(Player player, int chosenWorker, int[] movingTo){

        return player.getGodCard().getMoveStrategy().checkMove(turnInfo,gameboard,player,chosenWorker,movingTo);
    }

    public String performMove(Player player, int chosenWorker, int[] movingTo){

        String result;
        result=player.getGodCard().getMoveStrategy().move(turnInfo,gameboard,player,chosenWorker,movingTo);
        notifyNewBoardState();
        return result;
    }

    public String performBuildCheck(Player player, int chosenWorker, int[] buildingInto, String pieceType){

        return player.getGodCard().getBuildStrategy().checkBuild(turnInfo,gameboard,player,chosenWorker,buildingInto,pieceType);
    }

    public String performBuild(Player player, int chosenWorker, int[] buildingInto, String pieceType){

        String result;
        result=player.getGodCard().getBuildStrategy().build(turnInfo,gameboard,player,chosenWorker,buildingInto,pieceType);
        notifyNewBoardState();
        return result;

    }

    private void notifyGameEnd(Player player){notify(new EndOfGameMessage(player));}
    private void notifyVictory(Player player){
        notify(new WinMessage(player));
    }
    private void notifyNewTurn(Player player){ notify(new NewTurnMessage(player)); }
    private void notifyLoss(Player player){
        notify(new LoseMessage(player));
    }
    private void notifyNewBoardState(){
        notify(new NewBoardStateMessage(gameboard.getBoardState()));
    }


    //check on name validity is done client-side
    public void selectGameCards(String[] names){

        for(String name : names){
            GodCard godCard= completeDeck.findCard(name);
            gameDeck.getDeck().add(godCard);
            completeDeck.getDeck().remove(godCard);
        }
    }

    //check on name validity is done client-side
    public void chooseOwnCard(Player player, String name){

        GodCard godCard= gameDeck.findCard(name);
        player.setGodCard(godCard);
        gameDeck.getDeck().remove(godCard);
    }

    public int getIndexFromColour(ArrayList <Player> players, Colour colour){

        for(int i=0;i<players.size();i++){
            if(players.get(i).getColour()==colour){
                return i;
            }
        }

        throw new IllegalArgumentException("NO PLAYER WITH SUCH COLOUR FOUND TO GIVE INDEX "+colour);
    }

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
    
    public void placeOnBoard(Player player, int x1, int y1, int x2, int y2){
        
        player.getWorker(0).setStartingPosition(x1,y1);
        player.getWorker(1).setStartingPosition(x2,y2);
        gameboard.getTowerCell(x1,y1).getFirstNotPieceLevel().setWorker(player.getWorker(0));
        gameboard.getTowerCell(x2,y2).getFirstNotPieceLevel().setWorker(player.getWorker(1));
        notifyNewBoardState();
    }













    /**
     * For testing purpose only
     * @param c turn's colour
     */
    public void setColour(Colour c){
        this.turn = c;
    }
    public void setEliminated(int i){eliminated[i]=true;}

}
