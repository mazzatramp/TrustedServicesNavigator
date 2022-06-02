package com.trustedservices.navigator.components;

import com.trustedservices.Help;
import com.trustedservices.domain.TrustedList;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FilterPanesAccordionTest {
    //COSTRUTTORE
    //FORSE POSSO TESTARE MOLTI METODI MA DEVO CAPIRE SE POSSO USARE IL GET
    FilterPanesAccordion filterSelectionAccordion;
    @Disabled
    @Test
    @DisplayName("is instantiated with new DisplayPane()")
    void isInstantiatedWithNewClasse() {
        new FilterPanesAccordion();
    }

    @Nested
    @DisplayName("when null")
    class WhenNull {
        @BeforeEach
        void setOggettoNull() {
            filterSelectionAccordion= null;
        }
        @DisplayName("and I use the method fillWith")
        @Nested
        class fillFilterPanesWith{
            TrustedList argumentTrustedList;
            @Test
            @DisplayName("with a TrustedListasArgument")
            void met() throws IOException {
                argumentTrustedList= Help.getWholeList();
                assertThrows(NullPointerException.class, () -> filterSelectionAccordion.fillFilterPanesWith(argumentTrustedList));

            }
            @Test
            @DisplayName("with a null TrustedList as Argument")
            void metodo(){
                argumentTrustedList= null;
                assertThrows(NullPointerException.class, () -> filterSelectionAccordion.fillFilterPanesWith(argumentTrustedList));
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
            filterSelectionAccordion= new FilterPanesAccordion();
        }
        @DisplayName("and I use the method fillWith")
        @Nested
        class fillWith{
            TrustedList argumentTrustedList;
            @Test
            @DisplayName("with a TrustedListasArgument")
            void met() throws IOException {

            }
            @Test
            @DisplayName("with a null TrustedList as Argument")
            void metodo(){
           }

        }
        @DisplayName("and I use the method ...")
        @Nested
        class nomeMetodo2{}
    }
}