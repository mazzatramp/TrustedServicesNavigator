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

@DisplayName("I create a ServiceTypeFilter")
class ServiceTypeFilterTest {
    ServiceTypeFilter serviceTypeFilter;

    @BeforeEach
    @DisplayName("is instantiated thanks to new ServiceTypeFilter()")
    void isInstantiatedWithNewServiceTypeFilter() {
        serviceTypeFilter = new ServiceTypeFilter();
    }


    @BeforeEach
    void createAFilterController() {
        serviceTypeFilter = new ServiceTypeFilter();
    }

    @DisplayName("when I use the method ApplyTo")
    @Nested
    class ApplyTo {
        TrustedList argumentTrustedList;

        @DisplayName("with a list as argument, should return the same list")
        @Test
        void withListAsArgument() {
            DummyTrustedList dummyTrustedList = DummyTrustedList.getInstance();
            argumentTrustedList = dummyTrustedList.getDummyTrustedList();
            TrustedList expectedFilteredList = argumentTrustedList;
            serviceTypeFilter.applyTo(argumentTrustedList);
            assertEquals(expectedFilteredList, argumentTrustedList);
        }

        @DisplayName("with a null list as argument, should return a null list")
        @Test
        void withNullListAsArgument() {
            argumentTrustedList = null;
            assertEquals(null, argumentTrustedList);
        }

    }

    @DisplayName("after I set a whitelist of filters that can link to a service")
    @Nested
    class setPossibleFilters {

        private void setServiceTypes(Set<String> serviceTypes) {
            Set<String> serviceTypeSet = new HashSet<>(serviceTypes);
            serviceTypeFilter.setWhitelist(serviceTypeSet);
        }

        @DisplayName("and I use the method ApplyTo")
        @Nested
        class ApplyTo {
            TrustedList argumentTrustedList;

            private static Stream<Arguments> getServiceTypes() {
                Set<String> serviceTypesSet1 = new HashSet<>();
                serviceTypesSet1.add("QCertESeal");
                Set<String> serviceTypesSet2 = new HashSet<>();
                serviceTypesSet2.add("QWAC");
                Set<String> serviceTypesSet3 = new HashSet<>();
                serviceTypesSet3.add("Timestamp");
                Set<String> serviceTypesSet4 = new HashSet<>();
                serviceTypesSet4.add("QCertESeal");
                serviceTypesSet4.add("QValQESig");
                serviceTypesSet4.add("QeRDS");
                serviceTypesSet4.add("QPresQESeal");
                serviceTypesSet4.add("Timestamp");
                serviceTypesSet4.add("NonRegulatory");
                serviceTypesSet4.add("QWAC");
                serviceTypesSet4.add("QPresQESig");
                serviceTypesSet4.add("CertESeal");
                serviceTypesSet4.add("QCertESig");
                serviceTypesSet4.add("WAC");
                serviceTypesSet4.add("QValQESeal");
                serviceTypesSet4.add("QTimestamp");
                serviceTypesSet4.add("CertUndefined");
                serviceTypesSet4.add("GenESig");
                serviceTypesSet4.add("CertESig");
                Set<String> serviceTypesSet5 = new HashSet<>();
                serviceTypesSet5.add("CertESig");
                serviceTypesSet5.add("noSenseFilter");
                return Stream.of(
                        Arguments.of(serviceTypesSet1),
                        Arguments.of(serviceTypesSet2),
                        Arguments.of(serviceTypesSet3),
                        Arguments.of(serviceTypesSet4),
                        Arguments.of(serviceTypesSet5)
                );
            }

            @ParameterizedTest
            @MethodSource("getServiceTypes")
            @DisplayName("with a list with compatible elements with the filters as argument, should return a not empty list with only those elements")
            void withListAsArgument(Set<String> serviceTypes) {
                setServiceTypes(serviceTypes);
                DummyTrustedList dummyTrustedList = DummyTrustedList.getInstance();
                argumentTrustedList = dummyTrustedList.getDummyTrustedList();
                Set<String> expectedServiceType = serviceTypes;
                AtomicInteger numberOfServiceWithServiceTypeInWhitelist = new AtomicInteger();//necessario per controllare che il numero di servizi compatibili con i filtri
                //nella lista iniziale sia uguale al numero di servizi della lista filtrata
                argumentTrustedList.getCountries().forEach(country -> {
                    country.getProviders().forEach(provider -> {
                        provider.getServices().forEach(service -> {
                            if ((service.getServiceTypes().stream().anyMatch(currentService -> expectedServiceType.contains(currentService))) || (expectedServiceType.isEmpty())) {
                                numberOfServiceWithServiceTypeInWhitelist.getAndIncrement();
                            }
                        });
                    });
                });
                serviceTypeFilter.applyTo(argumentTrustedList);
                assertFalse(argumentTrustedList.isEmpty());

                argumentTrustedList.getCountries().forEach(country -> {
                    country.getProviders().forEach(provider -> {
                        provider.getServices().forEach(service -> {
                            numberOfServiceWithServiceTypeInWhitelist.getAndDecrement();
                            assertTrue((service.getServiceTypes().stream().anyMatch(currentService -> expectedServiceType.contains(currentService)) || (expectedServiceType.isEmpty())));

                        });
                    });
                });
                assertEquals(numberOfServiceWithServiceTypeInWhitelist.get(), 0);


            }

