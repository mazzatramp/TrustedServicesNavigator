package com.trustedservices.navigator.components;

import com.trustedservices.domain.TrustedList;
import org.junit.jupiter.api.*;

import java.io.IOException;

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