package com.trustedservicesnavigator.frontend.filters;

import com.trustedservicesnavigator.frontend.filters.StatusFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("A StatusFilter")
public class StatusFilterTest {
    StatusFilter sf;
    @Nested
    @DisplayName("when null")
    class WhenNull {
        @BeforeEach
        void setOggettoNull() {
          sf=null;
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