            @DisplayName("with a list with only incompatible elements with the filters as argument, should return a list with no elements")
            @ParameterizedTest
            @MethodSource("getServiceTypes")
            void withNotPossibleListAsArgument(Set<String> serviceTypes) {
                setServiceTypes(serviceTypes);
                argumentTrustedList = getTestTrustedList();
                serviceTypeFilter.applyTo(argumentTrustedList);
                assertTrue(argumentTrustedList.getCountries().isEmpty());
            }

            @DisplayName("with a null list, should return NullPointerException unless filters are empty")
            @ParameterizedTest
            @MethodSource("getServiceTypes")
            void withNullListAsArgumentAndNotEmptyFilters(Set<String> serviceTypes) {
                setServiceTypes(serviceTypes);
                argumentTrustedList = null;

                assertThrows(NullPointerException.class, () -> serviceTypeFilter.applyTo(argumentTrustedList));

            }


        }
    }

    @DisplayName("after I set a whitelist of filters that cannot link to a service")
    @Nested
    class setImpossibleFilters {

        private void setServiceTypes(Set<String> serviceTypes) {
            Set<String> serviceTypeSet = new HashSet<>(serviceTypes);
            serviceTypeFilter.setWhitelist(serviceTypeSet);
        }

        @DisplayName("and I use the method ApplyTo")
        @Nested
        class ApplyTo {
            TrustedList argumentTrustedList;

            private static Stream<Arguments> getServiceTypes() {
                Set<String> serviceTypeSet1 = new HashSet<>();
                serviceTypeSet1.add("noSenseFilter");
                Set<String> serviceTypeSet2 = new HashSet<>();
                serviceTypeSet2.add("noSenseFilter");
                serviceTypeSet2.add("anotherNoSenseFilter");
                return Stream.of(
                        Arguments.of(serviceTypeSet2),
                        Arguments.of(serviceTypeSet2)
                );
            }

            @ParameterizedTest
            @MethodSource("getServiceTypes")
            @DisplayName("with a list, returns no element")
            void withListAsArgument(Set<String> serviceTypesSet) {
                setServiceTypes(serviceTypesSet);
                DummyTrustedList dummyTrustedList = DummyTrustedList.getInstance();
                argumentTrustedList = dummyTrustedList.getDummyTrustedList();
                serviceTypeFilter.applyTo(argumentTrustedList);
                assertTrue(argumentTrustedList.isEmpty());
            }

            @DisplayName("with a null list, throw NullPointer Exception")
            @ParameterizedTest
            @MethodSource("getServiceTypes")
            void withNullListAsArgument(Set<String> statusSet) {
                setServiceTypes(statusSet);
                argumentTrustedList = null;
                assertThrows(NullPointerException.class, () -> serviceTypeFilter.applyTo(argumentTrustedList));
            }

        }

    }

    @DisplayName("after I set a null whitelist")
    @Nested
    class setNullFilters {

        private void setServiceTypesInWhitelist(Set<String> serviceTypes) {
            serviceTypeFilter.setWhitelist(serviceTypes);
        }

        @DisplayName("and I use the method ApplyTo")
        @Nested
        class ApplyTo {
            TrustedList argumentTrustedList;

            @Test
            @DisplayName("with a list, throw NullPointerException")
            void withListAsArgument() {
                Set<String> serviceTypes = null;
                setServiceTypesInWhitelist(serviceTypes);
                DummyTrustedList dummyTrustedList = DummyTrustedList.getInstance();
                argumentTrustedList = dummyTrustedList.getDummyTrustedList();
                assertThrows(NullPointerException.class, () -> serviceTypeFilter.applyTo(argumentTrustedList));

            }
        }

    }

    private TrustedList getTestTrustedList() {
        TreeSet<Country> listOfCountries = new TreeSet<>();
        Country country1 = new Country("Austria", "AT");
        TreeSet<String> serviceTypesDatakom = new TreeSet<>();
        serviceTypesDatakom.add("test");
        TreeSet<String> serviceTypesA_sign_premium_CA = new TreeSet<>();
        serviceTypesA_sign_premium_CA.add("test");
        TreeSet<Service> servicesDatakom = new TreeSet<>();
        Provider Datakom_Austria_GmbH = new Provider(country1, 3, "Datakom Austria GmbH", "VATAT-U44837307", serviceTypesDatakom, servicesDatakom);
        Service A_sign_premium_CA = new Service(Datakom_Austria_GmbH, 1, "a-sign Premium CA", "http://uri.etsi.org/TrstSvc/Svctype/CA/QC", "withdrawn", serviceTypesA_sign_premium_CA);
        servicesDatakom.add(A_sign_premium_CA);
        country1.getProviders().add(Datakom_Austria_GmbH);

        Country country2 = new Country("Belgium", "Be");
        TreeSet<String> serviceTypesConnective = new TreeSet<>();
        serviceTypesConnective.add("test1");
        serviceTypesConnective.add("test2");
        TreeSet<String> serviceTypesConnective_Validation_Service = new TreeSet<>();
        serviceTypesConnective_Validation_Service.add("test1");
        serviceTypesConnective_Validation_Service.add("test2");
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