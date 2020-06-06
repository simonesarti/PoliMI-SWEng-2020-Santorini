package it.polimi.ingsw.GUI;

import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.CardChoice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DivinityChoiceDialog extends JDialog{

    private final GuiController guiController;
    private final ArrayList<String> selected=new ArrayList<>();
    private final int toSelect;
    private final int total;
    private final GodNameLink[][] links;


    public DivinityChoiceDialog(ArrayList<String> names, int n, GuiController guiController) {

        this.guiController=guiController;

        toSelect=n;
        total=names.size();

        setTitle("Gods");
        setLayout(new GridLayout(total/4+1, 4));
        links=new GodNameLink[4][total/4+1];

        for(int j=0;j<total/4+1;j++){
            for(int i=0;i<4;i++){
                links[i][j]=new GodNameLink();

                //creates buttons
                links[i][j].setButton(new JButton());

                //associates god names
                int index=i+4*j;
                if(index<total){
                    links[i][j].setName(names.get(index));
                }else{
                    links[i][j].setName("Empty");
                }
                //sets icon based on name
                links[i][j].setButtonIcon();
                links[i][j].getButton().setBackground(Color.GRAY);

                //adds to the layout
                add(links[i][j].getButton());

                links[i][j].getButton().addActionListener(new CardSelectionListener());
            }
        }

        pack();
        setVisible(true);
    }



    private GodNameLink findLink(JButton button){

        for(int j=0;j<total/4+1;j++){
            for(int i=0;i<4;i++){
                if(button.equals(links[i][j].getButton())){
                    return links[i][j];
                }
            }
        }
        throw new IllegalArgumentException("link not found");
    }


    private class CardSelectionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            JButton pressed=(JButton)e.getSource();

            GodNameLink link=findLink(pressed);

            if(!link.getName().equals("Empty")){
                if (selected.contains(link.getName())){
                    selected.remove(link.getName());
                    pressed.setBackground(Color.GRAY);
                    //System.out.println("removed "+link.getName());

                } else {
                    pressed.setBackground(Color.CYAN);
                    selected.add(link.getName());
                    //System.out.println("added "+link.getName());
                }
                repaint();

                if (selected.size() == toSelect) {

                    //TODO finire
/*
                    //DEBUG
                    for(String selected : selected.toArray(String[]::new)){
                        System.out.println(selected+",");
                    }
*/

                    //guiController.send(new CardChoice(selected.toArray(String[]::new)));
                    dispose();
                }
            }


        }
    }


    private class GodNameLink {

        private String name;
        private JButton button;

        public String getName() {
            return name;
        }

        public JButton getButton() {
            return button;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setButton(JButton button) {
            this.button = button;
        }

        public void setButtonIcon(){

            switch(name){

                case "Apollo":
                    button.setIcon(new ImageIcon(Images.getImage(Images.APOLLO).getScaledInstance(200,200,Image.SCALE_SMOOTH)));
                    break;

                case "Artemis":
                    button.setIcon(new ImageIcon(Images.getImage(Images.ARTEMIS).getScaledInstance(200,200,Image.SCALE_SMOOTH)));
                    break;

                case "Athena":
                    button.setIcon(new ImageIcon(Images.getImage(Images.ATHENA).getScaledInstance(200,200,Image.SCALE_SMOOTH)));
                    break;

                case "Atlas":
                    button.setIcon(new ImageIcon(Images.getImage(Images.ATLAS).getScaledInstance(200,200,Image.SCALE_SMOOTH)));
                    break;

                case "Demeter":
                    button.setIcon(new ImageIcon(Images.getImage(Images.DEMETER).getScaledInstance(200,200,Image.SCALE_SMOOTH)));
                    break;

                case "Hephaestus":
                    button.setIcon(new ImageIcon(Images.getImage(Images.HEPHAESTUS).getScaledInstance(200,200,Image.SCALE_SMOOTH)));
                    break;

                case "Minotaur":
                    button.setIcon(new ImageIcon(Images.getImage(Images.MINOTAUR).getScaledInstance(200,200,Image.SCALE_SMOOTH)));
                    break;

                case "Pan":
                    button.setIcon(new ImageIcon(Images.getImage(Images.PAN).getScaledInstance(200,200,Image.SCALE_SMOOTH)));
                    break;

                case "Prometheus":
                    button.setIcon(new ImageIcon(Images.getImage(Images.PROMETHEUS).getScaledInstance(200,200,Image.SCALE_SMOOTH)));
                    break;

                case "Empty":
                    button.setIcon(new ImageIcon(Images.getImage(Images.EMPTY_CARD).getScaledInstance(200,200,Image.SCALE_SMOOTH)));
                    break;

            }
        }
    }


}
