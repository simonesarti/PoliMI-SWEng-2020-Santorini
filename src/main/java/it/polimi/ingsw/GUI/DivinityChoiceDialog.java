package it.polimi.ingsw.GUI;

import it.polimi.ingsw.GUI.messages.ActionMessage;
import it.polimi.ingsw.GUI.messages.SelectedGods;
import it.polimi.ingsw.observe.Observable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DivinityChoiceDialog extends JDialog{

    private class GodNameLink {

        private String name;
        private JButton button;
        private final int imageDim=150;

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

            switch (name) {
                case "Apollo" -> button.setIcon(new ImageIcon(Images.getImage(Images.APOLLO).getScaledInstance(imageDim, imageDim, Image.SCALE_SMOOTH)));
                case "Artemis" -> button.setIcon(new ImageIcon(Images.getImage(Images.ARTEMIS).getScaledInstance(imageDim, imageDim, Image.SCALE_SMOOTH)));
                case "Athena" -> button.setIcon(new ImageIcon(Images.getImage(Images.ATHENA).getScaledInstance(imageDim, imageDim, Image.SCALE_SMOOTH)));
                case "Atlas" -> button.setIcon(new ImageIcon(Images.getImage(Images.ATLAS).getScaledInstance(imageDim, imageDim, Image.SCALE_SMOOTH)));
                case "Demeter" -> button.setIcon(new ImageIcon(Images.getImage(Images.DEMETER).getScaledInstance(imageDim, imageDim, Image.SCALE_SMOOTH)));
                case "Hephaestus" -> button.setIcon(new ImageIcon(Images.getImage(Images.HEPHAESTUS).getScaledInstance(imageDim, imageDim, Image.SCALE_SMOOTH)));
                case "Minotaur" -> button.setIcon(new ImageIcon(Images.getImage(Images.MINOTAUR).getScaledInstance(imageDim, imageDim, Image.SCALE_SMOOTH)));
                case "Pan" -> button.setIcon(new ImageIcon(Images.getImage(Images.PAN).getScaledInstance(imageDim, imageDim, Image.SCALE_SMOOTH)));
                case "Prometheus" -> button.setIcon(new ImageIcon(Images.getImage(Images.PROMETHEUS).getScaledInstance(imageDim, imageDim, Image.SCALE_SMOOTH)));
                case "Empty" -> button.setIcon(new ImageIcon(Images.getImage(Images.EMPTY_CARD).getScaledInstance(imageDim, imageDim, Image.SCALE_SMOOTH)));
            }
        }
    }

    private final GuiController guiController;
    private final ArrayList<String> selected=new ArrayList<>();
    
    private final int toSelect;
    private int total;
    private int rows;
    private int columns;
    
    private GodNameLink[][] links;


    public DivinityChoiceDialog(JFrame frame, ArrayList<String> names, int n, GuiController guiController) {

        super(frame);

        this.guiController=guiController;

        toSelect=n;
        setDisposition(names.size());
        setDialogParameters();
        setGrid(names);
        showDialog(frame);



    }


    private void setDialogParameters(){
        setTitle("Gods");
        setLayout(new GridLayout(rows, columns));
    }
    private void setDisposition(int tot){
        total=tot;
        columns=4;
        
        if(total%columns==0){
            rows=total/columns;
        }else{
            rows=total/columns+1;
        }
    }
    private void setGrid(ArrayList<String> names){

        links=new GodNameLink[columns][rows];

        for(int j=0;j<rows;j++){
            for(int i=0;i<columns;i++){
                links[i][j]=new GodNameLink();

                //creates buttons
                links[i][j].setButton(new JButton());

                //associates god names
                int index=i+columns*j;
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
    }
    private void showDialog(JFrame frame){
        pack();
        setLocationRelativeTo(frame);
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

    private class CardSelectionListener extends Observable<ActionMessage> implements  ActionListener{

        public CardSelectionListener() {
            this.addObserver(guiController);
        }

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

                    dispose();
                    notify(new SelectedGods(selected.toArray(String[]::new)));
                }
            }


        }
    }





}
