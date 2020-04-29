package it.polimi.ingsw.model;

import it.polimi.ingsw.messages.GameToPlayerMessages.*;
import it.polimi.ingsw.observe.Observable;

/**
 * This class contains an instance of Gameboard and schedules players' turns
 */
public class Model extends Observable<NotifyMessages> {

    private final GameBoard gameboard;
    private final TurnInfo turnInfo;
    private Colour turn;
    private int playersLeft;
    private boolean[] eliminated=new boolean[3];

//----------------------------------------------------------------------------------------------------------

    public Model(int numberOfPlayers){
        gameboard = new GameBoard();
        turnInfo=new TurnInfo();
        turn=Colour.WHITE;

        playersLeft=numberOfPlayers;

        if(numberOfPlayers==2){
            eliminated[2]=true;
        }

    }

    public GameBoard getGameBoard(){return gameboard;}

    public TurnInfo getTurnInfo(){return turnInfo;}

    public void assignColour2(Player player1, Player player2){

        if(player1.getBirthday().compareTo(player2.getBirthday())<=0){
            player1.setColour(Colour.WHITE);
            player2.setColour(Colour.BLUE);
        }else{
            player2.setColour(Colour.WHITE);
            player1.setColour(Colour.BLUE);
        }
    }

    public void assignColour3(Player player1, Player player2,Player player3){

        if(player1.getBirthday().compareTo(player2.getBirthday()) <=0 &&
           player1.getBirthday().compareTo(player3.getBirthday()) <=0){

            player1.setColour(Colour.WHITE);

            if(player2.getBirthday().compareTo(player3.getBirthday())<=0) {
                player2.setColour(Colour.BLUE);
                player3.setColour(Colour.GREY);
            }else{
                player3.setColour(Colour.BLUE);
                player2.setColour(Colour.GREY);
            }

        }else if(player2.getBirthday().compareTo(player1.getBirthday())<=0 &&
                 player2.getBirthday().compareTo(player3.getBirthday())<=0){

            player2.setColour(Colour.WHITE);

            if(player1.getBirthday().compareTo(player3.getBirthday())<=0){
                player1.setColour(Colour.BLUE);
                player3.setColour(Colour.GREY);
            }else{
                player3.setColour(Colour.BLUE);
                player1.setColour(Colour.GREY);
            }
        }else{

            player3.setColour(Colour.WHITE);

            if(player1.getBirthday().compareTo(player2.getBirthday())<=0){
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
    public boolean isPlayerTurn(Player player){ return player.getColour()== turn;}

    public Colour getTurn(){
        return turn;
    }

    /**
     * updates the turn based on the order of the player, and resets turnInfo
    */
    public void updateTurn() {

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

        turnInfo.turnInfoReset();
    }

    public boolean isEliminated(Player player){
        return eliminated[player.getColour().ordinal()];
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

        eliminated[player.getColour().ordinal()]=true;
        playersLeft--;
    }

    public int getPlayersLeft() {
        return playersLeft;
    }

    //TODO finire notify (ad esempio la win può termiare la partita/aggiungere eliminazioni e così via
    //TODO lose deve rimuovere le pedine dal gioco dell'eliminato
    public void notifyLoss(Player player){
        notify(new LoseMessage(player));
    }
    public void notifyVictory(Player player){
        notify(new WinMessage(player));
    }
    public void notifyQuit(Player player){
        notify(new QuitMessage((player)));
    }
    public void notifyNewBoardState(){
        notify(new NewBoardStateMessage(gameboard.getBoardState()));
    }


    /**
     * For testing purpose only
     * @param c turn's colour
     */
    public void setColour(Colour c){
        this.turn = c;
    }

}
