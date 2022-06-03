package com.trustedservices.domain;

import com.trustedservices.Help;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A TrustedList")
class TrustedListTest {
    TrustedList trustedList;
    @Nested
    @DisplayName("when null")
    class WhenNull {
        @BeforeEach
        void setTrustedListNull() {
            trustedList=null;
        }
        @DisplayName("and I use the method clone it should throw an error")
        @Test
        void cloneNullTrustedList()
        {
            TrustedList listToClone =trustedList;
            //assert
            assertThrows(NullPointerException.class,() ->  listToClone.clone());
        }
        @DisplayName("and I use the method ...")
        @Nested
        class nomeMetodo2{}
    }
    @Test
    @DisplayName("is instantiated with new TrustedList()")
    void isInstantiatedWithNewTrustedList() {
        new TrustedList();
    }
    @Test
    @DisplayName("is instantiated with new TrustedList(List<Country>)")
    void isInstantiatedWithNewTrustedList_ListOfCountriesAsArgument() {
        new TrustedList(new TreeSet<>());
    }
    @Nested
    @DisplayName("when new")
    class WhenNew{
        @BeforeEach
        void createACountry() {
            trustedList = new TrustedList();
        }
        @DisplayName("and I use the method downloadAPIDATA")
        @Nested
        class downloadApiData{
             /* SIAMO SICURI CHE SERVA DOWNLOADAPIDATA IN TRUSTEDLIST?
        @Test
        //cosa metto come expectedValue perchè non ha senso controllare che non siano vuote, dovrei avere una copia della lista intera e confrontarla credo
        void APIDownload_NoInputs_FillTrustedList() throws IOException
        {
            //arrange
            TrustedList TrustedListT = new TrustedList();
            //act
            TrustedListT.downloadApiData();
            //assert
            boolean isCountriesLIstEmpty = TrustedListT.getCountries().isEmpty();
            assertFalse(isCountriesLIstEmpty);
        }
        //da fare anche per providers e il resto
        //oppure faccio un metodo isempty in trustedlist

        @Test
        void APIDownload_NoInputs_FillServiceTypes() throws IOException
        {
            //arrange
            TrustedList TrustedListT = new TrustedList();
            //act
            TrustedListT.downloadApiData();
            //assert
            boolean isServiceTypesListEmpty = TrustedListT.getServiceTypes().isEmpty();
            assertFalse(isServiceTypesListEmpty);
        }

        @Test
        void APIDownload_NoInputs_FillProviders() throws IOException
        {
            //arrange
            TrustedList TrustedListT = new TrustedList();
            //act
            TrustedListT.downloadApiData();
            //la logica del prossimo blocco di codice è che se esistono liste di provider vuote dopo di
            // APIDownload() segnalo che il test è fallito
            TrustedListT.getCountries().forEach(country -> {
                if( country.getProviders().isEmpty()){
                    fail("esistono liste di provider vuote dopo di APIDownload()");
                }
            });
        /*boolean isProviderListEmpty = false;
        assertFalse(isProviderListEmpty); // sarebbe meglio se esiste il contrario di fail() da mettere al posto di questa riga di codice
    */
             /*
    @Test
    void CountriesListIsEmptyBeforeAPIDownload()
    {
        //System.out.println(c);
        TrustedList TrustedListT = new TrustedList();
        boolean isCountriesLIstEmpty = TrustedListT.getCountries().isEmpty();
        assertTrue(isCountriesLIstEmpty);
    }*/
             /*
    @Test
    void ServiceTypesListIsEmptyBeforeAPIDownload()
    {
        TrustedList TrustedListT = new TrustedList();
        boolean isServiceTypesListEmpty = TrustedListT.getServiceTypes().isEmpty();
        assertTrue(isServiceTypesListEmpty);
    }*/
             /*@Test
    void ProviderListIsEmptyBeforeAPIDownload()
    {
        TrustedList TrustedListT = new TrustedList();

        //la logica del prossimo blocco di codice è che se esistono liste di provider non vuote prima di
        // APIDownload() segnalo che il test è fallito
        TrustedListT.getCountries().forEach(country -> {
           if( !country.getProviders().isEmpty()){
               fail("una lista di provider non era vuota prima di APIDownload()");
           }
            });
        boolean isProviderListEmpty = true;
        assertTrue(isProviderListEmpty); // sarebbe meglio se esiste il contrario di fail() da mettere al posto di questa riga di codice
    }*/
        }
        @DisplayName("and I use the method fillServiceTypesAndStatuses")
        @Nested
        class fillServiceTypesAndStatuses{}
        @DisplayName("and I use the method clone")
        @Nested
        class clone_Test{
            @DisplayName("It returns the same TrustedList")
            @Test
            void cloneATrustedListEmpty(){
                //arrange
                TrustedList listToClone = trustedList;
                //act
                TrustedList clonedList = listToClone.clone();
                //assert
                assertEquals(listToClone,clonedList);
            }


        }
        @DisplayName("and I use the method equals")
        @Nested
        class Equals{
            @DisplayName("with a whole list as argument, it returns false")
            @Test
            void equalsWithWholeListAsArgument() throws IOException {
                System.out.println(trustedList.getCountries());
                TrustedList list1 = trustedList;
                TrustedList list2 = Help.getWholeList();
                System.out.println("lista 1 " + list1.getCountries());
                System.out.println("lista 2 "+ list2.getCountries());
                //act
                boolean haveTrustedListTheSameValues = list1.equals(list2);
                //assert
                assertFalse(haveTrustedListTheSameValues);
            }
            @DisplayName("with a empty list as argument, it returns true")
            @Test
            void equalsWithEmptyListAsArgument(){
                TrustedList list1 = trustedList;
                TrustedList list2 = new TrustedList();
                //act
                boolean haveTrustedListTheSameValues = list1.equals(list2);
                //assert
                assertTrue(haveTrustedListTheSameValues);
            }

        }
        @DisplayName("and I fill it")
        @Nested
        class fillTrustedList{
            @BeforeEach
            void fillTList() throws IOException {
                trustedList = Help.getWholeList();
            }

