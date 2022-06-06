package com.trustedservices.navigator.filters;

import com.trustedservices.DummyTrustedList;
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

    @DisplayName("after I set a whitelist of filters")
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
                Set<String> statusSet6 = new HashSet<>();
                statusSet6.add("noSenseFilter");
                return Stream.of(
                        Arguments.of(statusSet1),
                        Arguments.of(statusSet2),
                        Arguments.of(statusSet3),
                        Arguments.of(statusSet4),
                        Arguments.of(statusSet5),
                        Arguments.of(statusSet6)

                );
            }

            @ParameterizedTest
            @MethodSource("getStatuses")
            @DisplayName("with a list with compatible elements with the filters as argument, return a list with only those elements")
            void withListAsArgument(Set<String> statusSet) {
                setStatusesInWhitelist(statusSet);
                DummyTrustedList dummyTrustedList = DummyTrustedList.getInstance();
                argumentTrustedList = dummyTrustedList.getDummyTrustedList();
                AtomicInteger numberOfServicesWithStatusInWhitelist = new AtomicInteger(); //necessario per controllare che il numero di servizi compatibili con i filtri
                //nella lista iniziale sia uguale al numero di servizi della lista filtrata

                argumentTrustedList.getCountries().forEach(country -> {
                    country.getProviders().forEach(provider -> {
                        provider.getServices().forEach(service -> {
                            if (statusSet.contains(service.getStatus())) {
                                numberOfServicesWithStatusInWhitelist.getAndIncrement();
                            }
                            ;
                        });
                    });
                });
                statusFilter.applyTo(argumentTrustedList);
                argumentTrustedList.getCountries().forEach(country -> {
                    country.getProviders().forEach(provider -> {
                        provider.getServices().forEach(service -> {
                            numberOfServicesWithStatusInWhitelist.getAndDecrement();
                            assertTrue(statusSet.contains(service.getStatus()));
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
                argumentTrustedList = new TrustedList();
                statusFilter.applyTo(argumentTrustedList);
                assertTrue(argumentTrustedList.getCountries().isEmpty());
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
}