package com.tsn.trustedservicesnavigator;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CountryTest {
    //HELPER METHOD
    private Country getCountryN(int indexOfTheCountry) throws IOException {
        //SAREBBE BELLO TESTARE CON TUTTE LE COUNTRIES. QUINDI IL GET DOVREBBE TORNARE
        //TUTTE LE COUNTRIES DA 0 A 28
        TrustedList initialList = Help.getWholeList();
        Country aCountry = initialList.getCountries().get(indexOfTheCountry);
        return aCountry;
    }

    //EQUALS
    @Nested
    class Equals{ @Test
        void aCountry_equals_SameCountry_ReturnsTrue() throws IOException {
        //arrange
        Country country1 = getCountryN(0);
        Country country2 = getCountryN(0);
        //act
        boolean areCountriesTHeSame = country1.equals(country2);
        //assert
        assertTrue(areCountriesTHeSame);
        }
        @Test
        void aCountry_equals_AnotherCountry_ReturnsFalse() throws IOException {
            //arrange
            Country country1 = getCountryN(0);
            Country country2 = getCountryN(1);
            //act
            boolean areCountriesTHeSame = country1.equals(country2);
            //assert
            assertFalse(areCountriesTHeSame);
        }
        @Test
        void emptyList_Equals_EmptyList_ReturnsTrue() throws IOException {
            //arrange
            TrustedList list1 = new TrustedList();
            TrustedList list2 = new TrustedList();
            //act
            boolean haveTrustedListTheSameValues = list1.equals(list2);
            //assert
            assertTrue(haveTrustedListTheSameValues);
        }
        @Test
        void aCountry_Equals_NullCountry_ReturnsFalse() throws IOException {
            //arrange
            Country country1 = getCountryN(0);
            Country country2 = null;
            //act
            boolean areCountriesTHeSame = country1.equals(country2);
            //assert
            assertFalse(areCountriesTHeSame);
        }
    /*
    //NULLequalsNULL devo provare anche
    COSA CONVIENE FARE IN QUESTO CASO?
    @Test
    void equals_NullListWithWholeList_ReturnsIDK() throws IOException {
        //arrange
        TrustedList list1 = null;
        TrustedList list2 = getWholeList();
        //act
        boolean haveTrustedListTheSameValues = list1.equals(list2);
        //assert
        assertFalse(haveTrustedListTheSameValues);
    }*/
    /*
    DOVREI FARLO CON STESSI VALORI MA OGGETTO DIVERSO, NON PENSO NEL CASO PRIMA FOSSE COSI
    @Test
    void equals_APartialListAndSamePartialList_ReturnsTrue() throws IOException {
        //arrange
        TrustedList list1 = getWholeList();
        TrustedList list2 = getWholeList();
        //act
        boolean haveTrustedListTheSameValues = list1.equals(list2);
        //assert
        assertTrue(haveTrustedListTheSameValues);
    }*/}
    //----------------------------------------------------------------------------
    //COMPARETO
    @Nested
    class CompareTo{
        //STESSA COUNTRY
        @Test
        void aCountry_CompareTo_SameCountry_ReturnsZero() throws IOException {
            //arrange
            Country country1 = getCountryN(0);
            Country country2 = getCountryN(0);
            //act
            int comparison = country1.compareTo(country2);
            int expected =0;
            //assert
            assertEquals(expected, comparison);
        }
        //COUNTRY MAGGIORE
        //DEVO METTERE ANCHE DIFFERENZA O BASTA POS E NEG
        @Test
        void aCountry_CompareTo_BiggerCountry_ReturnNegative() throws IOException {
            //arrange
            Country country1 = getCountryN(0);
            Country country2 = getCountryN(1);
            //act
            int comparison = country1.compareTo(country2);
            //assert
            assertTrue(comparison<0);

        }
        //COUNTRY MINORE
        //NULL A SX
        //NULL A DX
        //NULL NULLL
        //EMPTY A SX
        //EMPTY A DX
        //EMPTY EMPTY
    }
    //----------------------------------------------------------------------------
    //HASHCODE E TOSTRING?
    //CLONE
    @Nested
    class Clone{ @Test
    void clone_aCountry_ReturnsSameCountry() throws IOException {
        //arrange
        Country countryToBeCloned = getCountryN(0);
        //act
        Country clonedCountry= countryToBeCloned.clone();
        //assert
        assertEquals(countryToBeCloned,clonedCountry);
    }
        /* SE ABBIAMO UN COSTRUTTORE E PIU COMODO
        @Test
        void clone_EmptyCountry_ReturnsEmptyCountry() {
            //arrange
            TrustedList listToClone = new TrustedList();
            //act
            TrustedList clonedList = listToClone.clone();
            //assert
            //mettere un fail se non è vuota?
            assertEquals(listToClone,clonedList);
        }*/
        @Test
        void clone_NullCountry_returnsError(){ //SAREBBE DA METTERE UN ERRORE PIU SPECIFICO NEL
            //METODO CLONE SE VOGLIO CLONARE QUALCOSA DI NULL
            //arrange
            Country countryToClone =null;
            //assert
            assertThrows(NullPointerException.class,() ->  countryToClone.clone());
            //assertEquals(getWholeList(),clonedList)
        }
  /* POCO INTERESSANTE
    @Test
    void clone_WholeTrustedList_ReturnsWholeTrustedList() throws IOException {
        //arrange
        TrustedList listToClone = getWholeList();
        //act
        TrustedList clonedList = listToClone.clone();
        //assert
        assertEquals(listToClone,clonedList);
    }
*/
    }

    //----------------------------------------------------------------------------
   //TOSTRING
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

}