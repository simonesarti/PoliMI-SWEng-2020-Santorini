package it.polimi.ingsw.GUI;

import javax.swing.*;
import java.awt.*;


public class HowToUseGuiDialog extends JDialog {

    private class InternalPanel extends JPanel{

        private final Image background=Images.getImage(Images.RULE_BACKGROUND);

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(background,0,0,this.getWidth(),this.getHeight(),this);
        }
    }

    private InternalPanel panel;
    private JLabel title;
    private JTextArea explanation;

    public HowToUseGuiDialog(){

        setDialogProperties();
        setComponents();
        setLocations();
        addComponents();
        setVisible(true);
    }

    private void setDialogProperties() {
        setSize(400,400);
    }

    private void setComponents(){
        panel=new InternalPanel();

        title=new JLabel("How to play with the gui");
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setOpaque(false);
        Font t=new Font("Lucida Handwriting",Font.BOLD,15);
        title.setFont(t);


        explanation=new JTextArea();
        String text="The cards on the left hand side are the gods selected for this match, "+
                    "click on them if you want to see their description.\n\n"+
                    "To perform and action, press the corresponding button. If you want to "+
                    "move or build, you will have to select a cell containing the worker "+
                    "you want to  use, and then the cell on which it will perform the action.\n\n"+
                    "Pressing an \"action\" button before completing this steps will be "+
                    "interpreted as a decision to ignore the current action and perform "+
                    "the newly selected one";
        explanation.setText(text);
        //explanation.setLineWrap(true);
        explanation.setWrapStyleWord(true);
        explanation.setEditable(false);
        Font area=new Font("Helvetica",Font.PLAIN,12);
        explanation.setFont(area);
        explanation.setOpaque(false);
    }

    private void setLocations(){
        title.setBounds(50,100,100,30);
        explanation.setBounds(50,200,100,200);

    }

    private void addComponents() {
        this.add(panel);
        panel.add(title);
        panel.add(explanation);
    }

}
