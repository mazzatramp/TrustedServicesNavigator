package com.trustedservices.navigator.filters;

import com.trustedservices.DummyTrustedList;
import com.trustedservices.domain.Country;
import com.trustedservices.domain.Provider;
import com.trustedservices.domain.Service;
import com.trustedservices.domain.TrustedList;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("I create a StatusFilter")
class StatusFilterTest {
    StatusFilter statusFilter;

    @BeforeEach
    @DisplayName("is instantiated thanks to new StatusFilter()")
    void isInstantiatedWithNewStatus() {
        statusFilter = new StatusFilter();
    }

    @DisplayName("when I use the method ApplyTo")
    @Nested
    class ApplyTo {
        TrustedList argumentTrustedList;

        @DisplayName("with a list as argument, return the same list")
        @Test
        void withListAsArgument() {
            DummyTrustedList dummyTrustedList = DummyTrustedList.getInstance();
            argumentTrustedList = dummyTrustedList.getDummyTrustedList();
            TrustedList expectedFilteredList = argumentTrustedList;
            statusFilter.applyTo(argumentTrustedList);
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

        private void setStatusesInWhitelist(Set<String> statuses) {
            statusFilter.setWhitelist(statuses);
        }

        @DisplayName("and I use the method ApplyTo")
        @Nested
        class ApplyTo {
            TrustedList argumentTrustedList;

            private static Stream<Arguments> getStatuses() {
                Set<String> statusSet1 = new HashSet<>();
                statusSet1.add("granted");
                Set<String> statusSet2 = new HashSet<>();
                statusSet2.add("granted");
                statusSet2.add("withdrawn");
                Set<String> statusSet3 = new HashSet<>();
                statusSet3.add("withdrawn");
                Set<String> statusSet4 = new HashSet<>();
                statusSet4.add("granted");
                statusSet4.add("withdrawn");
                statusSet4.add("deprecatedatnationallevel");
                statusSet4.add("recognisedatnationallevel");
                Set<String> statusSet5 = new HashSet<>();
                statusSet5.add("granted");
                statusSet5.add("withdrawn");
                statusSet5.add("deprecatedatnationallevel");
                statusSet5.add("recognisedatnationallevel");
                statusSet5.add("noSenseFilter");

                return Stream.of(
                        Arguments.of(statusSet1),
                        Arguments.of(statusSet2),
                        Arguments.of(statusSet3),
                        Arguments.of(statusSet4),
                        Arguments.of(statusSet5)

                );
            }

            @ParameterizedTest
            @MethodSource("getStatuses")
            @DisplayName("with a list with compatible elements with the filters as argument, return a not empty list with only those elements")
            void withListAsArgument(Set<String> statusSet) {
                setStatusesInWhitelist(statusSet);
                Set<String> expectedStatusSet = statusSet;
                DummyTrustedList dummyTrustedList = DummyTrustedList.getInstance();
                argumentTrustedList = dummyTrustedList.getDummyTrustedList();
                AtomicInteger numberOfServicesWithStatusInWhitelist = new AtomicInteger(); //necessario per controllare che il numero di servizi compatibili con i filtri
                //nella lista iniziale sia uguale al numero di servizi della lista filtrata

                argumentTrustedList.getCountries().forEach(country -> {
                    country.getProviders().forEach(provider -> {
                        provider.getServices().forEach(service -> {
                            if (statusSet.contains(service.getStatus()) || expectedStatusSet.isEmpty()) {
                                numberOfServicesWithStatusInWhitelist.getAndIncrement();
                            }
                            ;
                        });
                    });
                });
                statusFilter.applyTo(argumentTrustedList);
                assertFalse(argumentTrustedList.isEmpty());
                argumentTrustedList.getCountries().forEach(country -> {
                    country.getProviders().forEach(provider -> {
                        provider.getServices().forEach(service -> {
                            numberOfServicesWithStatusInWhitelist.getAndDecrement();
                            assertTrue(expectedStatusSet.contains(service.getStatus()) || expectedStatusSet.isEmpty());
                        });
                    });
                });
                assertEquals(numberOfServicesWithStatusInWhitelist.get(), 0);

            }

            @DisplayName("with a list with only incompatible elements with the filters as argument, return a list with no elements")
            @ParameterizedTest
            @MethodSource("getStatuses")
            void withNotPossibleListAsArgument(Set<String> statusSet) {
                setStatusesInWhitelist(statusSet);
                argumentTrustedList = getTestTrustedList();
                statusFilter.applyTo(argumentTrustedList);
                assertTrue(argumentTrustedList.getCountries().isEmpty());
            }

            @DisplayName("with a null list, should return NullPointerException unless filters are empty")
            @ParameterizedTest
            @MethodSource("getStatuses")
            void withNullListAsArgumentAndNotEmptyFilters(Set<String> statuses) {
                setStatusesInWhitelist(statuses);
                argumentTrustedList = null;

                assertThrows(NullPointerException.class, () -> statusFilter.applyTo(argumentTrustedList));

            }


        }

    }

