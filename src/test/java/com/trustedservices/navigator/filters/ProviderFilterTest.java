package com.trustedservices.navigator.filters;

import com.trustedservices.DummyTrustedList;
import com.trustedservices.domain.Country;
import com.trustedservices.domain.Provider;
import com.trustedservices.domain.Service;
import com.trustedservices.domain.TrustedList;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("I create a ProviderFilter")
class ProviderFilterTest {
    ProviderFilter providerFilter;

    @BeforeEach
    @DisplayName("is instantiated thanks to new ProviderFilter()")
    void isInstantiatedWithNewProviderFilter() {
        providerFilter = new ProviderFilter();
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
                DummyTrustedList dummyTrustedList = DummyTrustedList.getInstance();
                argumentTrustedList = dummyTrustedList.getDummyTrustedList();
                Set<String> expectedProviders = new HashSet<>(providersSet);
                AtomicInteger numberOfServiceOfProviderInWhitelist = new AtomicInteger();//necessario per controllare che il numero di servizi compatibili con i filtri
                //nella lista iniziale sia uguale al numero di servizi della lista filtrata
                argumentTrustedList.getCountries().forEach(country -> {
                    country.getProviders().forEach(provider -> {
                        if ((expectedProviders.contains(country.getName() + "/" + provider.getName())) || (expectedProviders.isEmpty())) {
                            numberOfServiceOfProviderInWhitelist.getAndIncrement();
                        }
                    });
                });
                providerFilter.applyTo(argumentTrustedList);
                assertFalse(argumentTrustedList.isEmpty());

                argumentTrustedList.getCountries().forEach(country -> {
                    country.getProviders().forEach(provider -> {
                        numberOfServiceOfProviderInWhitelist.getAndDecrement();
                        assertTrue(expectedProviders.contains(country.getName() + "/" + provider.getName()) || (expectedProviders.isEmpty()));
                    });
                });
                assertEquals(numberOfServiceOfProviderInWhitelist.get(), 0);

            }

            @DisplayName("with a list with only incompatible elements with the filters as argument, should return a list with no elements")
            @ParameterizedTest
            @MethodSource("getProviders")
            void withNotPossibleListAsArgument(Set<String> providers) {
                setProviders(providers);
                argumentTrustedList = getTestTrustedList();
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

            private static Stream<Arguments> getProviders() {
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
                DummyTrustedList dummyTrustedList = DummyTrustedList.getInstance();
                argumentTrustedList = dummyTrustedList.getDummyTrustedList();
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
                DummyTrustedList dummyTrustedList = DummyTrustedList.getInstance();
                argumentTrustedList = dummyTrustedList.getDummyTrustedList();
                assertThrows(NullPointerException.class, () -> providerFilter.applyTo(argumentTrustedList));

            }
        }

    }

    private TrustedList getTestTrustedList() {
        TreeSet<Country> listOfCountries = new TreeSet<>();
        Country country1 = new Country("Austria", "AT");
        TreeSet<String> serviceTypesDatakom = new TreeSet<>();
        serviceTypesDatakom.add("QCertESig");
        TreeSet<String> serviceTypesA_sign_premium_CA = new TreeSet<>();
        serviceTypesA_sign_premium_CA.add("QCertESig");
        TreeSet<Service> servicesDatakom = new TreeSet<>();
        Provider Datakom_Austria_GmbH = new Provider(country1, 3, "Datakom Austria GmbH", "VATAT-U44837307", serviceTypesDatakom, servicesDatakom);
        Service A_sign_premium_CA = new Service(Datakom_Austria_GmbH, 1, "a-sign Premium CA", "http://uri.etsi.org/TrstSvc/Svctype/CA/QC", "withdrawn", serviceTypesA_sign_premium_CA);
        servicesDatakom.add(A_sign_premium_CA);
        country1.getProviders().add(Datakom_Austria_GmbH);

        Country country2 = new Country("Belgium", "Be");
        TreeSet<String> serviceTypesConnective = new TreeSet<>();
        serviceTypesConnective.add("QValQESig");
        serviceTypesConnective.add("QValQESeal");
        TreeSet<String> serviceTypesConnective_Validation_Service = new TreeSet<>();
        serviceTypesConnective_Validation_Service.add("QValQESig");
        serviceTypesConnective_Validation_Service.add("QValQESeal");
        TreeSet<Service> servicesConnective = new TreeSet<>();
        Provider Connective = new Provider(country2, 13, "CONNECTIVE", "VATBE-0467046486", serviceTypesConnective, servicesConnective);
        Service Connective_Validation_Service = new Service(Connective, 1, "Connective Validation Service", "http://uri.etsi.org/TrstSvc/Svctype/QESValidation/Q", "granted", serviceTypesConnective_Validation_Service);
        servicesConnective.add(Connective_Validation_Service);
        country2.getProviders().add(Connective);
        listOfCountries.add(country1);
        listOfCountries.add(country2);
        TrustedList testTrustedList = new TrustedList(listOfCountries);
        testTrustedList.updateServiceTypesAndStatuses();
        return testTrustedList;
    }
}


