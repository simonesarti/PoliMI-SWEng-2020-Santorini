package it.polimi.ingsw.model;

import it.polimi.ingsw.messages.PlayerInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    @Nested
    class assignColour {

        private ArrayList<Player> setPlayersBirthDay2(int gg1, int gg2) {

            ArrayList<Player> players=new ArrayList<>();
            players.add(new Player(new PlayerInfo("x", new GregorianCalendar(1998, Calendar.SEPTEMBER, gg1), 2)));
            players.add(new Player(new PlayerInfo("x", new GregorianCalendar(1998, Calendar.SEPTEMBER, gg2), 2)));
            return players;
        }

        private ArrayList<Player> setPlayersBirthDay3(int gg1, int gg2, int gg3) {
            ArrayList<Player> players=new ArrayList<>();
            players.add(new Player(new PlayerInfo("x", new GregorianCalendar(1998, Calendar.SEPTEMBER, gg1), 3)));
            players.add(new Player(new PlayerInfo("x", new GregorianCalendar(1998, Calendar.SEPTEMBER, gg2), 3)));
            players.add(new Player(new PlayerInfo("x", new GregorianCalendar(1998, Calendar.SEPTEMBER, gg3), 3)));
            return players;

        }

        private void checkColour2(ArrayList<Player> players, Colour colour1, Colour colour2) {
            assertEquals(colour1, players.get(0).getColour());
            assertEquals(colour2, players.get(1).getColour());
        }

        private void checkColour3(ArrayList<Player> players, Colour colour1, Colour colour2, Colour colour3) {
            assertEquals(colour1, players.get(0).getColour());
            assertEquals(colour2, players.get(1).getColour());
            assertEquals(colour3, players.get(2).getColour());

        }

        Model model;

        @Nested
        class assignColour2 {

            @BeforeEach
            void init() {
                model = new Model(2);
            }

            @Test
            void WB_differentDay() {
                ArrayList<Player> players = setPlayersBirthDay2(2,1);
                model.assignColour(players);
                checkColour2(players, Colour.WHITE, Colour.BLUE);
            }

            @Test
            void WB_sameDay() {
                ArrayList<Player> players = setPlayersBirthDay2(1, 1);
                model.assignColour(players);
                checkColour2(players, Colour.WHITE, Colour.BLUE);
            }

            @Test
            void BW_differentDay() {
                ArrayList<Player> players = setPlayersBirthDay2(1,2);
                model.assignColour(players);
                checkColour2(players, Colour.BLUE, Colour.WHITE);
            }

        }

        @Nested
        class assignColour3{

            @BeforeEach
            void init() {
                model = new Model(3);
            }

            @Test
            void WBG_3sameDay(){
                ArrayList<Player> players = setPlayersBirthDay3(1,1,1);
                model.assignColour(players);
                checkColour3(players,Colour.WHITE,Colour.BLUE,Colour.GREY);
            }

            @Test
            void WBG_sameDay12(){
                ArrayList<Player> players = setPlayersBirthDay3(2,2,1);
                model.assignColour(players);
                checkColour3(players,Colour.WHITE,Colour.BLUE,Colour.GREY);
            }

            @Test
            void WGB_sameDay13(){
                ArrayList<Player> players = setPlayersBirthDay3(2,1,2);
                model.assignColour(players);
                checkColour3(players,Colour.WHITE,Colour.GREY,Colour.BLUE);
            }

            @Test
            void WBG_sameDay23(){
                ArrayList<Player> players = setPlayersBirthDay3(2,1,1);
                model.assignColour(players);
                checkColour3(players,Colour.WHITE,Colour.BLUE,Colour.GREY);
            }

            @Test
            void GWB_sameDay23(){
                ArrayList<Player> players = setPlayersBirthDay3(1,2,2);
                model.assignColour(players);
                checkColour3(players,Colour.GREY,Colour.WHITE,Colour.BLUE);
            }

            @Test
            void reverse(){
                ArrayList<Player> players = setPlayersBirthDay3(1,2,3);
                model.assignColour(players);
                checkColour3(players,Colour.GREY,Colour.BLUE,Colour.WHITE);
            }

            @Test
            void BWG_sameDay13(){
                ArrayList<Player> players = setPlayersBirthDay3(1,2,1);
                model.assignColour(players);
                checkColour3(players,Colour.BLUE,Colour.WHITE,Colour.GREY);
            }

            @Test
            void BGW(){
                ArrayList<Player> players = setPlayersBirthDay3(2,1,3);
                model.assignColour(players);
                checkColour3(players,Colour.BLUE,Colour.GREY,Colour.WHITE);
            }

            @Test
            void WGB(){
                ArrayList<Player> players = setPlayersBirthDay3(3,1,2);
                model.assignColour(players);
                checkColour3(players,Colour.WHITE,Colour.GREY,Colour.BLUE);
            }


        }
    }

}