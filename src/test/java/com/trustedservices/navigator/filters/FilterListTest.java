package com.trustedservices.navigator.filters;

import com.trustedservices.DummyTrustedList;
import com.trustedservices.domain.TrustedList;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("I create a FilterList")
public class FilterListTest {

    FilterList filterList;

    @Nested
    @DisplayName("when new with collection of filters")
    class WhenNewWithFilters {

        private void createFilterList(List<Filter> filters) {
            filterList = new FilterList(filters);
        }

        @DisplayName("and the filters can link to a service")
        @Nested
        class PossibleFilter {
            TrustedList argumentTrustedList;

            @DisplayName("and I use the method getFilteredListFrom")
            @Nested
            class getFilteredListFrom {
                private static Stream<Arguments> getFiltersThatCanLinkToAService() {
                    List<Filter> collectionOfFilters1 = new ArrayList<>();
                    Set<String> providerSet1 = new HashSet<>();
                    providerSet1.add("Austria/PrimeSign GmbH");
                    ProviderFilter filterProvider1 = new ProviderFilter();
                    filterProvider1.setWhitelist(providerSet1);
                    Set<String> serviceTypeSet1 = new HashSet<>();
                    serviceTypeSet1.add("QCertESeal");
                    ServiceTypeFilter filterServiceType1 = new ServiceTypeFilter();
                    filterServiceType1.setWhitelist(serviceTypeSet1);
                    Set<String> statusSet1 = new HashSet<>();
                    statusSet1.add("granted");
                    StatusFilter filterStatus1 = new StatusFilter();
                    filterStatus1.setWhitelist(statusSet1);
                    collectionOfFilters1.add(filterProvider1);
                    collectionOfFilters1.add(filterServiceType1);
                    collectionOfFilters1.add(filterStatus1);

                    List<Filter> collectionOfFilters2 = new ArrayList<>();
                    Set<String> providerSet2 = new HashSet<>();
                    providerSet2.add("Italy/Azienda Zero");
                    ProviderFilter filterProvider2 = new ProviderFilter();
                    filterProvider2.setWhitelist(providerSet2);
                    Set<String> serviceTypeSet2 = new HashSet<>();
                    serviceTypeSet2.add("QCertESeal");
                    ServiceTypeFilter filterServiceType2 = new ServiceTypeFilter();
                    filterServiceType2.setWhitelist(serviceTypeSet2);
                    Set<String> statusSet2 = new HashSet<>();
                    statusSet2.add("withdrawn");
                    StatusFilter filterStatus2 = new StatusFilter();
                    filterStatus2.setWhitelist(statusSet1);
                    collectionOfFilters2.add(filterProvider1);
                    collectionOfFilters2.add(filterServiceType1);
                    collectionOfFilters2.add(filterStatus1);
                    return Stream.of(
                            Arguments.of(collectionOfFilters1),
                            Arguments.of(collectionOfFilters2)
                    );
                }

                @DisplayName("a list as argument that contain services possible, should return just the filtered services")
                @ParameterizedTest
                @MethodSource("getFiltersThatCanLinkToAService")
                void PossibleFiltersTrustedListAsArgument(List<Filter> filters) {

                    createFilterList(filters);
                    DummyTrustedList dummyTrustedList = DummyTrustedList.getInstance();
                    argumentTrustedList = dummyTrustedList.getDummyTrustedList();
                    Set<String> providersExpected = filters.get(0).getWhitelist();
                    Set<String> expectedServiceTypes = filters.get(1).getWhitelist();
                    Set<String> expectedStatuses = filters.get(2).getWhitelist();
                    AtomicInteger numberOfServicesCompatibleWithFilters = new AtomicInteger(); //necessario per controllare che il numero di servizi compatibili con i filtri
                    //nella lista iniziale sia uguale al numero di servizi della lista filtrata

                    argumentTrustedList.getCountries().forEach(country -> {
                        country.getProviders().forEach(provider -> {
                            if (providersExpected.contains(country.getName() + "/" + provider.getName())) {
                                provider.getServices().forEach(service -> {
                                    if ((expectedStatuses.contains(service.getStatus())) && (service.getServiceTypes().stream().anyMatch(currentService -> expectedServiceTypes.contains(currentService)))) {
                                        numberOfServicesCompatibleWithFilters.getAndIncrement();
                                    }
                                });
                            }
                        });
                    });
                    TrustedList filteredList = filterList.getFilteredListFrom(argumentTrustedList);
                    assertFalse(filteredList.isEmpty());
                    filteredList.getCountries().forEach(country -> {
                        country.getProviders().forEach(provider -> {
                            assertTrue(providersExpected.contains(country.getName() + "/" + provider.getName()));
                            provider.getServices().forEach(service -> {
                                numberOfServicesCompatibleWithFilters.getAndDecrement();
                                assertTrue(service.getServiceTypes().stream().anyMatch(currentService -> expectedServiceTypes.contains(currentService)));
                                assertTrue(expectedStatuses.contains(service.getStatus()));
                            });
                        });
                    });
                    assertEquals(0, numberOfServicesCompatibleWithFilters.get());
                }

