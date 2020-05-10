package it.polimi.ingsw.messages;

/**
 * This class contains messages that will appear on screen
 */
public class GameMessage {

    //game phase
    public static String welcome = "Welcome";
    public static String waitingOtherPlayers = "Waiting other players...";
    public static String win="You Win";
    public static String lose="You Lose";
    public static String quit="You are now going to be disconnected";
    public static String nicknameTaken="This nickname has already been taken";
    public static String abandonedLobby="You have abandoned the lobby";


    //classic messages
    public static String turnCompleted = "You completed your turn, please enter END to confirm that you want to end it\n";
    public static String wrongTurn = "This isn't your turn, please wait\n";
    public static String turnNotEnded = "You haven't completed your turn yet, please complete it\n";
    public static String turnAlreadyEnded = "You have already completed your turn, please confirm that you want to end it\n";


    public static String eliminated = "You've already been eliminated\n";
    public static String notEliminated="You haven't been eliminated yet, you are not allowed to quit\n";

    //move request
    public static String moveRequest = "Make your move (worker number (1/2),x,y)";

    //Generic move error messages
    public static String NotSameWorker = "You have to use the same worker as before\n";
    public static String alreadyMoved = "You have already moved, please go on with your turn\n";
    public static String noMoveToCompleteTower = "You can't move here, this space already contains a complete tower, please choose another position\n";
    public static String noMovedToOccupiedTower = "You can't move here, this space already contains another worker, please choose another position\n";
    public static String noHighJump = "This move isn't allowed, you can't move up more than one level. Choose another position\n";

    //move ok
    public static String moveOK= "ok";

    //build request
    public static String buildRequest = "Choose your build (Piece type (block/dome),x,y)";

    //generic build error messages
    public static String hasNotMoved = "You have not moved yet\n";
    public static String alreadyBuilt = "You have already built\n";
    public static String noBuildToCompleteTower = "You can't build here, this space already contains a complete tower, please choose another position\n";
    public static String noBuildToOccupiedTower = "You can't build here, this space already contains a worker, please choose another position\n";
    public static String noDomesInBlock = "You can't place a dome in a space reserved to a block. Please try something else\n";
    public static String noBlocksInDome = "You can't place a block in a space reserved to a dome. Please try something else\n";
    public static String notSameThatMoved = "You must build with the same worker who moved\n";

    //build ok
    public static String buildOK= "ok";

    //move AND build error messages
    public static String notOwnPosition = "This is your current position, choose another one\n";
    public static String notInSurroundings = "This move is not allowed as it is not to one of the cell surrounding the selected worker\n";
    public static String notInGameBoard = "This position in outside the gameboard, try again \n";
    public static String invalidWorkerNumber= "This worker number is invalid, try again\n";

    //card selection messages
    public static String noSuchCardInSelectionDeck ="There are no card with that name in the selection deck";
    public static String noSuchCardInGameDeck="The card you chose wasn't selected for this match or someone else has already taken it";







    //GOD SPECIFIC MESSAGES

    //APOLLO
    public static String noMovedToOccupiedTowerApollo = "This cell is occupied by your other worker, please choose another position\n";
    //ARTEMIS
    public static String alreadyMovedTwice = "You have already moved twice\n";
    //ATHENA
    public static String athenaNoMoveUp = "This move isn't allowed due to Athena's power\n";
    //MINOTAUR
    public static String noMovedToOccupiedTowerMinotaur = "This cell is occupied by your other worker, please choose another position\n";
    public static String CannotForceWorker = "This worker can not be forced by Minotaur's power, please choose another position\n";
    //ARTEMIS
    public static String ArtemisFirstPosition = "This is your first position, choose another one\n";
    public static String moveAgainOrBuild = "You can now decide to move again or to build\n";
    //DEMETER
    public static String DemeterFirstBuild = "This is your first building position, choose another one\n";
    public static String buildAgainOrEnd = "You can now decide to build again or choose END to end your turn\n";
    public static String alreadyBuiltTwice = "You have already built twice, end your turn\n";
    //PROMETHEUS
    public static String prometheusNoMoveUp ="this move isn't allowed due to your decision to build before moving\n";
    public static String noBuildMoreThanTwice ="you already built twice, your turn should have already ended, ERROR\n";
    //HEPHAESTUS
    public static String HephaestusWrongBuild = "You must build on top of your first block";
    public static String mustBeBlock = "Your second piece must be a Block";
}
