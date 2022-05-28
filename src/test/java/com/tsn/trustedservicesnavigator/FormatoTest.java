package com.tsn.trustedservicesnavigator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

//SAREBBE BELLO ANCHE METTERE DEI TIMEOUT NEI TEST

@DisplayName("A nomeClasse")
public class FormatoTest {
    //Classe nomeOggetto;
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
    @DisplayName("is instantiated with new costruttoreOggetto")
    void isInstantiatedWithNewClasse() {
        //new Classe();
    }
    @Nested
    @DisplayName("when new")
    class WhenNew{
        @BeforeEach
        void createACountry() {
        //nomeOggetto=new Classe()
        }
        @DisplayName("and I use the method ...")
        @Nested
        class nomeMetodo1{}
        @DisplayName("and I use the method ...")
        @Nested
        class nomeMetodo2{}}
}
