package it.polimi.ingsw.cli;


import it.polimi.ingsw.model.Colour;

public class Cell {

    private String[] stripes = new String[5];

    /**
     * every cell contains 5 stripe
     */
    public Cell() {
        for (int i = 0; i < 5; i++) {
            stripes[i] = new String();
        }

    }

    /**
     * this functions create a uniformly coloured stripes
     * @param BackgroundAnsiColour is the colour of the stripe
     * @return the string which represents the coloured stripe
     */
    public String uniformStripe(String BackgroundAnsiColour) {
        String stripe = BackgroundAnsiColour + "            " + AnsiCode.RESET;
        return stripe;
    }

    /**
     * this functions create a coloured stripes which contains a section of a dome
     * @param BackgroundAnsiColour is the colour around the dome
     * @return the string which represents the coloured stripe
     */
    public String domeStripe(String BackgroundAnsiColour) {
        String stripe = BackgroundAnsiColour + "  " + AnsiCode.BACKGROUND_BLUE + "        " + BackgroundAnsiColour + "  " + AnsiCode.RESET;
        return stripe;
    }

    /**
     * this functions create a void stripes
     * @return the string which represents the stripe
     */
    public String emptyStripe(){
       String stripe = "            ";
       return stripe;
    }


    /**
     * this functions create a stripes which contains the coordinate and its value
     * @return the string which represents the stripe
     */
    public String coordinateStripe(String coordinate, int value) {
        String num = String.valueOf(value);
        String stripe = "     "+AnsiCode.TEXT_BLACK + coordinate + num+ "     "+AnsiCode.RESET;
        return stripe;
    }

    /**
     * this functions create a coloured stripes which contains a worker certain colour worker and its number
     * @param BackgroundAnsiColour is the colour around the worker
     * @return the string which represents the coloured stripe
     */
    public String workerStripe(String BackgroundAnsiColour, int workerNumber, Colour workercolour) {
        String num = String.valueOf(workerNumber);
        String stripe="";
        if (workercolour.equals(Colour.BLUE)){
             stripe = BackgroundAnsiColour + "     " + AnsiCode.TEXT_BLUE + "W" + num + "     " + AnsiCode.RESET;
        }
        else if (workercolour.equals(Colour.RED)){
             stripe = BackgroundAnsiColour + "     " + AnsiCode.TEXT_RED + "W" + num + "     " + AnsiCode.RESET;
        }
        else if (workercolour.equals(Colour.PURPLE)){
             stripe = BackgroundAnsiColour + "     " + AnsiCode.TEXT_PURPLE + "W" + num + "     " + AnsiCode.RESET;
        }

        return stripe;
    }

    /**
     * this functions create a coloured stripes which represents a level
     * @return the string which represents the coloured stripe
     */
    public String levelStripe(int level) {
        String num = String.valueOf(level);
        String stripe = AnsiCode.BACKGROUND_WHITE + "    " + AnsiCode.TEXT_BLACK + "LV " + num + "    " + AnsiCode.RESET;
        return stripe;
    }

    public String getStripe(int n) {
        return stripes[n];
    }

    /**
     * Creates a block representing a dome
     */
    public void assignDome(String BackgroundAnsiColour){
        stripes[0]=uniformStripe(BackgroundAnsiColour);
        stripes[1]=domeStripe(BackgroundAnsiColour);
        stripes[2]=domeStripe(BackgroundAnsiColour);
        stripes[3]=domeStripe(BackgroundAnsiColour);
        stripes[4]=uniformStripe(BackgroundAnsiColour);
    }
    /**
     * Creates a block with x or y coordinates
     */
    public void assignCoordinate(String coordinate,int value){
        stripes[0]=emptyStripe();
        stripes[1]=emptyStripe();
        stripes[2]=coordinateStripe(coordinate,value);
        stripes[3]=emptyStripe();
        stripes[4]=emptyStripe();
    }
    /**
     * Creates a level 0 block
     */
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
    /**
     * Creates a block with a worker
     */
    public void assignOnlyWorker(int workerNumber, Colour workercolour){

        stripes[0]=uniformStripe(AnsiCode.BACKGROUND_GREEN);
        stripes[1]=uniformStripe(AnsiCode.BACKGROUND_GREEN);
        stripes[2]=workerStripe(AnsiCode.BACKGROUND_GREEN,workerNumber, workercolour);
        stripes[3]=uniformStripe(AnsiCode.BACKGROUND_GREEN);
        stripes[4]=uniformStripe(AnsiCode.BACKGROUND_GREEN);

    }
    /**
     * Creates a level 1/2/3 block
     */
    public void assignOnlyLevel(int level){

        stripes[0]=uniformStripe(AnsiCode.BACKGROUND_WHITE);
        stripes[1]=uniformStripe(AnsiCode.BACKGROUND_WHITE);
        stripes[2]=levelStripe(level);
        stripes[3]=uniformStripe(AnsiCode.BACKGROUND_WHITE);
        stripes[4]=uniformStripe(AnsiCode.BACKGROUND_WHITE);

    }
    /**
     * Creates a level 1/2/3 block with a worker
     */
    public void assignLevelAndWorker(int level, int workerNumber, Colour workercolour){

        stripes[0]=uniformStripe(AnsiCode.BACKGROUND_WHITE);
        stripes[1]=levelStripe(level);
        stripes[2]=uniformStripe(AnsiCode.BACKGROUND_WHITE);
        stripes[3]=workerStripe(AnsiCode.BACKGROUND_WHITE,workerNumber,workercolour);
        stripes[4]=uniformStripe(AnsiCode.BACKGROUND_WHITE);

    }




}
