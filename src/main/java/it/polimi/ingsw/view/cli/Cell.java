package it.polimi.ingsw.view.cli;




public class Cell {

    private String[] stripes = new String[5];

    public Cell() {
        for (int i = 0; i < 5; i++) {
            stripes[i] = new String();
        }

    }



    public String uniformStripe(String BackgroundAnsiColour) {
        String stripe = BackgroundAnsiColour + "            " + AnsiCode.RESET;
        return stripe;
    }

    public String domeStripe(String BackgroundAnsiColour) {
        String stripe = BackgroundAnsiColour + "  " + AnsiCode.BACKGROUND_BLUE + "        " + BackgroundAnsiColour + "  " + AnsiCode.RESET;
        return stripe;
    }

    public String emptyStripe(){
       String stripe = "            ";
       return stripe;
    }


    public String coordinateStripe(String coordinate, int value) {
        String num = String.valueOf(value);
        String stripe = "     "+AnsiCode.TEXT_BLACK + coordinate + num+ "     "+AnsiCode.RESET;
        return stripe;
    }

    public String workerStripe(String BackgroundAnsiColour, int workerNumber) {
        String num = String.valueOf(workerNumber);
        String stripe = BackgroundAnsiColour + "     " + AnsiCode.TEXT_BLACK + "W" + num + "     " + AnsiCode.RESET;
        return stripe;
    }

    public String levelStripe(int level) {
        String num = String.valueOf(level);
        String stripe = AnsiCode.BACKGROUND_WHITE + "    " + AnsiCode.TEXT_BLACK + "LV " + num + "    " + AnsiCode.RESET;
        return stripe;
    }

    public String getStripe(int n) {
        return stripes[n];
    }


    public void assignDome(String BackgroundAnsiColour){
        stripes[0]=uniformStripe(BackgroundAnsiColour);
        stripes[1]=domeStripe(BackgroundAnsiColour);
        stripes[2]=domeStripe(BackgroundAnsiColour);
        stripes[3]=domeStripe(BackgroundAnsiColour);
        stripes[4]=uniformStripe(BackgroundAnsiColour);
    }

    public void assignCoordinate(String coordinate,int value){
        stripes[0]=emptyStripe();
        stripes[1]=emptyStripe();
        stripes[2]=coordinateStripe(coordinate,value);
        stripes[3]=emptyStripe();
        stripes[4]=emptyStripe();
    }

    public void assignGreen(){

        for(int i=0;i<5;i++){
           stripes[i]=uniformStripe(AnsiCode.BACKGROUND_GREEN);
        }
    }

    public void assignVoid(){
        for(int i=0;i<5;i++){
            stripes[i]=emptyStripe();
        }
    }

    public void assignOnlyWorker(int workerNumber){

        stripes[0]=uniformStripe(AnsiCode.BACKGROUND_GREEN);
        stripes[1]=uniformStripe(AnsiCode.BACKGROUND_GREEN);
        stripes[2]=workerStripe(AnsiCode.BACKGROUND_GREEN,workerNumber);
        stripes[3]=uniformStripe(AnsiCode.BACKGROUND_GREEN);
        stripes[4]=uniformStripe(AnsiCode.BACKGROUND_GREEN);

    }

    public void assignOnlyLevel(int level){

        stripes[0]=uniformStripe(AnsiCode.BACKGROUND_WHITE);
        stripes[1]=uniformStripe(AnsiCode.BACKGROUND_WHITE);
        stripes[2]=levelStripe(level);
        stripes[3]=uniformStripe(AnsiCode.BACKGROUND_WHITE);
        stripes[4]=uniformStripe(AnsiCode.BACKGROUND_WHITE);

    }

    public void assignLevelAndWorker(int level, int workerNumber){

        stripes[0]=uniformStripe(AnsiCode.BACKGROUND_WHITE);
        stripes[1]=levelStripe(level);
        stripes[2]=uniformStripe(AnsiCode.BACKGROUND_WHITE);
        stripes[3]=workerStripe(AnsiCode.BACKGROUND_WHITE,workerNumber);
        stripes[4]=uniformStripe(AnsiCode.BACKGROUND_WHITE);

    }




}
/*
        stripes[0]=
        stripes[1]=
        stripes[2]=
        stripes[3]=
        stripes[4]=

 */