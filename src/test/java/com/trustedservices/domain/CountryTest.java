package com.trustedservices.domain;

import com.trustedservices.Help;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A Country")
class CountryTest {
    Country country;

    @Test
    @DisplayName("is instantiated with new Country(String,String)")
    void isInstantiatedWithNewCountry() {
        new Country("Italia", "IT");
    }

    @Nested
    @DisplayName("when new")
    class WhenNew {
        @BeforeEach
        void createACountry() {
            country = new Country("Austria", "AT"); //SE USO COSI' IL COSTRUTTURE PERO' DOPO IL METODO CLONE NON LO TESTA TUTTO
        }

        @DisplayName("and I use the method equals(Object)")
        @Nested
        class Equals {
            Country argumentCountry;
                @DisplayName("and the two countries are the same object, method should return true")
                @Test
                void SameCountryObjectAsArgument() {
                    //arrange
                    argumentCountry = country;
                    //act
                    boolean areCountriesTHeSame = country.equals(argumentCountry);
                    //assert
                    assertTrue(areCountriesTHeSame);

                }

            @DisplayName("and the two countries are the same , method should return true")
            @Test
            void SameCountryAsArgument() {

                argumentCountry =  new Country("Austria", "AT");;

                boolean areCountriesTHeSame = country.equals(argumentCountry);

                assertTrue(areCountriesTHeSame);
            }

            @DisplayName("and the two countries are not the same, method should return false")
                @Test
                void NotSameCountryAsArgument() {

                    argumentCountry = new Country("Italia", "IT");

                    boolean areCountriesTHeSame = country.equals(argumentCountry);

                    assertFalse(areCountriesTHeSame);
                }
                @DisplayName("with a country null as argument, method should return false")
                @Test
                void NullAsArgument() {

                    argumentCountry = null;

                    boolean areCountriesTheSame = country.equals(argumentCountry);

                    assertFalse(areCountriesTheSame);
                }
            /*
            DOVREI FARLO CON STESSI VALORI MA OGGETTO DIVERSO, NON PENSO NEL CASO PRIMA FOSSE COSI
            */
        }

        @DisplayName("and I use the method compareTo(Country)")
        @Nested
        class CompareTo {

               Country argumentCountry;

                @DisplayName("and the two countries are the same, method should return 0")
                @Test
                void SameCountryAsArgument() throws IOException {

                    argumentCountry = country;

                    int comparison = country.compareTo(argumentCountry);
                    int expectedreturn = 0;

                    assertEquals(expectedreturn, comparison);
                }

                @DisplayName("and the argument country is greater, method should return a negative number")
                @Test
                void aCountry_CompareTo_BiggerCountry_ReturnNegative() throws IOException {

                    argumentCountry = Help.getCountryN(1);

                    int comparison = country.compareTo(argumentCountry);

                    assertTrue(comparison < 0);

                }
                @DisplayName("and the argument country is lower, method should return a positive number")
                @Test
                void aCountry_CompareTo_LowerCountry_ReturnNegative() {
                    argumentCountry = country;
                    country = Help.getCountryN(1);

                    int comparison = country.compareTo(argumentCountry);

                    assertTrue(comparison > 0);
                }
                @DisplayName("and the argument country is null, method should return a error")
                @Test
                void aCountry_CompareTo_null() {
                    Country argumentCountry = null;

                    assertThrows(NullPointerException.class, () -> country.compareTo(argumentCountry));
                }
            }


        @DisplayName("and I use the method clone()")
        @Nested
        class Clone {
            @DisplayName("It returns the same country")
            @Test
            void cloneACountryReturnsSameCountry() {
                Country countryToBeCloned = country;

                Country clonedCountry = countryToBeCloned.clone();

                assertEquals(countryToBeCloned, clonedCountry);
            }
        }
    }
}