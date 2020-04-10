package it.polimi.ingsw.messages;

/**
 * This class contains messages that will appear on screen
 */
public class GameMessage {

    //classic messages
    public static String newTurn = "The previous turn has been completed\n";
    public static String wrongTurn = "This isn't your turn, please wait\n";
    public static String eliminated = "You've already been eliminated\n";
    public static String turnNotEnded = "You haven't completed your turn yet, please proceed to complete it with a move/build\n";

    //move request
    public static String moveRequest = "Make your move (worker number (1/2),x,y)";

    //Generic move error messages

    public static String alreadyMoved = "You have already moved, please build\n";
    public static String noMoveToCompleteTower = "You can't move here, this space already contains a complete tower, please choose another position\n";
    public static String noMovedToOccupiedTower = "You can't move here, this space already contains another worker, please choose another position\n";
    public static String noHighJump = "This move isn't allowed, you can't move up more than one level. Choose another position\n";

    //move ok
    public static String moveOK= "ok";

    //build request
    public static String buildRequest = "Choose your build (Piece type (block/dome), worker number (1/2),x,y)";

    //generic build error messages
    public static String hasNotMoved = "You have not moved yet\n";
    public static String alreadyBuilt = "You have already built\n";
    public static String noBuildToCompleteTower = "You can't build here, this space already contains a complete tower, please choose another position\n";
    public static String noBuildToOccupiedTower = "You can't build here, this space already contains a worker, please choose another position\n";
    public static String noLevel1Left = "No more first level blocks left. Please try something else\n";
    public static String noLevel2Left = "No more second level blocks left. Please try something else\n";
    public static String noLevel3Left = "No more third level blocks left. Please try something else\n";
    public static String noDomesLeft = "No more domes left. Please try something else\n";
    public static String noDomesInBlock = "You can't place a dome in a space reserved to a block. Please try something else\n";
    public static String noBlocksInDome = "You can't place a block in a space reserved to a dome. Please try something else\n";

    //build ok
    public static String buildOK= "ok";

    //move AND build error messages
    public static String notTheSame = "This is your current position, choose another one\n";
    public static String notInSurroundings = "This move is not allowed as it is not to one of the cell surrounding the selected worker\n";
    public static String notInGameboard = "This position in outside the gameboard, try again \n";

    //GOD SPECIFIC MESSAGES

    //ATHENA
    public static String athenaNoMoveUp = "This move isn't allowed due to Athena's power";


}
