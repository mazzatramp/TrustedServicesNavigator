package com.tsn.trustedservicesnavigator;

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

        @DisplayName("and I use the method clone()")
        @Nested
        class Clone {
            @DisplayName("returns an Error")
            @Test
            void clone_NullCountry_returnsError() {
                //SAREBBE DA METTERE UN ERRORE PIU SPECIFICO NEL
                //METODO CLONE SE VOGLIO CLONARE QUALCOSA DI NULL
                //arrange
                Country countryToClone = country;
                //assert
                assertThrows(NullPointerException.class, () -> countryToClone.clone());
                //assertEquals(getWholeList(),clonedList)
            }
        }

       // @DisplayName("and I use the method equals(Object)")
        @Nested
        class Equals{

        }
       // @DisplayName("and I use the method compareTo(Country)")
        @Nested
        class CompareTo{

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
        //POTREMMO TOGLIERE POSSIBIITA DI AVERE COUNTRIES VUOTE
        @BeforeEach
        void createACountry() {

            country = new Country("Austria", "AT");
        }

        @DisplayName("and I use the method equals(Object)")
        @Nested
        class Equals {
            //CON QUESTO METODO DUE COUNTRIES CON LO STESSO NOME MA PROVIDERS DIVERSI SONO UGUALI
            @DisplayName("with a country as argument")
            @Nested
            class CountryAsArgument {
                Country argumentCountry;

                @DisplayName("and the two countries are the same, method should return true")
                @Test
                void SameCountryAsArgument() {
                    //arrange
                    Country argumentCountry = country;
                    //act
                    boolean areCountriesTHeSame = country.equals(argumentCountry);
                    //assert
                    assertTrue(areCountriesTHeSame);
                }

                @DisplayName("and the two countries are not the same, method should return false")
                @Test
                void NotSameCountryAsArgument() {
                    //arrange
                    Country argumentCountry = new Country("Italia", "IT");
                    //act
                    boolean areCountriesTHeSame = country.equals(argumentCountry);
                    //assert
                    assertFalse(areCountriesTHeSame);
                }
            }

            @DisplayName("with a country null as argument, method should return false")
            @Test
            void NullAsArgument() {
                //arrange
                Country argumentCountry = null;
                //act
                boolean areCountriesTHeSame = country.equals(argumentCountry);
                //assert
                assertFalse(areCountriesTHeSame);
            }
            /*
            //NULLequalsNULL devo provare anche
            //NUllequalsWHolelist

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
                    Country argumentCountry = country;
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
                    Country argumentCountry = Help.getCountryN(1);
                    //act
                    int comparison = country.compareTo(argumentCountry);
                    //assert
                    assertTrue(comparison < 0);

                }
            }

            //COUNTRY MINORE
            //NULL A SX
            //NULL A DX
            //NULL NULLL
            //EMPTY A SX
            //EMPTY A DX
            //EMPTY EMPTY
        }

        @DisplayName("and I use the method clone()")
        @Nested
        class Clone {
            @DisplayName("It returns the same country")
            @Test
            void cloneACountryReturnsSameCountry() throws IOException {
                //arrange
                Country countryToBeCloned = country;
                //act
                Country clonedCountry = countryToBeCloned.clone();
                //assert
                assertEquals(countryToBeCloned, clonedCountry);
            }
            /* SE ABBIAMO UN COSTRUTTORE E' PIU' COMODO
            @Test
            void clone_EmptyCountry_ReturnsEmptyCountry() {
                }*/

        }
    }
}
    //----------------------------------------------------------------------------
    //HASHCODE E TOSTRING?
    //----------------------------------------------------------------------------
   //TOSTRING
    //I GET E SET
    /*
    @Test
    void getName_NoInputs_ReturnsName()
{
    //arrange
    Country ITA = new Country("Italy","1");

    //act
    String actualName = ITA.getName();

    //assert
    assertEquals("Italy",actualName);
}

}*/