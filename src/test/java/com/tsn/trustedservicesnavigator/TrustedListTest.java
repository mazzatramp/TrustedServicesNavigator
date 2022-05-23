package com.tsn.trustedservicesnavigator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TrustedListTest {

    /*int c=5;
    @BeforeEach
    void setUp() {
      // TrustedList TrustedListT = new TrustedList();
        c++;
    }*/

    @Test
    void CountriesListIsEmptyBeforeAPIDownload()
    {
        //System.out.println(c);
        TrustedList TrustedListT = new TrustedList();
        boolean isCountriesLIstEmpty = TrustedListT.getCountries().isEmpty();
        assertTrue(isCountriesLIstEmpty);
    }
    @Test
    void CountriesListIsNotEmptyAfterAPIDownload() throws IOException
    {
        TrustedList TrustedListT = new TrustedList();
        TrustedListT.downloadApiData();
        boolean isCountriesLIstEmpty = TrustedListT.getCountries().isEmpty();
        assertFalse(isCountriesLIstEmpty);
    }
    @Test
    void ServiceTypesListIsEmptyBeforeAPIDownload()
    {
        TrustedList TrustedListT = new TrustedList();
        boolean isServiceTypesListEmpty = TrustedListT.getServiceTypes().isEmpty();
        assertTrue(isServiceTypesListEmpty);
    }
    @Test
    void ServiceTypesIsNotEmptyAfterAPIDownload() throws IOException
    {
        TrustedList TrustedListT = new TrustedList();
        TrustedListT.downloadApiData();
        boolean isServiceTypesListEmpty = TrustedListT.getServiceTypes().isEmpty();
        assertFalse(isServiceTypesListEmpty);
    }
    @Test
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
    }

    @Test
    void ProviderTypesIsNotEmptyAfterAPIDownload() throws IOException
    {
        TrustedList TrustedListT = new TrustedList();
        TrustedListT.downloadApiData();

        //la logica del prossimo blocco di codice è che se esistono liste di provider vuote dopo di
        // APIDownload() segnalo che il test è fallito
        TrustedListT.getCountries().forEach(country -> {
            if( country.getProviders().isEmpty()){
               fail("esistono liste di provider vuote dopo di APIDownload()");
            }
        });
        boolean isProviderListEmpty = false;
        assertFalse(isProviderListEmpty); // sarebbe meglio se esiste il contrario di fail() da mettere al posto di questa riga di codice
    }
}