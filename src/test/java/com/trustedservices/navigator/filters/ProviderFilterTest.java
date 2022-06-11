package com.trustedservices.navigator.filters;

import com.trustedservices.TestTrustedList;
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
    @DisplayName("is instantiated thanks to new ProviderFilter()")
    void testingConstructor1() {
        new ProviderFilter();
    }

    @BeforeEach
    @DisplayName("is instantiated thanks to new ProviderFilter()")
    void isInstantiatedWithNewProviderFilter() {
        providerFilter = new ProviderFilter();
    }

    @DisplayName("and I use the method ApplyTo")
// In this nested class filters are empty, I will not use setWhitelist
    @Nested
    class ApplyTo {
        TrustedList argumentTrustedList;

        @DisplayName("with a list as argument, should return the same list")
        @Test
        void withListAsArgument() {
            argumentTrustedList = TestTrustedList.getWholeLocalTrustedList();
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

            private Stream<Arguments> getProviders() {
                Set<String> providersSet1 = new HashSet<>();
                providersSet1.add("Austria/Datakom Austria GmbH");
                Set<String> providersSet2 = new HashSet<>();
                providersSet2.add("Austria/Datakom Austria GmbH");
                providersSet2.add("Italy/Azienda Zero");
                Set<String> providersSet3 = new HashSet<>();//the presence of one or more of made up filters like "noSenseFilter"
                // do not affect the behaviour of the filter if other meaningful filters are present
                providersSet3.add("Italy/Azienda Zero");
                providersSet3.add("NoSenseFilter");
                return Stream.of(
                        Arguments.of(providersSet1),
                        Arguments.of(providersSet2),
                        Arguments.of(providersSet3)
                );
            }

            @ParameterizedTest
            @MethodSource("getProviders")
            @DisplayName("with a list with compatible elements with the filters as argument, should return a not empty list with only those elements")
            void withListAsArgument(Set<String> providersSet) {
                setProviders(providersSet);
                argumentTrustedList = TestTrustedList.getWholeLocalTrustedList();
                Set<String> expectedProviders = new HashSet<>(providersSet);
                AtomicInteger numberOfServicesCompatibleWithFiltersInArgumentTrustedList = new AtomicInteger();
                AtomicInteger numberOfServiceInFilteredList = new AtomicInteger();
                //these last two variables are needed to check that the number of services in the argument list compatible with the filters
                //is equal to the number of services in the filtered list
                argumentTrustedList.getCountries().forEach(country -> {
                    country.getProviders().forEach(provider -> {
                        if ((expectedProviders.contains(country.getName() + "/" + provider.getName())) || (expectedProviders.isEmpty())) {
                            numberOfServicesCompatibleWithFiltersInArgumentTrustedList.getAndIncrement();
                        }
                    });
                });
                providerFilter.applyTo(argumentTrustedList);
                assertFalse(argumentTrustedList.isEmpty());

                argumentTrustedList.getCountries().forEach(country -> {
                    country.getProviders().forEach(provider -> {
                        numberOfServiceInFilteredList.getAndIncrement();
                        assertTrue(expectedProviders.contains(country.getName() + "/" + provider.getName()) || (expectedProviders.isEmpty()));
                    });
                });
                assertEquals(numberOfServicesCompatibleWithFiltersInArgumentTrustedList.get(), numberOfServiceInFilteredList.get());
//This assertion is done because if we would check only the other assertions we would not have really checked if the expected and
                //actual output are the same. The filtered list could have missed some services compatible with the filters from the argument list and
                //we would not have known. By counting the services we know.
            }

            @DisplayName("with a list with only incompatible elements with the filters as argument, should return a list with no elements")
            @ParameterizedTest
            @MethodSource("getProviders")
            void withNotPossibleListAsArgument(Set<String> providers) {
                setProviders(providers);
                argumentTrustedList = TestTrustedList.getTrustedListWith(
                        "countryWithOneProvider", "RealCountryWithOneRealProviderWitAllServices2");
                providerFilter.applyTo(argumentTrustedList);
                assertTrue(argumentTrustedList.getCountries().isEmpty());
            }

            @DisplayName("with a null list, should return NullPointerException unless filters are empty")
            @ParameterizedTest
            @MethodSource("getProviders")
            void withNullListAsArgumentAndNotEmptyFilters(Set<String> providers) {
                setProviders(providers);
                argumentTrustedList = null;

                assertThrows(NullPointerException.class, () -> providerFilter.applyTo(argumentTrustedList));

            }


        }

    }

    @DisplayName("after I set a whitelist of filters that cannot link to a service")
    @Nested
    class setImpossibleFilters {

        private void setProviders(Set<String> providers) {
            Set<String> providersSet = new HashSet<>(providers);
            providerFilter.setWhitelist(providersSet);
        }

        @DisplayName("and I use the method ApplyTo")
        @Nested
        class ApplyTo {
            TrustedList argumentTrustedList;

            private Stream<Arguments> getProviders() {
                //a list of filters with only made up elements link to no services
                Set<String> providersSet1 = new HashSet<>();
                providersSet1.add("noSenseFilter");
                Set<String> providersSet2 = new HashSet<>();
                providersSet2.add("noSenseFilter");
                providersSet2.add("anotherNoSenseFilter");
                return Stream.of(
                        Arguments.of(providersSet2),
                        Arguments.of(providersSet2)
                );
            }

            @ParameterizedTest
            @MethodSource("getProviders")
            @DisplayName("with a list, returns no element")
            void withListAsArgument(Set<String> providerSet) {
                setProviders(providerSet);
                argumentTrustedList = TestTrustedList.getWholeLocalTrustedList();
                providerFilter.applyTo(argumentTrustedList);
                assertTrue(argumentTrustedList.isEmpty());
            }

            @DisplayName("with a null list, throw NullPointer Exception")
            @ParameterizedTest
            @MethodSource("getProviders")
            void withNullListAsArgument(Set<String> providerSet) {
                setProviders(providerSet);
                argumentTrustedList = null;
                assertThrows(NullPointerException.class, () -> providerFilter.applyTo(argumentTrustedList));
            }

        }

    }

    @DisplayName("after I set a null whitelist")
    @Nested
    class setNullFilters {

        private void setServiceTypesInWhitelist(Set<String> providers) {
            providerFilter.setWhitelist(providers);
        }

        @DisplayName("and I use the method ApplyTo")
        @Nested
        class ApplyTo {
            TrustedList argumentTrustedList;

            @Test
            @DisplayName("with a list, throw NullPointerException")
            void withListAsArgument() {
                Set<String> providerSet = null;
                setServiceTypesInWhitelist(providerSet);
                argumentTrustedList = TestTrustedList.getWholeLocalTrustedList();
                assertThrows(NullPointerException.class, () -> providerFilter.applyTo(argumentTrustedList));

            }
        }

    }

}


