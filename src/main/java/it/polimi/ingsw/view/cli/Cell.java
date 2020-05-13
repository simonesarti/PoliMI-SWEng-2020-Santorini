package it.polimi.ingsw.view.cli;


/*
public class Cell {

    private String[] stripes = new String[4];

    public Cell(){
        for (int i=0; i<5; i++){
            stripes[i] = new String();
        }

    }

    public void FirstEmptyCell(){
        stripes[0] = "\u001B[42m            \u001B[0m";
        stripes[1] = "\u001B[42m            \u001B[0m";
        stripes[2] = "\u001B[42m            \u001B[0m";
        stripes[3] = "\u001B[42m            \u001B[0m";
        stripes[4] = "\u001B[42m            \u001B[0m";
    }

    public void FirstCellWithWorker1(){
        stripes[0] = "\u001B[42m            \u001B[0m";
        stripes[1] = "\u001B[42m            \u001B[0m";
        stripes[2] = "\u001B[42m     \u001B[30mW1     \u001B[0m";
        stripes[3] = "\u001B[42m            \u001B[0m";
        stripes[4] = "\u001B[42m            \u001B[0m";
    }

    public void FirstCellWithWorker2(){
        stripes[0] = "\u001B[42m            \u001B[0m";
        stripes[1] = "\u001B[42m            \u001B[0m";
        stripes[2] = "\u001B[42m     \u001B[30mW2     \u001B[0m";
        stripes[3] = "\u001B[42m            \u001B[0m";
        stripes[4] = "\u001B[42m            \u001B[0m";
    }


    public void Level1Cell (){
        stripes[0] = "\u001B[47m            \u001B[0m";
        stripes[1] = "\u001B[47m            \u001B[0m";
        stripes[2] = "\u001B[47m    \u001B[30mLV 1    \u001B[0m";
        stripes[3] = "\u001B[47m            \u001B[0m";
        stripes[4] = "\u001B[47m            \u001B[0m";
    }

    public void Level2Cell (){
        stripes[0] = "\u001B[47m            \u001B[0m";
        stripes[1] = "\u001B[47m            \u001B[0m";
        stripes[2] = "\u001B[47m    \u001B[30mLV 2    \u001B[0m";
        stripes[3] = "\u001B[47m            \u001B[0m";
        stripes[4] = "\u001B[47m            \u001B[0m";
    }

    public void Level3Cell (){
        stripes[0] = "\u001B[47m            \u001B[0m";
        stripes[1] = "\u001B[47m            \u001B[0m";
        stripes[2] = "\u001B[47m    \u001B[30mLV 3    \u001B[0m";
        stripes[3] = "\u001B[47m            \u001B[0m";
        stripes[4] = "\u001B[47m            \u001B[0m";
    }

    public void Level1CellWithWorker1 (){
        stripes[0] = "\u001B[47m            \u001B[0m";
        stripes[1] = "\u001B[47m    \u001B[30mLV 1    \u001B[0m";
        stripes[2] = "\u001B[47m            \u001B[0m";
        stripes[3] = "\u001B[47m     \u001B[30mW1     \u001B[0m";
        stripes[4] = "\u001B[47m            \u001B[0m";
    }

    public void Level1CellWithWorker2 (){
        stripes[0] = "\u001B[47m            \u001B[0m";
        stripes[1] = "\u001B[47m    \u001B[30mLV 1    \u001B[0m";
        stripes[2] = "\u001B[47m            \u001B[0m";
        stripes[3] = "\u001B[47m     \u001B[30mW2     \u001B[0m";
        stripes[4] = "\u001B[47m            \u001B[0m";
    }

    public void Level2CellWithWorker1 (){
        stripes[0] = "\u001B[47m            \u001B[0m";
        stripes[1] = "\u001B[47m    \u001B[30mLV 2    \u001B[0m";
        stripes[2] = "\u001B[47m            \u001B[0m";
        stripes[3] = "\u001B[47m     \u001B[30mW1     \u001B[0m";
        stripes[4] = "\u001B[47m            \u001B[0m";
    }

    public void Level2CellWithWorker2 (){
        stripes[0] = "\u001B[47m            \u001B[0m";
        stripes[1] = "\u001B[47m    \u001B[30mLV 2    \u001B[0m";
        stripes[2] = "\u001B[47m            \u001B[0m";
        stripes[3] = "\u001B[47m     \u001B[30mW2     \u001B[0m";
        stripes[4] = "\u001B[47m            \u001B[0m";
    }

    public void Level3CellWithWorker1 (){
        stripes[0] = "\u001B[47m            \u001B[0m";
        stripes[1] = "\u001B[47m    \u001B[30mLV 3    \u001B[0m";
        stripes[2] = "\u001B[47m            \u001B[0m";
        stripes[3] = "\u001B[47m     \u001B[30mW1     \u001B[0m";
        stripes[4] = "\u001B[47m            \u001B[0m";
    }

    public void Level3CellWithWorker2 (){
        stripes[0] = "\u001B[47m            \u001B[0m";
        stripes[1] = "\u001B[47m    \u001B[30mLV 3    \u001B[0m";
        stripes[2] = "\u001B[47m            \u001B[0m";
        stripes[3] = "\u001B[47m     \u001B[30mW2     \u001B[0m";
        stripes[4] = "\u001B[47m            \u001B[0m";
    }

    public void CellWithDome(){
        stripes[0] = "\u001B[47m            \u001B[0m";
        stripes[1] = "\u001B[47m  \u001B[44m        \u001B[47m  \u001B[0m";
        stripes[2] = "\u001B[47m  \u001B[44m        \u001B[47m  \u001B[0m";
        stripes[3] = "\u001B[47m  \u001B[44m        \u001B[47m  \u001B[0m";
        stripes[4] = "\u001B[47m            \u001B[0m";
    }

    public void FirstCellWithDome(){
        stripes[0] = "\u001B[42m            \u001B[0m";
        stripes[1] = "\u001B[42m  \u001B[44m        \u001B[42m  \u001B[0m";
        stripes[2] = "\u001B[42m  \u001B[44m        \u001B[42m  \u001B[0m";
        stripes[3] = "\u001B[42m  \u001B[44m        \u001B[42m  \u001B[0m";
        stripes[4] = "\u001B[42m            \u001B[0m";
    }



    private String uniformStripe(String BackgroundAnsiColour){
        String stripe=...;
        return stripe;
    }

    private String domeStripe(String BackgroundAnsiColour){
        String stripe=...;
        return stripe;
    }

    private String coordinateStripe(int value){
        String stripe=...;
        //in cui ha i ...+value.toString()+....
        //scegli tu colore backgruond di questa
        return stripe;
    }

    private String workerStripe(String BackgroundAnsiColour,int workerNumber){
        //concetto di prima con coordinata, ci infili il numero come stringa dentro l'ansi+ il W o W: o come vuoi tu
    }

    private String levelStripe(int workerNumber){
        //concetto di prima con coordinata, ci infili il numero come stringa dentro l'ansi+ il W o W: o come vuoi tu

        //2 possibilità: indichiamo sempre il livello, in quel caso ci sia aggiunge anche il colore di sfondo
        //perchè scriveremmo lv0 anche nella cella vuota e dobbiamo distingue verde/grigio
        //oppure lasciarlo così e dire che se chiamo levelStrpe è perchè c'è almeno un livello => colore=grigio
    }




}

 */
