package it.polimi.ingsw.model;

import it.polimi.ingsw.model.strategy.buildstrategy.BuildStrategy;
import it.polimi.ingsw.model.strategy.losestrategy.LoseStrategy;
import it.polimi.ingsw.model.strategy.movestrategy.MoveStrategy;
import it.polimi.ingsw.model.strategy.winstrategy.WinStrategy;

public class GodCard{

    //TODO aggiungere le strategy

    private final String name;
    private final String title;
    private final GodClassification classification;
    private final String powerDescription;
    private MoveStrategy moveStrategy;
    private BuildStrategy buildStrategy;
    private WinStrategy winStrategy;
    private LoseStrategy loseStrategy;



    //TODO nel costruttore devono essere impostate le strategy

    public GodCard(String[] godsData){
        name=godsData[0];
        title=godsData[1];
        classification=GodClassification.parseInput(godsData[2]);
        powerDescription=godsData[3];

    }

    public String getGodName() {
        return name;
    }


    public String cardDeclaration(){
        return "GOD: " + name + " (" + title + ")\n" + "POWER: " + powerDescription;
    }


    public MoveStrategy getMoveStrategy() {
        return moveStrategy;
    }
}