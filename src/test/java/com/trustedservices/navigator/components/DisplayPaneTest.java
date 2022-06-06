package com.trustedservices.navigator.components;

import com.trustedservices.DummyTrustedList;
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
            void met() {
                DummyTrustedList dummyTrustedList = DummyTrustedList.getInstance();
                argumentTrustedList= dummyTrustedList.getDummyTrustedList();
                assertThrows(NullPointerException.class, () -> displayPane.fillWith(argumentTrustedList));

            }
            @Test
            @DisplayName("with a null TrustedList as Argument")
            void metodo(){
                argumentTrustedList= null;
                assertThrows(NullPointerException.class, () -> displayPane.fillWith(argumentTrustedList));
            }

        }
    }

}
    //FILLWITH() NON DOVREI AVERE PROBLEMI A TESTARLO

    //Con mocks
    //TESTARE FXMLOADER LANCIA IO EXCEPTION