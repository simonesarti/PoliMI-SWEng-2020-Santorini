package it.polimi.ingsw.GUI;

import it.polimi.ingsw.client.ClientSideConnection;

import java.util.ArrayList;

public class AppGUI {

    public static void main(String[] args){

        //new GUI(new ClientSideConnection("127.0.0.1",12345));
        //new PlayerInfoRequestScreen(false);

        ArrayList<String> names=new ArrayList<>();
        names.add("Apollo");
        names.add("Artemis");
        names.add("Athena");
        names.add("Atlas");
        names.add("Demeter");
        names.add("Hephaestus");
        names.add("Minotaur");
        names.add("Pan");
        names.add("Prometheus");
        new DivinityChoiceScreen(names,3);


    }
}
