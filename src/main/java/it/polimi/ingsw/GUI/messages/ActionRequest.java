package it.polimi.ingsw.GUI.messages;

/**
 * message that contains the type of action that the player chose to perform
 */
public class ActionRequest extends ActionMessage{

    private final String action;

    public ActionRequest(String action) {
        this.action=action;
    }

    public String getAction() {
        return action;
    }
}
