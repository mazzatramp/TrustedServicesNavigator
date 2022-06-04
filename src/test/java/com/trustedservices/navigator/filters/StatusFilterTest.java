package com.trustedservices.navigator.filters;

import com.trustedservices.Help;
import com.trustedservices.domain.TrustedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

//COPIA E INCOLLA DA PROVIDERFILTERLIST

@DisplayName("I create a StatusFilter")//esiste un termine piu tecnico di create?
class StatusFilterTest {
    StatusFilter statusFilter = new StatusFilter();

    @Test
    @DisplayName("is instantiated with new StatusFilter()")
    void isInstantiatedWithNewStatus() {
        new StatusFilter();
    }

    //whitelist is set with setWhitelist method

    @DisplayName("and I use the method ApplyTo")
    @Nested
    class ApplyTo {
        TrustedList argumentTrustedList;

        @DisplayName("with a list as argument, should return the same list")
        @Test
        void withListAsArgument(){
            argumentTrustedList = Help.getWholeList();
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

        private void setStatuses(String status){
            Set<String> setStatus = new HashSet<>();
            setStatus.add(status);
            statusFilter.setWhitelist(setStatus);
        }
        @DisplayName("and I use the method ApplyTo")
        @Nested
        class ApplyTo {
            TrustedList argumentTrustedList;

            private static Stream<Arguments> getStatuses(){
                return Stream.of(
                        Arguments.of("granted"),
                        Arguments.of("withdrawn")
                );
            }
            @ParameterizedTest
            @MethodSource("getStatuses")
            @DisplayName("with a list with compatible elements with the filters as argument, should return a list with only those elements")
            void withListAsArgument(String status) {
                setStatuses(status);
                argumentTrustedList = Help.getWholeList();
                String expectedStatus1 = status;
                statusFilter.applyTo(argumentTrustedList);
                argumentTrustedList.getCountries().forEach(country -> {
                    country.getProviders().forEach(provider -> {
                        provider.getServices().forEach(service -> {
                            assertTrue(service.getStatus().equals(expectedStatus1));
                        });
                    });
                });


            }


            @DisplayName("with a list with only incompatible elements with the filters as argument, should return a list with no elements")
            @ParameterizedTest
            @MethodSource("getStatuses")
            void withNotPossibleListAsArgument(){
                argumentTrustedList = new TrustedList();
                statusFilter.applyTo(argumentTrustedList);
                assertTrue(argumentTrustedList.getCountries().isEmpty());
            }

            @DisplayName("with a null list,  should return a list with no elements")
            @ParameterizedTest
            @MethodSource("getStatuses")
            void withNullListAsArgument() {
                argumentTrustedList = new TrustedList();
                statusFilter.applyTo(argumentTrustedList);
                assertTrue(argumentTrustedList.getCountries().isEmpty());
            }

        }

    }
    //IMPOSSIBILE WHITHELIST FILTERS COMBINATION DO NOT EXIST

}