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

    @Nested
    @DisplayName("when null")
    class WhenNull {
        @BeforeEach
        void setCountryNull() {
            country = null;
        }

        @Nested
        @DisplayName("and I use the method clone()")
        class Clone {
            @DisplayName("returns an Error")
            @Test
            void clone_NullCountry_returnsError() {
                assertThrows(NullPointerException.class, () -> country.clone());
            }
        }

        @DisplayName("and I use the method equals(Object)")
        @Nested
        class Equals{
            Country argumentCountry;
            @DisplayName("with a country as argument, it returns an Error")
            @Test
            void clone_NullCountry_returnsError() {
                argumentCountry = new Country("Austria", "AT");
                //SAREBBE DA METTERE UN ERRORE PIU SPECIFICO NEL
                //arrange
                //assert
                assertThrows(NullPointerException.class, () -> country.equals(argumentCountry));
                //assertEquals(getWholeList(),clonedList)
            }
            @DisplayName("with a null country as argument, it returns error ")
            @Test
            void prova() {
                argumentCountry = null;
                //SAREBBE DA METTERE UN ERRORE PIU SPECIFICO NEL
                //arrange
                //assert
                assertThrows(NullPointerException.class, () -> country.equals(argumentCountry));
                //assertEquals(getWholeList(),clonedList)
            }


        }
        @Nested
        class CompareTo{
           Country countryAsArgument;
           @DisplayName("with a country returns an Error")
           @Test
           void clone_NullCountry_returnsError() {
               countryAsArgument = new Country("Austria", "AT");
               assertThrows(NullPointerException.class, () -> country.compareTo(countryAsArgument));
           }
           @DisplayName("with null returns error ")
           @Test
           void prova() {
               countryAsArgument = null;
               assertThrows(NullPointerException.class, () -> country.compareTo(countryAsArgument));
           }
        }
    }

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
            country = new Country("Austria", "AT");
        }

        @DisplayName("and I use the method equals(Object)")
        @Nested
        class Equals {
            @DisplayName("with a country as argument")
            @Nested
            class CountryAsArgument {
                Country argumentCountry;

                @DisplayName("and the two countries are the same, method should return true")
                @Test
                void SameCountryAsArgument() {
                    //arrange
                    argumentCountry = country;
                    //act
                    boolean areCountriesTHeSame = country.equals(argumentCountry);
                    //assert
                    assertTrue(areCountriesTHeSame);
                }

                @DisplayName("and the two countries are not the same, method should return false")
                @Test
                void NotSameCountryAsArgument() {
                    //arrange
                     argumentCountry = new Country("Italia", "IT");
                    //act
                    boolean areCountriesTHeSame = country.equals(argumentCountry);
                    //assert
                    assertFalse(areCountriesTHeSame);
                }
                @DisplayName("with a country null as argument, method should return false")
                @Test
                void NullAsArgument() {
                    //arrange
                     argumentCountry = null;
                    //act
                    boolean areCountriesTHeSame = country.equals(argumentCountry);
                    //assert
                    assertFalse(areCountriesTHeSame);
                }
            }

            /*
            DOVREI FARLO CON STESSI VALORI MA OGGETTO DIVERSO, NON PENSO NEL CASO PRIMA FOSSE COSI
            */
        }

        @DisplayName("and I use the method compareTo(Country)")
        @Nested
        class CompareTo {
            @DisplayName("with a country as argument")
            @Nested
            class CountryAsArgument {
                Country argumentCountry;

                @DisplayName("and the two countries are the same, method should return 0")
                @Test
                void SameCountryAsArgument() throws IOException {
                    //arrange
                    argumentCountry = country;
                    //act
                    int comparison = country.compareTo(argumentCountry);
                    int expectedreturn = 0;
                    //assert
                    assertEquals(expectedreturn, comparison);
                }

                //COUNTRY MAGGIORE
                //DEVO METTERE ANCHE DIFFERENZA O BASTA POS E NEG?
                @DisplayName("and the argument country is greater, method should return a negative number")
                @Test
                void aCountry_CompareTo_BiggerCountry_ReturnNegative() throws IOException {
                    //arrange
                    argumentCountry = Help.getCountryN(1);
                    //act
                    int comparison = country.compareTo(argumentCountry);
                    //assert
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