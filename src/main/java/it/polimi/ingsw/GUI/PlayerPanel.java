package it.polimi.ingsw.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel that contains the player's name/colour and card
 */
public class PlayerPanel extends JPanel {

    private final ImageButton cardButton=new ImageButton();
    private String description;
    private final JLabel nicknameLabel=new JLabel();

    public PlayerPanel(String nickname, Color color, String godName,String description){

        setNicknameLabel(nickname,color);
        setCardButton(godName,description);
        addComponents();
    }

    private void setNicknameLabel(String nickname,Color color){

        nicknameLabel.setText("Player "+nickname);
        nicknameLabel.setForeground(Color.WHITE);
        nicknameLabel.setOpaque(true);
        nicknameLabel.setBackground(color);
    }

    /**
     * associates the right image to the button based on the name of the god
     * @param godName is the god name
     * @param description is the god description
     */
    private void setCardButton(String godName, String description){

        switch(godName){
            case "Apollo" -> cardButton.setButtonImage(Images.getImage(Images.APOLLO));
            case "Artemis" -> cardButton.setButtonImage(Images.getImage(Images.ARTEMIS));
            case "Athena" -> cardButton.setButtonImage(Images.getImage(Images.ATHENA));
            case "Atlas" -> cardButton.setButtonImage(Images.getImage(Images.ATLAS));
            case "Demeter" -> cardButton.setButtonImage(Images.getImage(Images.DEMETER));
            case "Hephaestus" -> cardButton.setButtonImage(Images.getImage(Images.HEPHAESTUS));
            case "Minotaur" -> cardButton.setButtonImage(Images.getImage(Images.MINOTAUR));
            case "Pan" -> cardButton.setButtonImage(Images.getImage(Images.PAN));
            case "Prometheus" -> cardButton.setButtonImage(Images.getImage(Images.PROMETHEUS));
            case "X" -> cardButton.setButtonImage(Images.getImage(Images.EMPTY_CARD));
        }
        this.description=description;
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

    /**
     * shows a JOptionPane containing the god's description when the card button is pressed
     */
    private class CardPressListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(cardButton,description,"Card info",JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
