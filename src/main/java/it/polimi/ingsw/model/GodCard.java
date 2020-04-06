package it.polimi.ingsw.model;

import it.polimi.ingsw.model.strategy.buildstrategy.BuildStrategy;
import it.polimi.ingsw.model.strategy.losestrategy.LoseStrategy;
import it.polimi.ingsw.model.strategy.movestrategy.MoveStrategy;
import it.polimi.ingsw.model.strategy.winstrategy.WinStrategy;

public class GodCard{

    //TODO aggiungere le strategy

    private final String name;
    private final String title;
    private final boolean usableFor3Players;
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
        usableFor3Players=toBoolean(godsData[3]);
        powerDescription=godsData[4];
    }

    public String getGodName() {
        return name;
    }

    public boolean canBeUsedIn3(){ return usableFor3Players;}

    public String cardDeclaration(){
        return "GOD: " + name + " (" + title + ")\n" + "POWER: " + powerDescription;
    }

    private boolean toBoolean(String valid){
        return (valid.toLowerCase()).equals("true");
    }

    public MoveStrategy getMoveStrategy() {
        return moveStrategy;
    }

    public BuildStrategy getBuildStrategy() {
        return buildStrategy;
    }

    public WinStrategy getWinStrategy(){
        return winStrategy;
    }

    public LoseStrategy getLoseStrategy() {
        return loseStrategy;
    }

    //TODO non così: nel costruttore asseganmento con movestartegy=new AthenaMoveStrategy(Messaggio), eliminala
 /*   public void setBuildStrategy(BuildStrategy buildStrategy) {
        this.buildStrategy = buildStrategy;
    }*/
}