package it.polimi.ingsw.model;

public class TurnInfo {

    //da usare per verificare che il non possano essere fatte altre mosse nel turno
    private boolean turnHasEnded;

    //da usare per verificare che il turno può finire se viene mandato un messaggio di fine turno
    private boolean turnCanEnd;

    //da usare per check attiazione potere di Atena
    private boolean athenaPowerActive;

    //da usare con move base
    private boolean hasAlreadyMoved;

    //da usare con move multiple
    private int numberOfMoves;

    //da usare per verificare che move e build siano fatte con lo stesso worker
    private int chosenWorker;

    //da usare con Demetra, check seconda costruzione
    private int[] lastBuildCoordinates;

    public TurnInfo(){

        turnHasEnded=false;
        turnCanEnd=false;
        athenaPowerActive=false;
        hasAlreadyMoved=false;
        numberOfMoves=0;

        chosenWorker=-1;
        lastBuildCoordinates = new int[]{-1, -1};
    }

    //resetta tutti i dati sul turno tranne il valore di Atena
    public void turnInfoReset(){

        turnHasEnded=false;
        turnCanEnd=false;
        hasAlreadyMoved=false;
        numberOfMoves=0;

        chosenWorker=-1;
        lastBuildCoordinates[0]=-1;
        lastBuildCoordinates[1]=-1;
    }

    public boolean getTurnHasEnded(){return turnHasEnded;}
    public void setTurnHasEnded(){turnHasEnded=true;}

    public boolean getTurnCanEnd(){return turnCanEnd;}
    public void setTurnCanEnd(){turnCanEnd=true;}

    public boolean getAthenaPowerActive(){return athenaPowerActive;}
    public void setAthenaPowerActive(){athenaPowerActive=true;}

    public boolean getHasAlreadyMoved(){return hasAlreadyMoved;}
    public void setHasAlreadyMoved(){hasAlreadyMoved=true;}

    public int getNumberOfMoves(){return numberOfMoves;}
    public void addMove(){numberOfMoves++;}

    public int getChosenWorker(){return chosenWorker;}
    public void setChosenWorker(int n){chosenWorker=n;}

    public int[] getLastBuildCoordinates(){return lastBuildCoordinates;}
    public void setLastBuildCoordinates(int x, int y){
        lastBuildCoordinates[0]=x;
        lastBuildCoordinates[1]=y;
    }





}
