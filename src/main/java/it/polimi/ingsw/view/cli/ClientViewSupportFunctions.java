package it.polimi.ingsw.view.cli;

public class ClientViewSupportFunctions {

    public String nameToCorrectFormat(String s){
        return s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();
    }
}
