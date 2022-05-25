package com.tsn.trustedservicesnavigator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


class TrustedListTest {
    //SAREBBE BELLO METTERE ANCHE DEI TIMEOUT

    //HELPER METHOD

    // DEVO FARE UN BEFOREEACH O QUALCOSA DEL GENERE CHE FACCIA IN MOD CHE NON DOVO FARE IL
    //DOWNLOAD DELLE API OGNI VOLTA
    private  TrustedList getWholeList() throws IOException {
        TrustedList initialList = new TrustedList();
        initialList.downloadApiData();
        return initialList;
    }

    //DOWNLOADAPIDATA()
    @Nested
    class APIDownload_Test{
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
        }
    /* NON HA PIU SENSO CON IL COSTRUTTORE IN TRUSTEDLIST
    @Test
    void CountriesListIsEmptyBeforeAPIDownload()
    {
        //System.out.println(c);
        TrustedList TrustedListT = new TrustedList();
        boolean isCountriesLIstEmpty = TrustedListT.getCountries().isEmpty();
        assertTrue(isCountriesLIstEmpty);
    }*/
    /* NON HA PIU SENSO CON IL COSTRUTTORE IN TRUSTED LIST
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

        //SE VOLESSI FARE UN TEST QUANDO NON HO CONNESIONE DEVO METTERE @ENABLEDIF
        //COMANDO ASSUMETRUE() CONTINA IL TEST SOLO SE L'ARGOMENTO E' VERO


    }
    //----------------------------------------------------------------------------
    //CLONE
    @Nested
    class clone_Test{


        //TEST CLONE CON ARGOMENTO IMPLICITO NON CLONABILE
        @Test
    void clone_WholeTrustedList_ReturnsWholeTrustedList() throws IOException {
        //arrange
        TrustedList listToClone = getWholeList();
        //act
        TrustedList clonedList = listToClone.clone();
        //assert
        assertEquals(listToClone,clonedList);
    }

        @Test
        void clone_EmptyTrustedList_ReturnsEmptyTrustedList() {
            //arrange
            TrustedList listToClone = new TrustedList();
            //act
            TrustedList clonedList = listToClone.clone();
            //assert
            //mettere un fail se non è vuota?
            assertEquals(listToClone,clonedList);
        }
        @Test
        void clone_Null_ReturnsError(){ //SAREBBE DA METTERE UN ERRORE PIU SPECIFICO NEL
            //METODO CLONE SE VOGLIO CLONARE QUALCOSA DI NULL
            //arrange
            TrustedList listToClone =null;
            //assert
            assertThrows(NullPointerException.class,() ->  listToClone.clone());
            //assertEquals(getWholeList(),clonedList)
        }
    }

    //----------------------------------------------------------------------------
    //EQUALS
    @Nested
    class equals_Test{ @Test
    void wholeList_equals_WholeList_ReturnsTrue() throws IOException {
        //arrange
        TrustedList list1 = getWholeList();
        TrustedList list2 = getWholeList();
        //act
        boolean haveTrustedListTheSameValues = list1.equals(list2);
        //assert
        assertTrue(haveTrustedListTheSameValues);
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
        void wholeList_Equals_NullList_ReturnsFalse() throws IOException {
            //arrange
            TrustedList list1 = getWholeList();
            TrustedList list2 = null;
            //act
            boolean haveTrustedListTheSameValues = list1.equals(list2);
            //assert
            assertFalse(haveTrustedListTheSameValues);
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
    }*/}

}