package com.trustedservices.navigator.filters;

import com.trustedservices.Help;
import com.trustedservices.domain.TrustedList;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

            @DisplayName("and I use the method getFilteredListFrom")
            @Nested
            class getFilteredListFrom {
                private static Stream<Arguments> getFilters() {
                    List<Filter> collectionOfFilters = new ArrayList<>();
                    Set<String> providerSet = new HashSet<>();
                    providerSet.add("Austria/PrimeSign GmbH");
                    ProviderFilter filterProvider = new ProviderFilter();
                    filterProvider.setWhitelist(providerSet);
                    Set<String> serviceTypeSet = new HashSet<>();
                    serviceTypeSet.add("QCertESeal");
                    ServiceTypeFilter filterServiceType = new ServiceTypeFilter();
                    filterServiceType.setWhitelist(serviceTypeSet);
                    Set<String> statusSet = new HashSet<>();
                    statusSet.add("granted");
                    StatusFilter filterStatus = new StatusFilter();
                    filterStatus.setWhitelist(statusSet);
                    collectionOfFilters.add(filterProvider);
                    collectionOfFilters.add(filterServiceType);
                    collectionOfFilters.add(filterStatus);

                    //mi da problemi
                    List<Filter> collectionOfFilters2 = new ArrayList<>();
                    ProviderFilter filterProvider2 = new ProviderFilter();
                    ServiceTypeFilter filterServiceType2 = new ServiceTypeFilter();
                    StatusFilter filterStatus2 = new StatusFilter();
                    collectionOfFilters2.add(filterProvider2);
                    collectionOfFilters2.add(filterServiceType2);
                    collectionOfFilters2.add(filterStatus2);

                    List<Filter> collectionOfFilters3 = new ArrayList<>();
                    Set<String> providerSet3 = new HashSet<>();
                    providerSet3.add("Italy/Azienda Zero");
                    ProviderFilter filterProvider3 = new ProviderFilter();
                    filterProvider3.setWhitelist(providerSet3);
                    Set<String> serviceTypeSet3 = new HashSet<>();
                    serviceTypeSet3.add("QCertESeal");
                    ServiceTypeFilter filterServiceType3 = new ServiceTypeFilter();
                    filterServiceType3.setWhitelist(serviceTypeSet3);
                    Set<String> statusSet3 = new HashSet<>();
                    statusSet3.add("withdrawn");
                    StatusFilter filterStatus3 = new StatusFilter();
                    filterStatus3.setWhitelist(statusSet);
                    collectionOfFilters3.add(filterProvider);
                    collectionOfFilters3.add(filterServiceType);
                    collectionOfFilters3.add(filterStatus);
                    return Stream.of(
                            Arguments.of(collectionOfFilters),
                            //Arguments.of(collectionOfFilters2),
                            Arguments.of(collectionOfFilters3)
                    );
                }

                TrustedList argumentTrustedList;

                @DisplayName("a list as argument that contain services possible, should return just the filtered services")
                @ParameterizedTest
                @MethodSource("getFilters")
                void PossibleFiltersTrustedListAsArgument(List<Filter> filters) {
                    createFilterList(filters);
                    argumentTrustedList = Help.getWholeList();
                    System.out.println(argumentTrustedList.getCountries());
                    Set<String> providersExpected = filters.get(0).getWhitelist();
                    Set<String> expectedServiceTypes = filters.get(1).getWhitelist();
                    Set<String> expectedStatuses = filters.get(2).getWhitelist();
                    System.out.println("ecco l'expected provider " + providersExpected);
                    System.out.println("ecco l'expectedstatus " + expectedStatuses);
                    System.out.println("ecco l'expectedservycetype " + expectedServiceTypes);
                    System.out.println(filterList.get(0).getWhitelist());
                    System.out.println(filterList.get(1).getWhitelist());
                    System.out.println(filterList.get(2).getWhitelist());
                    TrustedList filteredList = filterList.getFilteredListFrom(argumentTrustedList);
                    System.out.println("countries lista filtrata " + filteredList.getCountries());
                    assertFalse(filteredList.isEmpty());
                    filteredList.getCountries().forEach(country -> {
                        country.getProviders().forEach(provider -> {
                            System.out.println(provider.getName());
                            assertTrue(providersExpected.contains(country.getName() + "/" + provider.getName()));
                            provider.getServices().forEach(service -> {
                                System.out.println(service.getServiceTypes());
                                System.out.println(service.getStatus());
                                assertTrue(service.getServiceTypes().stream().toList().stream().anyMatch(servizio -> expectedServiceTypes.contains(servizio)));
                                assertTrue(expectedStatuses.contains(service.getStatus()));
                            });
                        });
                    });
                }

                @DisplayName("a list as argument that do not contain services possible, should return just no services")
                @ParameterizedTest
                @MethodSource("getFilters")
                void PossibleFiltersButNotPossibleTrustedListAsArgument(List<Filter> filters){
                    createFilterList(filters);

                    argumentTrustedList = new TrustedList();
                    TrustedList filteredList = filterList.getFilteredListFrom(argumentTrustedList);
                    assertTrue(filteredList.getCountries().isEmpty());
                    filteredList.getCountries().forEach(country -> {
                        assertTrue(country.getProviders().isEmpty());
                        country.getProviders().forEach(provider -> {
                            assertTrue(provider.getServices().isEmpty()); //OPPURE BASTA VEDERE CHE L'ARRAY DI COUNTRIES E' VUOTO?
                        });
                    });
                }


                @DisplayName("Null TrustedList as argument, should return an error")
                @ParameterizedTest
                @MethodSource("getFilters")
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
                TrustedList argumentTrustedList; //HA SENSO RIPETERE OGNI VOLTA ARGUMENTTRUSTEDLIST?

                private static Stream<Arguments> getFilters() {
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
                @MethodSource("getFilters")
                void TrustedListAsArgument(List<Filter> filters) {
                    createFilterList(filters);
                    argumentTrustedList = Help.getWholeList();

                    TrustedList filteredList = filterList.getFilteredListFrom(argumentTrustedList);
                    assertTrue(filteredList.getCountries().isEmpty());
                    filteredList.getCountries().forEach(country -> {
                        assertTrue(country.getProviders().isEmpty());
                        country.getProviders().forEach(provider -> {
                            assertTrue(provider.getServices().isEmpty()); //OPPURE BASTA VEDERE CHE L'ARRAY DI COUNTRIES E' VUOTO?
                        });
                    });
                }
            }

        }
    }
     //prova con collezione di filtri vuota
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
                argumentTrustedList = Help.getWholeList();

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
    } //E'UN ERRORE METTERE COSI' COME ARGOMENTO?
    //C'E' ANCHE UN ALTRO COSTRUTTORE IN VERITA'

}
