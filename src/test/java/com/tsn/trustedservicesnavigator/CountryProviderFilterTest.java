package com.tsn.trustedservicesnavigator;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class CountryProviderFilterTest {

   /* NON SO COME FARLO PERCHE NON C'E' MODO DI CREARE UNA TRUSTEDLIST A PARTIRE DA UNA MAPPA E DELLE LISTE
   private TrustedList getATestWhitelist(){
        List<String> serviceTypes = new ArrayList<>();
        serviceTypes.add("QCertESig");
        serviceTypes.add("QCertESeal");

        Map<String, List<String>> mappaCountriesProviders = new LinkedHashMap<>();
        List<String> providers = new ArrayList<>();
        providers.add("JCC PAYMENT SYSTEMS LTD");
        mappaCountriesProviders.put("Cyprus",providers);
        return mappaCountriesProviders;
    }*/
    private TrustedList getWholeList() throws IOException {
        TrustedList initialList = new TrustedList();
        initialList.downloadApiData();
        return initialList;
    }
    @Test
    void applyTo_WholeListWithNoWhitelist_WholeListDoesNotChange() throws IOException {
        //arrange
       ProviderFilter cpf = new ProviderFilter();
       TrustedList listToFilter = getWholeList();
       List<Country> expectedListOfCountries = listToFilter.getCountries();

       //act
        cpf.applyTo(listToFilter);

        //assert
        //test che combacino i paesi e i providers
        AtomicInteger i= new AtomicInteger();
        AtomicInteger l= new AtomicInteger();
        listToFilter.getCountries().forEach(country -> {
            if (!( country.getName().equals(expectedListOfCountries.get(i.get()).getName())))
            {
                fail("I paesi non combaciano");
            }
            System.out.println("\n actual " +  i.get() + " "+ country.getName());
            System.out.println("\n expected "  +  i.get() + " " + expectedListOfCountries.get(i.get()).getName());
            l.getAndSet(0);
              country.getProviders().forEach(provider -> {
                if (!(provider.getName().equals
                        (expectedListOfCountries.get(i.get()).getProviders().get(l.get()).getName())))
                {
                    fail("i providers non combaciano")
                    ;}
                  System.out.println("\n actual " +  i.get() + " " + l.get() +" " + provider.getName());
                  System.out.println("\n expected "+  i.get() + " " + l.get() +" "  +
                          expectedListOfCountries.get(i.get()).getProviders().get(l.get()).getName());
                l.getAndIncrement();
            });
            i.getAndIncrement();
        });
    }

  /*  NON POSSO FARLO PERCHE' NON POSSO CREARE UNA TRUSTED LIST CON I VALORI CHE VOGLIO IO
  AL MASSIMO DOVREI CREARE DELLE MAPPE E DEI SET DISTINTI CHE HANNO AL LORO INTERNO COUNTRIES
  PROVIDERS SERVICE TYPES STATUSES
  @Test
    void applyTo_WholeListWithWhitelist_WholeListBecomesWhitelist() throws IOException {
        //arrange
        ProviderFilter cpf = new ProviderFilter();
        TrustedList listToFilter = getWholeList();
        List<Country> expectedListOfCountries = getATestWhitelist();

        //act

    }*/

}