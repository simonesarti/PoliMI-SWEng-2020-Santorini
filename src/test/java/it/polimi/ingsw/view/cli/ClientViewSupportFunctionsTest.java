package it.polimi.ingsw.view.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientViewSupportFunctionsTest {

    ClientViewSupportFunctions support;

    @BeforeEach
    void init(){
        support=new ClientViewSupportFunctions();
    }


    @Test
    void nameToCorrectFormat() {
        String s="hEllOMYnameIsSimone";
        assertEquals("Hellomynameissimone",support.nameToCorrectFormat(s));
    }

}