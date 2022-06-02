package com.trustedservices.navigator.web;

import com.trustedservices.Help;
import com.trustedservices.domain.TrustedList;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
@Disabled
class TrustedJsonBuilderTest {
TrustedJsonBuilder trustedJsonBuilder;

    @Test
    @DisplayName("is instantiated with new costruttoreOggetto")
    void isInstantiatedWithNewClasse() {
        new TrustedJsonBuilder();
    }
    @Nested
    @DisplayName("when new")
    class WhenNew{
        @BeforeEach
        void createACountry() {
        trustedJsonBuilder= new TrustedJsonBuilder();
        }
        @DisplayName("and I use the method ...")
        @Nested
        class build{
            @DisplayName("if there are no errors")
            @Test
            void prova() throws IOException {
            TrustedList list = trustedJsonBuilder.build();
                assertEquals(list, Help.getWholeList()); //LA LISTA CHE OTTENGO E' QUELLA NEL FILE IN LOCALE VERO?
            }
            /*
            @DisplayName("if JsonObjectMapper returns error ")
            @Test
            void provaErrore() throws IOException {
                TrustedList list = trustedJsonBuilder.build();
                assertEquals(list, Help.getWholeList()); //LA LISTA CHE OTTENGO E' QUELLA NEL FILE IN LOCALE VERO?
            }

             */

        }
        }
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
}
