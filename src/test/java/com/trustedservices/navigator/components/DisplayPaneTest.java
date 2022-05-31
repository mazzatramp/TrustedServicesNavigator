package com.trustedservices.navigator.components;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A DisplayPane")
public class DisplayPaneTest {
    DisplayPane displayPane;
    @Disabled
    @Nested
    @DisplayName("when null")
    class WhenNull {
        @BeforeEach
        void setOggettoNull() {
            //nomeOggetto=null;
        }
        @DisplayName("and I use the method ...")
        @Nested
        class nomeMetodo1{}
        @DisplayName("and I use the method ...")
        @Nested
        class nomeMetodo2{}
    }
    @Test
    @DisplayName("is instantiated with new DisplayPane()")
    void isInstantiatedWithNewClasse() {
        new DisplayPane();
    }
    //FILLWITH() NON DOVREI AVERE PROBLEMI A TESTARLO

    //Con mocks
    //TESTARE FXMLOADER LANCIA IO EXCEPTION

}