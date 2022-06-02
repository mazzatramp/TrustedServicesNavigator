package com.trustedservices.navigator.filters;

import com.trustedservices.Help;
import com.trustedservices.domain.TrustedList;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

//COPIA E INCOLLA DA PROVIDERFILTERLIST

@DisplayName("A nomeClasse")
class ServiceTypeFilterTest {
    ServiceTypeFilter serviceTypeFilter;

    @Nested
    @DisplayName("when null")
    class WhenNull {
        @BeforeEach
        void setTrustedListNull() {
            serviceTypeFilter = null;
        }

        @DisplayName("and I use the method ApplyTo")
        @Nested
        class ApplyTo {
            TrustedList argumentTrustedList;

            @Test
            void withListAsArgument() throws IOException {
                argumentTrustedList = Help.getWholeList();
                assertThrows(NullPointerException.class, () -> serviceTypeFilter.applyTo(argumentTrustedList));
            }

            @Test
            void withNullListAsArgument() {
                argumentTrustedList = null;
                assertThrows(NullPointerException.class, () -> serviceTypeFilter.applyTo(argumentTrustedList));
            }
        }

    }

    @Test
    @DisplayName("is instantiated with new ServiceTypeFIlter()")
    void isInstantiatedWithNewServiceTypeFilter() {
        new ServiceTypeFilter();
    }

    @Nested
    @DisplayName("when new")
    class WhenNew {
        @BeforeEach
        void createAFilterController() {
            serviceTypeFilter = new ServiceTypeFilter();
        }

        @DisplayName("and use the method ApplyTo")
        @Nested
        class ApplyTo {
            TrustedList argumentTrustedList;

            @Test
            void withListAsArgument() throws IOException {
                argumentTrustedList = Help.getWholeList();
                TrustedList expectedFilteredList = argumentTrustedList;
                serviceTypeFilter.applyTo(argumentTrustedList);
                assertEquals(expectedFilteredList, argumentTrustedList);

            }

            @Test
            void withNullListAsArgument() {
                argumentTrustedList = null;
                assertEquals(null, argumentTrustedList);
                //assertThrows(NullPointerException.class, () -> providerFilter.applyTo(argumentTrustedList));
            }

        }

        @DisplayName("and I set a possible whitelist filters")
        @Nested
        class setPossibleFilters {
            @BeforeEach
            void setPossibleFilters() throws IOException {
                Set<String> setServiceType = new HashSet<>();
                setServiceType.add("granted");
                serviceTypeFilter.setWhitelist(setServiceType);
            }

            @DisplayName("and use the method ApplyTo")
            @Nested
            class ApplyTo {
                TrustedList argumentTrustedList;

                @Test
                void withListAsArgument() throws IOException {
                    argumentTrustedList = Help.getWholeList();
                    String expectedServiceType1 = "QCertESeal";
                    List<String> expectedServiceTypes = new ArrayList<>();
                    expectedServiceTypes.add(expectedServiceType1);
                    serviceTypeFilter.applyTo(argumentTrustedList);
                    argumentTrustedList.getCountries().forEach(country -> {
                        country.getProviders().forEach(provider -> {
                            provider.getServices().forEach(service -> {
                                boolean areEq = service.getServiceTypes().equals(expectedServiceTypes);
                                assertTrue(areEq);
                            });
                        });
                    });

                }

                @Test
                void withNotPossibleListAsArgument() throws IOException {
                    argumentTrustedList = new TrustedList();
                    serviceTypeFilter.applyTo(argumentTrustedList);
                    assertTrue(argumentTrustedList.getCountries().isEmpty());
                }

                @Test
                void withNullListAsArgument() {
                    argumentTrustedList = null;
                    assertThrows(NullPointerException.class, () -> serviceTypeFilter.applyTo(argumentTrustedList));
                }

            }

        }
        /* IN TEORIA TUTTE LE COMBUNAZIONI DI FILTRI SONO POSSIBILI
        @DisplayName("and I set a not possible whitelist filters")
        @Nested
        class setNotPossibleFilters{
            @DisplayName("and I use the method ApplyTo")
            @Nested
            class ApplyTo{
                //ESSENDO PROTECTED NON LO POSSO PROVARE QUA
            }
        }
        */


    }
}