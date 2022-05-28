package com.trustedservicesnavigator.frontend.filters;

import com.trustedservicesnavigator.frontend.filters.ProviderFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

//CAPIRE COME TESTARE CLASSI FIGLIE
@DisplayName("A provider filter")
class ProviderFilterTest {
    ProviderFilter providerFilter;
    @Test
    @DisplayName("is instantiated with new Filter()")
    void isInstantiatedWithNewProviderFilter() {
        new ProviderFilter();
    }
    @Nested
    @DisplayName("when new")
    class WhenNew
    {
        @BeforeEach
        void createAFilterController(){
            providerFilter = new ProviderFilter();
        }

        @DisplayName("and I use the method FilterByWhiteList")
        @Nested
        class FilterByWhiteList{
            //ESSENDO PROTECTED NON LO POSSO PROVARE QUA
        }
    }
    /*
    //METODI FATTI PRIMA CHE COUNTRYPROVIDERFILTER DIVENTASSE PROVIDERFILTER

    @Test
    void applyTo_WholeListWithNoWhitelist_WholeListDoesNotChange() throws IOException {
        //arrange
       ProviderFilter cpf = new ProviderFilter();
       TrustedList listToFilter = Help.getWholeList();
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