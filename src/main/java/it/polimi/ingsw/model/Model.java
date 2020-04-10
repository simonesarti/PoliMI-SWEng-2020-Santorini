package it.polimi.ingsw.model;

/**
 * This class contains an instance of Gameboard and schedules players' turns
 */
public class Model {

    private GameBoard gameboard;
    private int numberOfPlayers;

    private Colour turn;
    private boolean turnCanEnd;
    private Colour eliminated;



//----------------------------------------------------------------------------------------------------------

    public Model(int numberOfPlayers){
        gameboard = new GameBoard();
        turn=Colour.WHITE;
        turnCanEnd=false;

        this.numberOfPlayers=numberOfPlayers;
        if(this.numberOfPlayers==2){
            eliminated=Colour.GREY;
        }


    }

    public GameBoard getGameBoard(){return gameboard;}

    /**
     * Checks that current player's colour is equals to current turn's colour
     * @param player
     * @return boolean
     */
    public boolean isPlayerTurn(Player player){ return player.getColour()== turn;}

    /**
     * updates the turn based on the order of the player
    */
    public void updateTurn() {

        switch (eliminated){
            case GREY:
                if(turn==Colour.WHITE){
                    turn=Colour.BLUE;
                }else{
                    turn=Colour.WHITE;
                }
                break;
            case WHITE:
                if(turn==Colour.BLUE){
                    turn=Colour.GREY;
                }else{
                    turn=Colour.BLUE;
                }
                break;
            case BLUE:
                if(turn==Colour.WHITE){
                    turn=Colour.GREY;
                }else{
                    turn=Colour.WHITE;
                }
                break;
            default:
                if(turn==Colour.WHITE){
                    turn=Colour.BLUE;
                }else if(turn==Colour.BLUE){
                    turn=Colour.GREY;
                }else {
                    turn = Colour.WHITE;
                }
                break;
        }

        turnCanEnd=false;
    }

    public boolean getTurnCanEnd(){ return this.turnCanEnd; }

    public void setTurnCanEnd() {
        this.turnCanEnd = true;
    }

    public boolean isEliminated(Colour colour){
        return this.eliminated==colour;
    }


}
