package it.polimi.ingsw.model;

public class Match {
    //TODO bisogna stare attenti alla gestione dei turni (i turni sono di tipo Colour). Come partire? forse mettere nel costruttore turn = coloreChepartePerPrimo?
    private Colour turn;
    private GameBoard gameboard;
    private Player bluePlayer;
    private Player greyPlayer;
    private Player whitePlayer;

    public Match(){




    }

    public boolean isPlayerTurn(Player player){ return player.getColour()== turn;}

    public Colour getTurn(){ return turn;}

    public boolean isFeasibleBasicMove(int[] movingTo, Worker ChosenWorker){

        if (movingTo[0] < 0 || movingTo[0] > 4 || movingTo[1] < 0 || movingTo[1] > 4) {
            return false;
        }

        //towercell must be empty
        else if(gameboard.getTowerCell(movingTo[0],movingTo[1]).getTowerLevel().isOccupied()) return false;
        //towercell height must be <= (worker height +1)
        else if(gameboard.getTowerCell(movingTo[0],movingTo[1]).getTowerHeight() > (ChosenWorker.getCurrentPosition().getZ() +1)) return false;
        else return true;
    }

    public void updateTurn(){
        if(turn == Colour.BLUE){
            turn = Colour.GREY;
        }
        else if(turn == Colour.GREY){
            turn = Colour.WHITE;
        }
        else if(turn == Colour.WHITE){
            turn = Colour.BLUE;
        }
        else{
            //ERRORE, NON DOVREBBE ARRIVARE MAI QUI
        }

        return;
    }

    public Player getCurrentPlayer() {

        if (getTurn() == Colour.BLUE) {
            return bluePlayer;
        } else if (getTurn() == Colour.GREY) {
            return greyPlayer;
        } else return whitePlayer;
    }


}
