package it.polimi.ingsw.view.cli;

public class Stripe {

    private String stripe = new String();

    //costruttore
    public Stripe(int x){

    }
    //nel caso di colore uniforme
    public Stripe(String ansicolor){
        System.out.println(ansicolor + "            " + AnsiCode.RESET);
        System.out.println(ansicolor + "            " + AnsiCode.RESET);
        System.out.println(ansicolor + "            " + AnsiCode.RESET);
        System.out.println(ansicolor + "            " + AnsiCode.RESET);
        System.out.println(ansicolor + "            " + AnsiCode.RESET);

    }

    //nel caso di scritta: livello e worker
    public Stripe(String ansicolor, int value, boolean number){


            System.out.println(ansicolor + "            " + AnsiCode.RESET  );
            System.out.println(ansicolor + "    " + AnsiCode.TEXT_BLACK + "LV " +value + "    " + AnsiCode.RESET );
            System.out.println(ansicolor + "            " + AnsiCode.RESET  );
            System.out.println(ansicolor + "     " + AnsiCode.TEXT_BLACK + "W" +number + "     " + AnsiCode.RESET );
            System.out.println(ansicolor + "            " + AnsiCode.RESET );


    }
    //nel caso di cupola (due colori)
    public Stripe(String ansicolor, boolean dome){
        System.out.println(ansicolor + "            " + AnsiCode.RESET);
        System.out.println(ansicolor + "  " + AnsiCode.BACKGROUND_BLUE + "        " + ansicolor + "  " + AnsiCode.RESET );
        System.out.println(ansicolor + "  " + AnsiCode.BACKGROUND_BLUE + "        " + ansicolor + "  " + AnsiCode.RESET );
        System.out.println(ansicolor + "  " + AnsiCode.BACKGROUND_BLUE + "        " + ansicolor + "  " + AnsiCode.RESET );
        System.out.println(ansicolor + "            " + AnsiCode.RESET);
    }
}