    @DisplayName("after I set a whitelist of filters that cannot link to a service")
    @Nested
    class setImpossibleFilters {

        private void setStatusesInWhitelist(Set<String> statuses) {
            statusFilter.setWhitelist(statuses);
        }

        @DisplayName("and I use the method ApplyTo")
        @Nested
        class ApplyTo {
            TrustedList argumentTrustedList;

            private static Stream<Arguments> getStatuses() {
                Set<String> statusSet1 = new HashSet<>();
                statusSet1.add("noSenseFilter");
                Set<String> statusSet2 = new HashSet<>();
                statusSet2.add("noSenseFilter");
                statusSet2.add("anotherNoSenseFilter");
                return Stream.of(
                        Arguments.of(statusSet1),
                        Arguments.of(statusSet2)
                );
            }

            @ParameterizedTest
            @MethodSource("getStatuses")
            @DisplayName("with a list returns no element")
            void withListAsArgument(Set<String> statusSet) {
                setStatusesInWhitelist(statusSet);
                DummyTrustedList dummyTrustedList = DummyTrustedList.getInstance();
                argumentTrustedList = dummyTrustedList.getDummyTrustedList();
                statusFilter.applyTo(argumentTrustedList);
                assertTrue(argumentTrustedList.isEmpty());
            }

            @DisplayName("with a null list, throw NullPointer Exception")
            @ParameterizedTest
            @MethodSource("getStatuses")
            void withNullListAsArgument(Set<String> statusSet) {
                setStatusesInWhitelist(statusSet);
                argumentTrustedList = null;
                assertThrows(NullPointerException.class, () -> statusFilter.applyTo(argumentTrustedList));
            }

        }

    }


    @DisplayName("after I set a null whitelist")
    @Nested
    class setNullFilters {

        private void setStatusesInWhitelist(Set<String> statuses) {
            statusFilter.setWhitelist(statuses);
        }

        @DisplayName("and I use the method ApplyTo")
        @Nested
        class ApplyTo {
            TrustedList argumentTrustedList;

            @Test
            @DisplayName("with a list, throw NullPointerException")
            void withListAsArgument() {
                Set<String> statusSet = null;
                setStatusesInWhitelist(statusSet);
                DummyTrustedList dummyTrustedList = DummyTrustedList.getInstance();
                argumentTrustedList = dummyTrustedList.getDummyTrustedList();
                assertThrows(NullPointerException.class, () -> statusFilter.applyTo(argumentTrustedList));

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
        Service A_sign_premium_CA = new Service(Datakom_Austria_GmbH, 1, "a-sign Premium CA", "http://uri.etsi.org/TrstSvc/Svctype/CA/QC", "test1", serviceTypesA_sign_premium_CA);
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
        Service Connective_Validation_Service = new Service(Connective, 1, "Connective Validation Service", "http://uri.etsi.org/TrstSvc/Svctype/QESValidation/Q", "test2", serviceTypesConnective_Validation_Service);
        servicesConnective.add(Connective_Validation_Service);
        country2.getProviders().add(Connective);
        listOfCountries.add(country1);
        listOfCountries.add(country2);
        TrustedList testTrustedList = new TrustedList(listOfCountries);
        testTrustedList.updateServiceTypesAndStatuses();
        return testTrustedList;
    }
}