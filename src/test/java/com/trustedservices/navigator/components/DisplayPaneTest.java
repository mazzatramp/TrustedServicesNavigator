package com.trustedservices.navigator.components;

import com.trustedservices.Help;
import com.trustedservices.domain.TrustedList;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("A DisplayPane")
public class DisplayPaneTest {
    DisplayPane displayPane;
    @Disabled
    @Test
    @DisplayName("is instantiated with new DisplayPane()")
    void isInstantiatedWithNewClasse() {
        //NON SO PERCHE NON FUNZIONI
        new DisplayPane();
    }

    @Nested
    @DisplayName("when null")
    class WhenNull {
        @BeforeEach
        void setOggettoNull() {
         displayPane= null;
        }
        @DisplayName("and I use the method fillWith")
        @Nested
        class fillWith{
            TrustedList argumentTrustedList;
            @Test
            @DisplayName("with a TrustedListasArgument")
            void met() throws IOException {
                argumentTrustedList= Help.getWholeList();
                assertThrows(NullPointerException.class, () -> displayPane.fillWith(argumentTrustedList));

            }
            @Test
            @DisplayName("with a null TrustedList as Argument")
            void metodo(){
                argumentTrustedList= null;
                assertThrows(NullPointerException.class, () -> displayPane.fillWith(argumentTrustedList));
            }

        }
        @DisplayName("and I use the method ...")
        @Nested
        class nomeMetodo2{}
    }
    @Disabled
    @Nested
    @DisplayName("when new")
    class WhenNew {
        @BeforeEach
        void setOggettoNull() {
            displayPane= new DisplayPane();
        }
        @DisplayName("and I use the method fillWith")
        @Nested
        class fillWith{
            TrustedList argumentTrustedList;
            @Test
            @DisplayName("with a TrustedListasArgument")
            void met() throws IOException {
                argumentTrustedList= Help.getWholeList();
                assertThrows(NullPointerException.class, () -> displayPane.fillWith(argumentTrustedList));

            }
            @Test
            @DisplayName("with a null TrustedList as Argument")
            void metodo(){
                argumentTrustedList= null;
                assertThrows(NullPointerException.class, () -> displayPane.fillWith(argumentTrustedList));
            }

        }
        @DisplayName("and I use the method ...")
        @Nested
        class nomeMetodo2{}
    }

}
    //FILLWITH() NON DOVREI AVERE PROBLEMI A TESTARLO

    //Con mocks
    //TESTARE FXMLOADER LANCIA IO EXCEPTION