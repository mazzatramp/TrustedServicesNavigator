package com.trustedservices.navigator.filters;

import com.trustedservices.DummyTrustedList;
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

@DisplayName("I create a ServiceTypeFilter")
class ServiceTypeFilterTest {
    ServiceTypeFilter serviceTypeFilter;

    @Test
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

    @DisplayName("after I set a possible whitelist filters")
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
                return Stream.of(
                        Arguments.of(serviceTypesSet1),
                        Arguments.of(serviceTypesSet2),
                        Arguments.of(serviceTypesSet3),
                        Arguments.of(serviceTypesSet4)
                );
            }

            @ParameterizedTest
            @MethodSource("getServiceTypes")
            @DisplayName("with a list with compatible elements with the filters as argument, should return a list with only those elements")
            void withListAsArgument(Set<String> serviceTypes) {
                setServiceTypes(serviceTypes);
                DummyTrustedList dummyTrustedList = DummyTrustedList.getInstance();
                argumentTrustedList = dummyTrustedList.getDummyTrustedList();
                Set<String> expectedServiceType = serviceTypes;
                AtomicInteger numberOfServiceWithServiceTypeInWhitelist= new AtomicInteger();
                argumentTrustedList.getCountries().forEach(country -> {
                    country.getProviders().forEach(provider -> {
                        provider.getServices().forEach(service -> {
                            if(service.getServiceTypes().stream().toList().stream().anyMatch(servizio -> expectedServiceType.contains(servizio))){
                                numberOfServiceWithServiceTypeInWhitelist.getAndIncrement();
                            };
                        });
                    });
                });
                serviceTypeFilter.applyTo(argumentTrustedList);
                argumentTrustedList.getCountries().forEach(country -> {
                    country.getProviders().forEach(provider -> {
                        provider.getServices().forEach(service -> {
                            numberOfServiceWithServiceTypeInWhitelist.getAndDecrement();
                            //System.out.println(service.getServiceTypes());
                            assertTrue(service.getServiceTypes().stream().toList().stream().anyMatch(servizio -> expectedServiceType.contains(servizio)));

                        });
                    });
                });
                assertEquals(numberOfServiceWithServiceTypeInWhitelist.get(),0);


            }

            @DisplayName("with a list with only incompatible elements with the filters as argument, should return a list with no elements")
            @ParameterizedTest
            @MethodSource("getServiceTypes")
            void withNotPossibleListAsArgument(Set<String> serviceTypes) {
                setServiceTypes(serviceTypes);
                argumentTrustedList = new TrustedList();
                serviceTypeFilter.applyTo(argumentTrustedList);
                assertTrue(argumentTrustedList.getCountries().isEmpty());
            }

            @DisplayName("with a null list, should return NullPointerException")
            @ParameterizedTest
            @MethodSource("getServiceTypes")
            void withNullListAsArgument(Set<String> serviceTypes) {
                setServiceTypes(serviceTypes);
                argumentTrustedList = null;
                assertThrows(NullPointerException.class, () -> serviceTypeFilter.applyTo(argumentTrustedList));
            }

        }

    }



}