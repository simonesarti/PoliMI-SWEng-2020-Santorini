package it.polimi.ingsw.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PlayerPanel extends JPanel {

    private final ImageButton cardButton=new ImageButton();
    private String description;
    private final JLabel nicknameLabel=new JLabel();

    public PlayerPanel(String card, String nickname, Color color){

        setCardButton(card);
        setNicknameLabel(nickname,color);
        addComponents();

    }

    private void setNicknameLabel(String nickname,Color color){

        nicknameLabel.setText("Player "+nickname);
        nicknameLabel.setForeground(Color.WHITE);
        nicknameLabel.setOpaque(true);
        nicknameLabel.setBackground(color);
    }

    private void setCardButton(String card){

        if(card.contains("Apollo")){
            cardButton.setIcon(Images.getIcon(Images.APOLLO));
            cardButton.setToolTipText("Apollo");
        }else if(card.contains("Artemis")){
            cardButton.setButtonImage(Images.getImage(Images.ARTEMIS));
            cardButton.setToolTipText("Artemis");
        }else if(card.contains("Athena")){
            cardButton.setButtonImage(Images.getImage(Images.ATHENA));
            cardButton.setToolTipText("Athena");
        }else if(card.contains("Atlas")){
            cardButton.setIcon(Images.getIcon(Images.ATLAS));
            cardButton.setToolTipText("Atlas");
        }else if(card.contains("Demeter")){
            cardButton.setIcon(Images.getIcon(Images.DEMETER));
            cardButton.setToolTipText("Demeter");
        }else if(card.contains("Hephaestus")){
            cardButton.setIcon(Images.getIcon(Images.HEPHAESTUS));
            cardButton.setToolTipText("Hephaestus");
        }else if(card.contains("Minotaur")){
            cardButton.setIcon(Images.getIcon(Images.MINOTAUR));
            cardButton.setToolTipText("Minotaur");
        }else if(card.contains("Pan")){
            cardButton.setIcon(Images.getIcon(Images.PAN));
            cardButton.setToolTipText("Pan");
        }else if(card.contains("Prometheus")){
            cardButton.setIcon(Images.getIcon(Images.PROMETHEUS));
            cardButton.setToolTipText("Prometheus");
        }else{
            cardButton.setButtonImage(Images.getImage(Images.EMPTY_CARD));
            cardButton.setToolTipText("not used");
        }

        description=card;
    }

    private void addComponents(){
        setLayout(new GridBagLayout());
        add(cardButton,setConstraints(0,1,10));
        add(nicknameLabel,setConstraints(1,1,1));
        cardButton.addActionListener(new CardPressListener());
}

    private GridBagConstraints setConstraints(int gridy, int gridheight, int weighty) {

        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        //x position of component
        gridBagConstraints.gridx = 0;
        //y position of the component
        gridBagConstraints.gridy = gridy;
        //rows used by the component
        gridBagConstraints.gridheight = gridheight;
        // Number of columns the component takes up
        gridBagConstraints.gridwidth = 1;
        // Gives the layout manager a hint on how to adjust component width (0 equals fixed)
        gridBagConstraints.weightx = 1;
        // Gives the layout manager a hint on how to adjust component height (0 equals fixed)
        gridBagConstraints.weighty = weighty;

        gridBagConstraints.ipadx = 0;
        gridBagConstraints.ipady = 0;

        // Defines padding top, left, bottom, right
        gridBagConstraints.insets = new Insets(0,0,0,0);

        // Defines where to place components if they don't
        // fill the space: CENTER, NORTH, SOUTH, EAST, WEST
        // NORTHEAST, etc.
        gridBagConstraints.anchor = GridBagConstraints.CENTER;

        // How should the component be stretched to fill the
        // space: NONE, HORIZONTAL, VERTICAL, BOTH
        gridBagConstraints.fill = GridBagConstraints.BOTH;

        return gridBagConstraints;
    }

    private class CardPressListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(cardButton,description,"Card info",JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
