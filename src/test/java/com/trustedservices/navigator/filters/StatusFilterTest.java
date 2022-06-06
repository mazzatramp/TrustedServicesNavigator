package com.trustedservices.navigator.filters;

import com.trustedservices.DummyTrustedList;
import com.trustedservices.domain.TrustedList;
import org.junit.jupiter.api.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("I create a StatusFilter")
class StatusFilterTest {
    StatusFilter statusFilter;

    @Test
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

    @DisplayName("after I set a possible whitelist of filters")
    @Nested
    class setPossibleFilters {

       private void setStatuses(Set<String> statuses) {
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
                //METTERE CASO IN CUI NON HO WHITELIST
                return Stream.of(
                        Arguments.of(statusSet1),
                        Arguments.of(statusSet2),
                        Arguments.of(statusSet3)
                );
            }
            @ParameterizedTest
            @MethodSource("getStatuses")
            @DisplayName("with a list with compatible elements with the filters as argument, return a list with only those elements")
            void withListAsArgument(Set<String> statusSet) {
                setStatuses(statusSet);
                DummyTrustedList dummyTrustedList = DummyTrustedList.getInstance();
                argumentTrustedList = dummyTrustedList.getDummyTrustedList();
                statusFilter.applyTo(argumentTrustedList);
                //assertFalse(argumentTrustedList.isEmpty());
                argumentTrustedList.getCountries().forEach(country -> {
                    country.getProviders().forEach(provider -> {
                        provider.getServices().forEach(service -> {
                            System.out.println(service.getStatus());
                            assertTrue(statusSet.contains(service.getStatus()));
                        });
                    });
                });
                //IN VERITA' STO CONTROLLANDO CHE NELLA LISTA VI ELEMENTI CORRETTI MA POTREBBE ESSERE STATO CANCELLATO QUALCHE ELEMENTO NEL PROCESSO
                //DEVO DUNQUE CONTARE LGI ELEMENTI INIZIALI E FINALI O TROVARTE UN ALTRA SOLUZIONE
            }

            @DisplayName("with a list with only incompatible elements with the filters as argument, return a list with no elements")
            @ParameterizedTest
            @MethodSource("getStatuses")
            void withNotPossibleListAsArgument(Set<String> statusSet) {
                setStatuses(statusSet);
                argumentTrustedList = new TrustedList();
                statusFilter.applyTo(argumentTrustedList);
                assertTrue(argumentTrustedList.getCountries().isEmpty());
            }

            @DisplayName("with a null list, return a list null list")
            @ParameterizedTest
            @MethodSource("getStatuses")
            void withNullListAsArgument(Set<String> statusSet) {
                setStatuses(statusSet);
                argumentTrustedList = null;
                assertThrows(NullPointerException.class,() -> statusFilter.applyTo(argumentTrustedList));
            }

        }

    }

    //IMPOSSIBILE (like no sense words) WHITHELIST FILTERS ?

}