package it.polimi.ingsw.GUI;

import it.polimi.ingsw.view.ClientViewSupportFunctions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PlayerInfoRequestScreen extends JDialog implements ActionListener{

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

    JButton confirmButton;

    public PlayerInfoRequestScreen(boolean isNicknameTaken) {

        nicknameRequest = new JLabel("Nickname: ");
        nickname = new JTextField();
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

        confirmButton.setBounds(125, 130, 150, 20);
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
        add(confirmButton);
    }

    private void dialogSettings() {
        //setTitle("Info Request");
        setSize(400, 210);
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
                //TODO COSA SUCCEDE, COSA INVIA
                dispose();
            }
        }
    }
}