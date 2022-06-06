package com.trustedservices.navigator.filters;

import com.trustedservices.DummyTrustedList;
import com.trustedservices.domain.TrustedList;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("I create a ProviderFilter")
class ProviderFilterTest {
    ProviderFilter providerFilter;

    @Test
    @BeforeEach
    @DisplayName("is instantiated thanks to new ProviderFilter()")
    void isInstantiatedWithNewProviderFilter() {
        providerFilter= new ProviderFilter();
    }

    @DisplayName("and I use the method ApplyTo")
    @Nested
    class ApplyTo {
        TrustedList argumentTrustedList;

        @DisplayName("with a list as argument, should return the same list")
        @Test
        void withListAsArgument() {
            DummyTrustedList dummyTrustedList = DummyTrustedList.getInstance();
            argumentTrustedList = dummyTrustedList.getDummyTrustedList();
            TrustedList expectedFilteredList = argumentTrustedList;
            providerFilter.applyTo(argumentTrustedList);
            assertEquals(expectedFilteredList, argumentTrustedList);

        }

        @DisplayName("with a null list as argument, should return a null list")
        @Test
        void withNullListAsArgument() {
            argumentTrustedList = null;
            assertEquals(null, argumentTrustedList);
        }

    }

    @DisplayName("and I set a possible whitelist filters")
    @Nested
    class setPossibleFilters {

        private void setProviders(Set<String> providers) {
            providerFilter.setWhitelist(providers);
        }

        @DisplayName("and I use the method ApplyTo")
        @Nested
        class ApplyTo {
            TrustedList argumentTrustedList;
            private static Stream<Arguments> getProviders() {
                Set<String> providersSet1 = new HashSet<>();
                providersSet1.add("Austria/Datakom Austria GmbH");
                Set<String> providersSet2 = new HashSet<>();
                providersSet2.add("Austria/Datakom Austria GmbH");
                providersSet2.add("Italy/Azienda Zero");
                Set<String> providersSet3 = new HashSet<>();
                providersSet3.add("Italy/Azienda Zero");
                return Stream.of(
                        Arguments.of(providersSet1),
                        Arguments.of(providersSet2),
                        Arguments.of(providersSet3)
                );
            }
            @ParameterizedTest
            @MethodSource("getProviders")
            @DisplayName("with a list with compatible elements with the filters as argument, should return a list with only those elements")
            void withListAsArgument(Set<String> providersSet)  {
                setProviders(providersSet);
                DummyTrustedList dummyTrustedList = DummyTrustedList.getInstance();
                argumentTrustedList = dummyTrustedList.getDummyTrustedList();
                Set<String> expectedProviders = new HashSet<>(providersSet);
                AtomicInteger numberOfServiceOfProviderInWhitelist= new AtomicInteger();
                argumentTrustedList.getCountries().forEach(country -> {
                    country.getProviders().forEach(provider -> {
                        if(providersSet.contains(country.getName() + "/" + provider.getName() )){
                            numberOfServiceOfProviderInWhitelist.getAndIncrement();
                        }
                    });
                });
                providerFilter.applyTo(argumentTrustedList);
                //System.out.println(" countries"  + argumentTrustedList.getCountries());
                argumentTrustedList.getCountries().forEach(country -> {
                    country.getProviders().forEach(provider -> {
                        numberOfServiceOfProviderInWhitelist.getAndDecrement();

                        //System.out.println(provider.getName());
                        assertTrue(providersSet.contains(country.getName() + "/" + provider.getName() ));
                        });
                    //VORREI ANCHE TESTARE CHE ABBIANO GLI STESSI SERVIZI MA POSSO FARLO ANCHE PIU ABVANTI
                });
                assertEquals(numberOfServiceOfProviderInWhitelist.get(),0);

            }
            @ParameterizedTest
            @MethodSource("getProviders")
            @DisplayName("with a list with only incompatible elements with the filters as argument, should return a list with no elements")
            void withNotPossibleListAsArgument(Set<String> providersSet)  {
                setProviders(providersSet);
                argumentTrustedList = new TrustedList();
                providerFilter.applyTo(argumentTrustedList);
                argumentTrustedList.getCountries().forEach(country -> {
                    assertTrue(country.getProviders().isEmpty());
                });
            }
            @ParameterizedTest
            @MethodSource("getProviders")
            @DisplayName("with a null list, should return NullPointerException")

            void withNullListAsArgument(Set<String> providersSet) {
                setProviders(providersSet);
                argumentTrustedList = null;
                assertThrows(NullPointerException.class, () -> providerFilter.applyTo(argumentTrustedList));
            }

        }

    }

//Se metti un filtro nosense  + uno sensato non considera il non sensato
//cosa succede se metti solo filtri nosense
}


