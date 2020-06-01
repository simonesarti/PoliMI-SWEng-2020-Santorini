package it.polimi.ingsw.GUI;

import it.polimi.ingsw.messages.GameToPlayerMessages.Notify.NewBoardStateMessage;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainPanel extends JPanel{

    private final Image background;
    private GamePanel gamePanel;
    private CardsPanel cardsPanel;
    private ChoicePanel choicePanel;

    public MainPanel(JFrame frame){
        background=Images.getImage(Images.INITIAL_BACKGROUND);
        setPreferredSize(new Dimension(frame.getHeight(),frame.getWidth()));
        //TODO
        //DEBUG
        ArrayList<String> cards=new ArrayList<>();
        ArrayList<String> nicknames=new ArrayList<>();
        cards.add("Athena");
        cards.add("Artemis");
        nicknames.add("Oli");
        nicknames.add("Ale");

        setMatchGui(cards,nicknames);
    }

    public void setMatchGui(ArrayList<String> cards, ArrayList<String> nicknames){
        setLayout(new GridBagLayout());

        gamePanel=new GamePanel();
        choicePanel=new ChoicePanel();
        cardsPanel=new CardsPanel(cards,nicknames);
        setInternalPanel(cardsPanel,0,0,1,1,1,1,0,0,0,0,0,0,10,1);
        setInternalPanel(choicePanel,1,0,1,1,1,1,0,0,0,0,0,0,10,1);
        setInternalPanel(gamePanel,2,0,1,20,10,1,0,0,0,0,0,0,10,1);

    }

    public void updateBoard(NewBoardStateMessage message){
        gamePanel.updateBoard(message);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background,0,0,this.getWidth(),this.getHeight(),this);
    }

    private void setInternalPanel(JPanel panel, int gridx, int gridy, int gridheight, int gridwidth, int weightx, int weighty,int ipadx, int ipady, int insetsTop,int insetsBottom, int insetsLeft, int insetsRight, int anchor, int fill){

            GridBagConstraints gridBagConstraints=new GridBagConstraints();

            //x position of component
            gridBagConstraints.gridx = gridx;
            //y position of the component
            gridBagConstraints.gridy = gridy;
            //rows used by the component
            gridBagConstraints.gridheight = gridheight;
            // Number of columns the component takes up
            gridBagConstraints.gridwidth = gridwidth;

            // Gives the layout manager a hint on how to adjust
            // component width (0 equals fixed)
            gridBagConstraints.weightx = weightx;

            // Gives the layout manager a hint on how to adjust
            // component height (0 equals fixed)
            gridBagConstraints.weighty = weighty;

            gridBagConstraints.ipadx= ipadx;
            gridBagConstraints.ipady= ipady;

            // Defines padding top, left, bottom, right
            gridBagConstraints.insets = new Insets(insetsTop,insetsLeft,insetsBottom,insetsRight);

            // Defines where to place components if they don't
            // fill the space: CENTER, NORTH, SOUTH, EAST, WEST
            // NORTHEAST, etc.
            gridBagConstraints.anchor = anchor;

            // How should the component be stretched to fill the
            // space: NONE, HORIZONTAL, VERTICAL, BOTH
            gridBagConstraints.fill = fill;

            add(panel,gridBagConstraints);

    }


}
