package com.trustedservices.navigator.filters;

import com.trustedservices.Help;
import com.trustedservices.domain.Provider;
import com.trustedservices.domain.TrustedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

//CAPIRE COME TESTARE CLASSI FIGLIE
@DisplayName("A provider filter")
class ProviderFilterTest {
    ProviderFilter providerFilter;
    @Nested
    @DisplayName("when null")
    class WhenNull {
        @BeforeEach
        void setTrustedListNull() {
            providerFilter = null;
        }
        @DisplayName("and I use the method ApplyTo")
        @Nested
        class ApplyTo{
            TrustedList argumentTrustedList;
            @Test
            void withListAsArgument() throws IOException {
                argumentTrustedList = Help.getWholeList();
                assertThrows(NullPointerException.class, () -> providerFilter.applyTo(argumentTrustedList));
            }
            @Test
            void withNullListAsArgument(){
                argumentTrustedList = null;
                assertThrows(NullPointerException.class, () -> providerFilter.applyTo(argumentTrustedList));
            }
        }

    }
    @Test
    @DisplayName("is instantiated with new ProviderFilter()")
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
        @DisplayName("and use the method ApplyTo")
        @Nested
        class ApplyTo{
            TrustedList argumentTrustedList;
            @Test
            void withListAsArgument() throws IOException {
                argumentTrustedList = Help.getWholeList();
                TrustedList expectedFilteredList = argumentTrustedList;
                providerFilter.applyTo(argumentTrustedList);
                assertEquals(expectedFilteredList,argumentTrustedList);

            }
            @Test
            void withNullListAsArgument(){
                argumentTrustedList = null;
                assertEquals(null ,argumentTrustedList);
                //assertThrows(NullPointerException.class, () -> providerFilter.applyTo(argumentTrustedList));
            }

        }
        @DisplayName("and I set a possible whitelist filters")
        @Nested
        class setPossibleFilters{
            @BeforeEach
            void setPossibleFilters() throws IOException {
                Set<String> setProvider = new HashSet<>();
                setProvider.add(Help.getWholeList().getCountries().get(0).getProviders().get(4).getName());
                providerFilter.setWhitelist(setProvider);
            }
            @DisplayName("and use the method ApplyTo")
            @Nested
            class ApplyTo{
                TrustedList argumentTrustedList;
                @Test
                void withListAsArgument() throws IOException {
                    argumentTrustedList = Help.getWholeList();
                    List<Provider> expectedProviders = new ArrayList<>();
                    expectedProviders.add(Help.getWholeList().getCountries().get(0).getProviders().get(4));
                    providerFilter.applyTo(argumentTrustedList);
                    argumentTrustedList.getCountries().forEach(country -> {
                        boolean areequal = expectedProviders.equals(country.getProviders());
                        assertTrue(areequal);
                        //VORREI ANCHE TESTARE CHE ABBIANO GLI STESSI SERVIZI MA POSSO FARLO ANCHE PIU ABVANTI
                    });


                }
                @Test
                void withNotPossibleListAsArgument() throws IOException {
                    argumentTrustedList = new TrustedList();
                    providerFilter.applyTo(argumentTrustedList);
                    argumentTrustedList.getCountries().forEach(country -> {
                       assertTrue(country.getProviders().isEmpty());
                    });
                }
                @Test
                void withNullListAsArgument(){
                    argumentTrustedList = null;
                    assertThrows(NullPointerException.class, () -> providerFilter.applyTo(argumentTrustedList));
                }

            }

        }
        /* IN TEORIA TUTTE LE COMBUNAZIONI DI FILTRI SONO POSSIBILI
        @DisplayName("and I set a not possible whitelist filters")
        @Nested
        class setNotPossibleFilters{
            @DisplayName("and I use the method ApplyTo")
            @Nested
            class ApplyTo{
                //ESSENDO PROTECTED NON LO POSSO PROVARE QUA
            }
        }
        */


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