package it.polimi.ingsw.model;

import it.polimi.ingsw.messages.PlayerInfo;

import java.util.Calendar;

public class Player{

    private final String nickname;
    private final Calendar birthday;
    private Worker[] workers = new Worker[2];
    private Colour colour;
    private GodCard godCard;

    public Player(PlayerInfo playerInfo){
        this.nickname=playerInfo.getPlayerNickname();
        this.birthday=playerInfo.getBirthday();
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public void setGodCard(GodCard godCard) {
        this.godCard = godCard;
    }

    public void setWorkers(){
        workers[0]=new Worker(this.colour);
        workers[1]=new Worker(this.colour);
    }

    public String getNickname() {
        return nickname;
    }

    public Calendar getBirthday() {
        return birthday;
    }

}
