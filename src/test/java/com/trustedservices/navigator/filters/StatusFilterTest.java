package com.trustedservices.navigator.filters;

import com.trustedservices.Help;
import com.trustedservices.domain.Provider;
import com.trustedservices.domain.TrustedList;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

//COPIA E INCOLLA DA PROVIDERFILTERLIST

@DisplayName("A StatusFilter")
class StatusFilterTest {
    StatusFilter statusFilter;

    @Test
    @DisplayName("is instantiated with new StatusFIlter()")
    void isInstantiatedWithNewStatus() {
        new StatusFilter();
    }
    @Nested
    @DisplayName("when new")
    class WhenNew
    {
        @BeforeEach
        void createAFilterController(){
            statusFilter = new StatusFilter();
        }
        @DisplayName("and I use the method ApplyTo")
        @Nested
        class ApplyTo{
            TrustedList argumentTrustedList;
            @DisplayName("with a list as argument, should return the same list")
            @Test
            void withListAsArgument() throws IOException {
                argumentTrustedList = Help.getWholeList();
                TrustedList expectedFilteredList = argumentTrustedList;
                statusFilter.applyTo(argumentTrustedList);
                assertEquals(expectedFilteredList,argumentTrustedList);

            }
            @DisplayName("with a null list as argument, should return a null list")
            @Test
            void withNullListAsArgument(){
                argumentTrustedList = null;
                assertEquals(null ,argumentTrustedList);
                //assertThrows(NullPointerException.class, () -> providerFilter.applyTo(argumentTrustedList));
            }

        }
        @DisplayName("and I set a possible whitelist filters")
        @Nested
        class setPossibleFilters{
            @BeforeEach
            void setPossibleFilters() throws IOException {
                Set<String> setStatus = new HashSet<>();
                setStatus.add("granted");
                statusFilter.setWhitelist(setStatus);
            }
            @DisplayName("and I use the method ApplyTo")
            @Nested
            class ApplyTo{
                TrustedList argumentTrustedList;
                @DisplayName("with a list with compatible elements with the filters as argument, should return a list with only those elements")
                @Test
                void withListAsArgument() throws IOException {
                    argumentTrustedList = Help.getWholeList();
                    String expectedStatus1 = "granted";
                    statusFilter.applyTo(argumentTrustedList);
                    argumentTrustedList.getCountries().forEach(country -> {
                        country.getProviders().forEach(provider -> {
                            provider.getServices().forEach(service -> {
                                assertTrue(service.getStatus().equals(expectedStatus1));                             });
                        });
                    });

                }
                @DisplayName("with a list with only incompatible elements with the filters as argument, should return a list with no elements")
                @Test
                void withNotPossibleListAsArgument() throws IOException {
                    argumentTrustedList = new TrustedList();
                    statusFilter.applyTo(argumentTrustedList);
                    assertTrue(argumentTrustedList.getCountries().isEmpty());
                }
                @DisplayName("with a null list, should return NullPointerException")
                @Test
                void withNullListAsArgument(){
                    argumentTrustedList = null;
                    assertThrows(NullPointerException.class, () -> statusFilter.applyTo(argumentTrustedList));
                }

            }

        }

    }
}