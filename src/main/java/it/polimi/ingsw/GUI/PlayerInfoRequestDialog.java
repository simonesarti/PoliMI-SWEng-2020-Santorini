package it.polimi.ingsw.GUI;

import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.PlayerInfo;
import it.polimi.ingsw.view.ClientViewSupportFunctions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

public class PlayerInfoRequestDialog extends JDialog implements ActionListener{

    GuiController guiController;

    JLabel nicknameRequest;
    JLabel invalidNickname;
    JTextField nickname;

    JLabel birthdayRequest;
    JTextField day;
    JTextField month;
    JTextField year;
    JTextField divider1;
    JTextField divider2;
    JLabel invalidBirthday;

    JLabel numberOfPlayerRequest;
    JSpinner numberOfPlayers;

    JButton confirmButton;

    public PlayerInfoRequestDialog(boolean isNicknameTaken, GuiController guiController) {

        setTitle("Information request");

        this.guiController=guiController;

        nicknameRequest = new JLabel("Nickname: ");
        this.nickname = new JTextField();
        invalidNickname = new JLabel();

        birthdayRequest = new JLabel("Date of birth: ");
        day = new JTextField();
        month = new JTextField();
        year = new JTextField();
        divider1 = new JTextField("/");
        divider1.setEditable(false);
        divider2 = new JTextField("/");
        divider2.setEditable(false);
        invalidBirthday = new JLabel();

        numberOfPlayerRequest=new JLabel("Number of players in game: ");
        numberOfPlayers=new JSpinner(new SpinnerNumberModel(2,2,3,1));
        numberOfPlayers.setEditor(new JSpinner.DefaultEditor(numberOfPlayers));

        confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(this);

        if (isNicknameTaken) {
            invalidNickname.setText("This username is already taken");
            invalidNickname.setForeground(Color.RED);
        }

        dialogSettings();
        setBounds();
        addToDialog();


    }

    private void setBounds() {

        nicknameRequest.setBounds(20, 20, 100, 20);
        nickname.setBounds(120, 20, 200, 20);
        invalidNickname.setBounds(120, 40, 200, 20);

        birthdayRequest.setBounds(20, 70, 100, 20);
        day.setBounds(120, 70, 50, 20);
        divider1.setBounds(170, 70, 10, 20);
        month.setBounds(180, 70, 50, 20);
        divider2.setBounds(230, 70, 10, 20);
        year.setBounds(240, 70, 50, 20);
        invalidBirthday.setBounds(120, 90, 200, 20);

        numberOfPlayerRequest.setBounds(20,120,160,20);
        numberOfPlayers.setBounds(200,120,40,20);

        confirmButton.setBounds(125, 160, 150, 20);
    }

    private void addToDialog() {
        add(nicknameRequest);
        add(nickname);
        add(invalidNickname);

        add(birthdayRequest);
        add(day);
        add(month);
        add(year);
        add(divider1);
        add(divider2);
        add(invalidBirthday);

        add(numberOfPlayerRequest);
        add(numberOfPlayers);

        add(confirmButton);
    }

    private void dialogSettings() {
        //setTitle("Info Request");
        setSize(400, 250);
        setLayout(null);
        setVisible(true);
    }

    private boolean isNameNotValid(String string) {
        return (string.equals("") || isOnlySpace(string));
    }

    private boolean isOnlySpace(String string) {

        char[] array = string.toCharArray();

        for (int i = 0; i < string.length(); i++) {
            if (array[i] != ' ') {
                return false;
            }
        }

        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (isNameNotValid(nickname.getText())) {
            invalidNickname.setForeground(Color.red);
            invalidNickname.setText("Invalid nickname");
            invalidBirthday.setText("");
        } else {
            invalidNickname.setText("");
            ClientViewSupportFunctions support = new ClientViewSupportFunctions();
            if (!support.isDateValid((day.getText() + "-" + month.getText() + "-" + year.getText()), day.getText(), month.getText(), year.getText())) {
                invalidBirthday.setForeground(Color.red);
                invalidBirthday.setText("Invalid date");
            } else {
                //TODO da finire

                guiController.setNickname(nickname.getText());

                //DEBUG
                System.out.println("sent "+nickname.getText()+"\ndate: "+Integer.parseInt(year.getText())+","+(Integer.parseInt(month.getText())-1)+","+Integer.parseInt(day.getText())+"\n number "+numberOfPlayers.getValue());
                //guiController.send(new PlayerInfo(nickname.getText(),new GregorianCalendar(Integer.parseInt(year.getText()),(Integer.parseInt(month.getText())-1),Integer.parseInt(day.getText())),(int)numberOfPlayers.getValue()));
                dispose();
            }
        }
    }
}