                @DisplayName("a list as argument that do not contain services possible, should return just no services")
                @ParameterizedTest
                @MethodSource("getFiltersThatCanLinkToAService")
                void PossibleFiltersButNotPossibleTrustedListAsArgument(List<Filter> filters) {
                    createFilterList(filters);
                    argumentTrustedList = new TrustedList();
                    TrustedList filteredList = filterList.getFilteredListFrom(argumentTrustedList);
                    assertTrue(filteredList.getCountries().isEmpty());

                }


                @DisplayName("Null as argument, should return an error")
                @ParameterizedTest
                @MethodSource("getFiltersThatCanLinkToAService")
                void NullTrustedListAsArgument(List<Filter> filters) {
                    createFilterList(filters);

                    argumentTrustedList = null;

                    assertThrows(NullPointerException.class, () -> filterList.getFilteredListFrom(argumentTrustedList));

                }

            }

        }

        @DisplayName("and the filters do not link to any service")
        @Nested
        class ImpossibleFilters {

            @DisplayName("and I use the method getFilteredListFrom")
            @Nested
            class getFilteredListFrom {
                TrustedList argumentTrustedList;

                private static Stream<Arguments> getFiltersThatCannotLinkToAService() {
                    List<Filter> collectionOfFilters = new ArrayList<>();
                    Set<String> providerSet = new HashSet<>();
                    providerSet.add("PrimeSign GmbH");
                    ProviderFilter filterProvider = new ProviderFilter();
                    filterProvider.setWhitelist(providerSet);
                    Set<String> serviceTypeSet = new HashSet<>();
                    serviceTypeSet.add("QCertESeal");
                    ServiceTypeFilter filterServiceType = new ServiceTypeFilter();
                    filterServiceType.setWhitelist(serviceTypeSet);
                    Set<String> statusSet = new HashSet<>();
                    statusSet.add("withdrawn");
                    StatusFilter filterStatus = new StatusFilter();
                    filterStatus.setWhitelist(statusSet);
                    collectionOfFilters.add(filterProvider);
                    collectionOfFilters.add(filterServiceType);
                    collectionOfFilters.add(filterStatus);
                    return Stream.of(
                            Arguments.of(collectionOfFilters)
                    );
                }

                @DisplayName("with impossible filters and a list as argument, should return no providers")
                @ParameterizedTest
                @MethodSource("getFiltersThatCannotLinkToAService")
                void TrustedListAsArgument(List<Filter> filters) {
                    createFilterList(filters);
                    DummyTrustedList dummyTrustedList = DummyTrustedList.getInstance();
                    argumentTrustedList = dummyTrustedList.getDummyTrustedList();

                    TrustedList filteredList = filterList.getFilteredListFrom(argumentTrustedList);
                    assertTrue(filteredList.getCountries().isEmpty());

                }
            }

        }
    }

    @Nested
    @DisplayName("when new")
    class WhenNew {
        @BeforeEach
        void createACountry() {
            filterList = new FilterList();
        }

        @DisplayName("and I use the method getFilteredListFrom(TrustedList)")
        @Nested
        class GetFilteredList {
            TrustedList argumentTrustedList;

            @DisplayName("with a TrustedList as argument should return the same trustedList")
            @Test
            void trustedListAsArgument() {
                DummyTrustedList dummyTrustedList = DummyTrustedList.getInstance();
                argumentTrustedList = dummyTrustedList.getDummyTrustedList();

                TrustedList filteredList = filterList.getFilteredListFrom(argumentTrustedList);
                assertEquals(filteredList, argumentTrustedList);
            }

            @DisplayName("with a null TrustedList as argument should return error")
            @Test
            void cloneNullTrustedList() {
                argumentTrustedList = null;
                assertThrows(NullPointerException.class, () -> filterList.getFilteredListFrom(argumentTrustedList));
            }
        }
    }

    @Test
    @DisplayName("is instantiated with new FilterList()")
    void ConstructorWithoutArguments() {
        new FilterList();
    }

    @Test
    @DisplayName("is instantiated with new FilterList(Collection<? extends Filter>)")
    void ConstructorWithFilters() {
        new FilterList(new ArrayList<>());
    }

}
