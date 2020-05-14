package it.polimi.ingsw.view.cli;




public class Cell {

    private String[] stripes = new String[4];

    public Cell(){
        for (int i=0; i<5; i++){
            stripes[i] = new String();
        }

    }



    public String uniformStripe(String BackgroundAnsiColour){
        String stripe= BackgroundAnsiColour+"            "+AnsiCode.RESET;
        return stripe;
    }

    private String domeStripe(String BackgroundAnsiColour1, String BackgroundAnsiColour2 ){
        String stripe= BackgroundAnsiColour1+"  "+BackgroundAnsiColour2+"        "+BackgroundAnsiColour1+"  "+AnsiCode.RESET;
        return stripe;
    }


    private String coordinateStripe(int value){
        String stripe = "";
        return stripe;
    }

    private String workerStripe(String BackgroundAnsiColour,int workerNumber){
        String num = String.valueOf(workerNumber);
        String stripe = BackgroundAnsiColour+"     "+AnsiCode.TEXT_BLACK+"W"+num+"     "+AnsiCode.RESET;
        return stripe;
    }

    private String levelStripe(int level){
        String num = String.valueOf(level);
        String stripe=AnsiCode.BACKGROUND_WHITE+"    "+AnsiCode.TEXT_BLACK+"LV "+num+"    "+AnsiCode.RESET;
        return stripe;
    }

    public String getStripe(int n){
        return stripes[n];
    }




}
