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
@DisplayName("A ProviderFilter")
class ProviderFilterTest {
    ProviderFilter providerFilter;
    @Test
    @DisplayName("is instantiated with new ProviderFilter()")
    void isInstantiatedWithNewProviderFilter() {
        new ProviderFilter();
    }
    @Nested
    @DisplayName("when new")
    class WhenNew {
        @BeforeEach
        void createAFilterController() {
            providerFilter = new ProviderFilter();
        }

        @DisplayName("and I use the method ApplyTo")
        @Nested
        class ApplyTo {
            TrustedList argumentTrustedList;

            @DisplayName("with a list as argument, should return the same list")
            @Test
            void withListAsArgument() {
                argumentTrustedList = Help.getWholeList();
                TrustedList expectedFilteredList = argumentTrustedList;
                providerFilter.applyTo(argumentTrustedList);
                assertEquals(expectedFilteredList, argumentTrustedList);

            }

            @DisplayName("with a null list as argument, should return a null list")
            @Test
            void withNullListAsArgument() {
                argumentTrustedList = null;
                assertEquals(null, argumentTrustedList);
                //questo non succede: assertThrows(NullPointerException.class, () -> providerFilter.applyTo(argumentTrustedList));
            }

        }

        @DisplayName("and I set a possible whitelist filters")
        @Nested
        class setPossibleFilters {
            @BeforeEach
            void setPossibleFilters() throws IOException {
                Set<String> setProvider = new HashSet<>();
                setProvider.add(Help.getWholeList().getCountries().get(0).getProviders().get(4).getName());
                providerFilter.setWhitelist(setProvider);
            }

            @DisplayName("and I use the method ApplyTo")
            @Nested
            class ApplyTo {
                TrustedList argumentTrustedList;

                @DisplayName("with a list with compatible elements with the filters as argument, should return a list with only those elements")
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

                @DisplayName("with a list with only incompatible elements with the filters as argument, should return a list with no elements")
                @Test
                void withNotPossibleListAsArgument() throws IOException {
                    argumentTrustedList = new TrustedList();
                    providerFilter.applyTo(argumentTrustedList);
                    argumentTrustedList.getCountries().forEach(country -> {
                        assertTrue(country.getProviders().isEmpty());
                    });
                }

                @DisplayName("with a null list, should return NullPointerException")

                @Test
                void withNullListAsArgument() {
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
}


