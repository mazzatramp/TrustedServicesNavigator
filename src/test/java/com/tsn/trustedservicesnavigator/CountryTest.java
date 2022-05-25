package com.tsn.trustedservicesnavigator;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CountryTest {
    //HELPER METHOD
    private Country getaCountry() throws IOException {
        TrustedList initialList = new TrustedList();
        initialList.downloadApiData();
        Country aCountry = initialList.getCountries().get(0);
        return aCountry;
    }
    //CLONE
    @Test
    void clone_aCountry_ReturnsSameCountry() throws IOException {
        //arrange
        Country countryToBeCloned = getaCountry();
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

    //----------------------------------------------------------------------------
    //EQUALS
    @Test
    void aCountry_equals_SameCountry_ReturnsTrue() throws IOException {
        //arrange
        Country country1 = getaCountry();
        Country country2 = getaCountry();
        //act
        boolean areCountriesTHeSame = country1.equals(country2);
        //assert
        assertTrue(areCountriesTHeSame);
    }
    /* HA SENSO FARLO?
    @Test
    void emptyList_Equals_EmptyList_ReturnsTrue() throws IOException {
        //arrange
        TrustedList list1 = new TrustedList();
        TrustedList list2 = new TrustedList();
        //act
        boolean haveTrustedListTheSameValues = list1.equals(list2);
        //assert
        assertTrue(haveTrustedListTheSameValues);
    }*/
    @Test
    void aCountry_Equals_NullCOuntry_ReturnsFalse() throws IOException {
        //arrange
        Country country1 = getaCountry();
        Country country2 = null;
        //act
        boolean areCountriesTHeSame = country1.equals(country2);
        //assert
        assertFalse(areCountriesTHeSame);
    }
    /*
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
    }*/
    //----------------------------------------------------------------------------
    //COMPARETO
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