            @DisplayName("and I use the method clone, it returns the same TrustedList")
            @Test
            void cloneAFullTrustedList() throws IOException {
                    //arrange
                    TrustedList listToClone = trustedList ;
                    //act
                    TrustedList clonedList = listToClone.clone();
                    //assert
                    //mettere un fail se non è vuota?
                    assertEquals(listToClone,clonedList);
            }
            @DisplayName("and I use the method equals()")
            @Nested
            class Equals{
                @DisplayName("with a whole list as argument, it returns true")
                @Test
                void equalsWithWholeListAsArgument() throws IOException {
                    TrustedList list1 = trustedList;
                    TrustedList list2 = Help.getWholeList();
                    //act
                    boolean haveTrustedListTheSameValues = list1.equals(list2);
                    //assert
                    assertTrue(haveTrustedListTheSameValues);
                }
                //HO CORRETTO UN BUG SU EQUALS DOVE NEL RETURN AL POSTO DI AND VI ERANO OR. IL PROBLEMA E' CHE SE RUNNAVO IL TEST SINGOLO ME LO DAVA OK
                //SE RUNNAVO TUTTI I TEST MI DAVA ERRORE. QUESTO POTREBBE VOLERE DIRE CHE C'E' UN ERRORE LOGICO NEL TEST OPPURE NO BISOGNA CAPIRLO
                @DisplayName("with a null list as argument, it returns false")
                @Test
                void equalsWithNullListAsArgument() throws IOException {
                    TrustedList list1 = trustedList;
                    TrustedList list2 = null;
                    //act
                    boolean haveTrustedListTheSameValues = list1.equals(list2);
                    //assert
                    assertFalse(haveTrustedListTheSameValues);
                }
            }
        }

    }



        //SE VOLESSI FARE UN TEST QUANDO NON HO CONNESIONE DEVO METTERE @ENABLEDIF



}

//GET E SET
 //----------------------------------------------------------------------------
    //EQUALS

/*

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
    */

