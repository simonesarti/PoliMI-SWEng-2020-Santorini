package it.polimi.ingsw;

public class GodCard{

    private final String name;
    private final String title;
    private final GodClassification classification;
    private final Boolean[] numberOfPlayers = new Boolean[2];
    private final String powerDescription;

    public GodCard(String[] godsData){
        name=godsData[0];
        title=godsData[1];
        classification=GodClassification.parseInput(godsData[2]);
        numberOfPlayers[0]=toBoolean(godsData[3]);
        numberOfPlayers[1]=toBoolean(godsData[4]);
        powerDescription=godsData[5];
    }

    public String getGodName() {
        return name;
    }

    private boolean toBoolean(String s){

        if(s.equals("true")){
            return true;
        }else{
            return false;
        }
    }

    public void cardDeclaration(){
        System.out.println(name+" ("+title+")\n"+"POWER: "+powerDescription);
    }


}