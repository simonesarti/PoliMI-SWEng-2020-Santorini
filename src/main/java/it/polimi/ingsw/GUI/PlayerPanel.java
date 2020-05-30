package it.polimi.ingsw.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PlayerPanel extends JPanel {

    private final JButton cardButton=new JButton();
    private String description;
    private final JLabel nicknameLabel;

    public PlayerPanel(String card, String nickname, Color color){
        setCardButton(card);

        if(nickname.equals("nickname not used")){
            nicknameLabel=new JLabel();
        }else{
            nicknameLabel=new JLabel("Player "+nickname);

        }
        nicknameLabel.setForeground(Color.WHITE);
        nicknameLabel.setBackground(color);

        setPanel();
    }

    private void setCardButton(String card){

        if(card.contains("Apollo")){
            cardButton.setIcon(Images.getIcon(Images.APOLLO));
            cardButton.setToolTipText("Apollo");
        }else if(card.contains("Artemis")){
            cardButton.setIcon(Images.getIcon(Images.ARTEMIS));
            cardButton.setToolTipText("Artemis");
        }else if(card.contains("Athena")){
            cardButton.setIcon(Images.getIcon(Images.ATHENA));
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
            cardButton.setIcon(Images.getIcon(Images.EMPTY_CARD));
            cardButton.setToolTipText("not used");
        }

        description=card;
    }

    private void setPanel(){
        setLayout(new BorderLayout());
        add(cardButton,BorderLayout.NORTH);
        add(nicknameLabel,BorderLayout.SOUTH);
        cardButton.addMouseListener(new CardPressListener());
        setVisible(true);
}

    private class CardPressListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            JOptionPane.showMessageDialog(cardButton,description,"Card info",JOptionPane.INFORMATION_MESSAGE);
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

}
