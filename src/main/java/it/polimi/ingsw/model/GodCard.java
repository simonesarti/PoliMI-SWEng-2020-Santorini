package it.polimi.ingsw.model;

import it.polimi.ingsw.model.strategy.buildstrategy.*;
import it.polimi.ingsw.model.strategy.losestrategy.*;
import it.polimi.ingsw.model.strategy.movestrategy.*;
import it.polimi.ingsw.model.strategy.winstrategy.*;

public class GodCard{

    private final String name;
    private final String title;
    private final boolean usableFor3Players;
    private final GodClassification classification;
    private final String powerDescription;
    private MoveStrategy moveStrategy;
    private BuildStrategy buildStrategy;
    private WinStrategy winStrategy;
    private LoseStrategy loseStrategy;


    /**
     * The constructor associate every GodCard to the right strategies
     * @param godsData
     */
    public GodCard(String[] godsData){
        name=godsData[0];
        title=godsData[1];
        classification=GodClassification.parseInput(godsData[2]);
        usableFor3Players=toBoolean(godsData[3]);
        powerDescription=godsData[4];

        switch(name) {

            case "Apollo" :
                moveStrategy = new ApolloMove();
                buildStrategy = new BasicBuild();
                winStrategy = new BasicWin();
                loseStrategy= new ApolloLose();
                break;

            case "Artemis" :
                moveStrategy = new ArtemisMove();
                buildStrategy = new BasicBuild();
                winStrategy = new BasicWin();
                loseStrategy = new BasicLose();
                break;

            case "Athena" :
                moveStrategy = new AthenaMove();
                buildStrategy = new BasicBuild();
                winStrategy = new BasicWin();
                loseStrategy = new AthenaLose();
                break;

            case "Atlas" :
                moveStrategy = new BasicMove();
                buildStrategy = new AtlasBuild();
                winStrategy = new BasicWin();
                loseStrategy = new BasicLose();
                break;

            case "Demeter" :
                moveStrategy = new BasicMove();
                buildStrategy = new DemeterBuild();
                winStrategy = new BasicWin();
                loseStrategy = new BasicLose();
                break;

            case "Hephaestus" :
                moveStrategy = new BasicMove();
                //buildStrategy = new HephaestusBuild();
                winStrategy = new BasicWin();
                loseStrategy = new BasicLose();
                break;

            case "Minotaur" :
                moveStrategy = new MinotaurMove();
                buildStrategy = new BasicBuild();
                winStrategy = new BasicWin();
                loseStrategy = new MinotaurLose();
                break;

            case "Pan" :
                moveStrategy = new BasicMove();
                buildStrategy = new BasicBuild();
                winStrategy = new PanWin();
                loseStrategy = new BasicLose();
                break;

            case "Prometheus" :
                //moveStrategy = new PrometheusMove();
                //buildStrategy = new PrometheusBuild();
                winStrategy = new BasicWin();
                loseStrategy = new PrometheusLose();
                break;

        }
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

}