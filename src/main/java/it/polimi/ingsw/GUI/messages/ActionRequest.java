package it.polimi.ingsw.GUI.messages;

public class ActionRequest extends ActionMessage{

    private final String action;

    public ActionRequest(String action) {
        this.action=action;
    }

    public String getAction() {
        return action;
    }
}
