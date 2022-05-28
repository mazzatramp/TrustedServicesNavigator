package com.trustedservicesnavigator.domain;

import com.trustedservicesnavigator.domain.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

//SAREBBE BELLO ANCHE METTERE DEI TIMEOUT NEI TEST

@DisplayName("A Service")
public class ServiceTest {
    Service service;
    @Nested
    @DisplayName("when null")
    class WhenNull {
        @BeforeEach
        void setOggettoNull() {
            service = null;
        }
        @DisplayName("and I use the method ...")
        @Nested
        class Equals{}
        @DisplayName("and I use the method ...")
        @Nested
        class Clone{}
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
        class Equals{}
        @DisplayName("and I use the method ...")
        @Nested
        class Clone{}}
}
