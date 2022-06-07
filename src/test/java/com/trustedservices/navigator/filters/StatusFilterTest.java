package com.trustedservices.navigator.filters;

import com.trustedservices.TestTrustedList;
import com.trustedservices.domain.TrustedList;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("I create a StatusFilter")
class StatusFilterTest {
    StatusFilter statusFilter;

    @Test
    @DisplayName("is instantiated thanks to new StatusFilter()")
    void testingConstructor() {
        new StatusFilter();
    }

    @BeforeEach
    @DisplayName("is instantiated thanks to new StatusFilter()")
    void isInstantiatedWithNewStatus() {
        statusFilter = new StatusFilter();
    }

    @DisplayName("when I use the method ApplyTo")
    // In this nested class filters are empty because I will not use setWhitelist
    @Nested
    class ApplyTo {
        TrustedList argumentTrustedList;

        @DisplayName("with a list as argument, return the same list")
        @Test
        void withListAsArgument() {
            argumentTrustedList = TestTrustedList.getWholeLocalTrustedList();
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
                Set<String> statusSet4 = new HashSet<>();//will have all possible statuses
                statusSet4.add("granted");
                statusSet4.add("withdrawn");
                statusSet4.add("deprecatedatnationallevel");
                statusSet4.add("recognisedatnationallevel");
                Set<String> statusSet5 = new HashSet<>();//the presence of one or more of made up filters like "noSenseFilter"
                // do not affect the behaviour of the filter if other meaningful filters are present
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
                argumentTrustedList = TestTrustedList.getWholeLocalTrustedList();
                AtomicInteger numberOfServicesCompatibleWithFiltersInArgumentTrustedList = new AtomicInteger();
                AtomicInteger numberOfServiceInFilteredList = new AtomicInteger();
                //these last two variables are needed to check that the number of services in the argument list compatible with the filters
                //is equal to the number of services in the filtered list

                argumentTrustedList.getCountries().forEach(country -> {
                    country.getProviders().forEach(provider -> {
                        provider.getServices().forEach(service -> {
                            if (statusSet.contains(service.getStatus()) || expectedStatusSet.isEmpty()) {
                                numberOfServicesCompatibleWithFiltersInArgumentTrustedList.getAndIncrement();
                            }
                        });
                    });
                });
                statusFilter.applyTo(argumentTrustedList);
                assertFalse(argumentTrustedList.isEmpty());
                argumentTrustedList.getCountries().forEach(country -> {
                    country.getProviders().forEach(provider -> {
                        provider.getServices().forEach(service -> {
                            numberOfServiceInFilteredList.getAndIncrement();
                            assertTrue(expectedStatusSet.contains(service.getStatus()) || expectedStatusSet.isEmpty());
                        });
                    });
                });
                assertEquals(numberOfServicesCompatibleWithFiltersInArgumentTrustedList.get(), numberOfServiceInFilteredList.get());
                //This assertion is done because if we would check only the other assertions we would not have really checked if the expected and
                //actual output are the same. The filtered list could have missed some services compatible with the filters from the argument list and
                //we would not have known. By counting the services we know.
            }

            @DisplayName("with a list with only incompatible elements with the filters as argument, return a list with no elements")
            @ParameterizedTest
            @MethodSource("getStatuses")
            void withNotPossibleListAsArgument(Set<String> statusSet) {
                setStatusesInWhitelist(statusSet);
                argumentTrustedList = TestTrustedList.getTrustedListWith(
                        "CountryWithProviderWithServices_ButStatusesSetToTest1",
                        "CountryWithProviderWithServices_ButStatusesSetToTest2");
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
                //a list of filters with only made up elements link to no services
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
                argumentTrustedList = TestTrustedList.getWholeLocalTrustedList();
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
                argumentTrustedList = TestTrustedList.getWholeLocalTrustedList();
                assertThrows(NullPointerException.class, () -> statusFilter.applyTo(argumentTrustedList));

            }
        }

    }

}