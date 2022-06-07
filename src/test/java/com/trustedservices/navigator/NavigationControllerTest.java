package com.trustedservices.navigator;

import com.trustedservices.DummyTrustedList;
import com.trustedservices.domain.TrustedList;
import com.trustedservices.navigator.filters.*;
import com.trustedservices.navigator.web.TrustedListBuilder;
import com.trustedservices.navigator.web.TrustedListJsonBuilder;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("A NavigationController")
class NavigationControllerTest {

    NavigationController navigationController;

    @Test
    @DisplayName("is instantiated with new NavigationController()")
    void isInstantiatedWithNewClass() {
        new NavigationController();
    }

    @Nested
    @DisplayName("when new")
    class WhenNew {

        @BeforeEach
        void createANavigationMediator() {
            navigationController = new NavigationController();
        }

        @DisplayName("and I use the method getFilteredList after building a complete list")
        @Nested
        class getFilteredList {
            @BeforeEach
            void buildList() throws IOException {
                TrustedListJsonBuilder builder = new TrustedListJsonBuilder();
                Path countries = Path.of("src/test/java/com/trustedservices/navigator/dummyCopyTrustedList/countryListDummy.json");
                Path providers = Path.of("src/test/java/com/trustedservices/navigator/dummyCopyTrustedList/providerListDummy.json");
                builder.setCountriesJson(Files.readString(countries));
                builder.setProvidersJson(Files.readString(providers));
                navigationController.buildCompleteList(builder);
            }

            private void setFilters(List<Filter> filters) {
                navigationController.getFilters().add(filters.get(0));
                navigationController.getFilters().add(filters.get(1));
                navigationController.getFilters().add(filters.get(2));
            }

            @DisplayName("when filters are empty should return the same trustedList")
            @Test
            void emptyFilters() {
                TrustedList expectedList = navigationController.getCompleteList();
                TrustedList filteredList = navigationController.getFilteredList();
                boolean areListEquals = filteredList.equals(expectedList);
                assertTrue(areListEquals);
            }

            @Nested
            @DisplayName("when filters are filled")
            class FilledFilters {
                private static Stream<Arguments> getFiltersThatCannotLinkToAService() {
                    List<Filter> collectionOfFilters1 = new ArrayList<>();
                    Set<String> providerSet1 = new HashSet<>();
                    providerSet1.add("PrimeSign GmbH");
                    ProviderFilter filterProvider1 = new ProviderFilter();
                    filterProvider1.setWhitelist(providerSet1);
                    Set<String> serviceTypeSet1 = new HashSet<>();
                    serviceTypeSet1.add("QCertESeal");
                    ServiceTypeFilter filterServiceType1 = new ServiceTypeFilter();
                    filterServiceType1.setWhitelist(serviceTypeSet1);
                    Set<String> statusSet1 = new HashSet<>();
                    statusSet1.add("withdrawn");
                    StatusFilter filterStatus1 = new StatusFilter();
                    filterStatus1.setWhitelist(statusSet1);
                    collectionOfFilters1.add(filterProvider1);
                    collectionOfFilters1.add(filterServiceType1);
                    collectionOfFilters1.add(filterStatus1);

                    List<Filter> collectionOfFilters2 = new ArrayList<>();
                    Set<String> providerSet2 = new HashSet<>();
                    providerSet2.add("PrimeSign GmbH");
                    ProviderFilter filterProvider2 = new ProviderFilter();
                    filterProvider2.setWhitelist(providerSet2);
                    Set<String> serviceTypeSet2 = new HashSet<>();
                    ServiceTypeFilter filterServiceType2 = new ServiceTypeFilter();
                    filterServiceType2.setWhitelist(serviceTypeSet2);
                    Set<String> statusSet2 = new HashSet<>();
                    statusSet2.add("NoSenseFilter");
                    statusSet2.add("AnotherNoSenseFilter");
                    StatusFilter filterStatus2 = new StatusFilter();
                    filterStatus2.setWhitelist(statusSet2);
                    collectionOfFilters2.add(filterProvider2);
                    collectionOfFilters2.add(filterServiceType2);
                    collectionOfFilters2.add(filterStatus2);
                    return Stream.of(
                            Arguments.of(collectionOfFilters1),
                            Arguments.of(collectionOfFilters2)
                    );
                }

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
                    filterStatus2.setWhitelist(statusSet2);
                    collectionOfFilters2.add(filterProvider2);
                    collectionOfFilters2.add(filterServiceType2);
                    collectionOfFilters2.add(filterStatus2);

                    List<Filter> collectionOfFilters3 = new ArrayList<>();
                    Set<String> providerSet3 = new HashSet<>();
                    providerSet3.add("Italy/Azienda Zero");
                    providerSet3.add("Austria/PrimeSign GmbH");
                    ProviderFilter filterProvider3 = new ProviderFilter();
                    filterProvider3.setWhitelist(providerSet3);
                    Set<String> serviceTypeSet3 = new HashSet<>();
                    serviceTypeSet3.add("QCertESeal");
                    ServiceTypeFilter filterServiceType3 = new ServiceTypeFilter();
                    filterServiceType3.setWhitelist(serviceTypeSet3);
                    Set<String> statusSet3 = new HashSet<>();
                    statusSet3.add("withdrawn");
                    StatusFilter filterStatus3 = new StatusFilter();
                    filterStatus3.setWhitelist(statusSet3);
                    collectionOfFilters3.add(filterProvider3);
                    collectionOfFilters3.add(filterServiceType3);
                    collectionOfFilters3.add(filterStatus3);

                    List<Filter> collectionOfFilters4 = new ArrayList<>();
                    Set<String> providerSet4 = new HashSet<>();
                    ProviderFilter filterProvider4 = new ProviderFilter();
                    providerSet4.add("Austria/PrimeSign GmbH");
                    filterProvider4.setWhitelist(providerSet4);
                    Set<String> serviceTypeSet4 = new HashSet<>();
                    ServiceTypeFilter filterServiceType4 = new ServiceTypeFilter();
                    filterServiceType4.setWhitelist(serviceTypeSet4);
                    Set<String> statusSet4 = new HashSet<>();
                    StatusFilter filterStatus4 = new StatusFilter();
                    filterStatus4.setWhitelist(statusSet4);
                    collectionOfFilters4.add(filterProvider4);
                    collectionOfFilters4.add(filterServiceType4);
                    collectionOfFilters4.add(filterStatus4);

                    List<Filter> collectionOfFilters5 = new ArrayList<>();
                    Set<String> providerSet5 = new HashSet<>();
                    ProviderFilter filterProvider5 = new ProviderFilter();
                    providerSet5.add("Austria/PrimeSign GmbH");
                    filterProvider5.setWhitelist(providerSet5);
                    Set<String> serviceTypeSet5 = new HashSet<>();
                    ServiceTypeFilter filterServiceType5 = new ServiceTypeFilter();
                    filterServiceType5.setWhitelist(serviceTypeSet5);
                    Set<String> statusSet5 = new HashSet<>();
                    statusSet5.add("noSenseFilter");
                    statusSet5.add("granted");
                    StatusFilter filterStatus5 = new StatusFilter();
                    filterStatus4.setWhitelist(statusSet5);
                    collectionOfFilters5.add(filterProvider5);
                    collectionOfFilters5.add(filterServiceType5);
                    collectionOfFilters5.add(filterStatus5);
                    return Stream.of(
                            Arguments.of(collectionOfFilters1),
                            Arguments.of(collectionOfFilters2),
                            Arguments.of(collectionOfFilters3),
                            Arguments.of(collectionOfFilters4),
                            Arguments.of(collectionOfFilters5)
                    );
                }

                @DisplayName("and the filters can link to a service, should return a not empty list with just the filtered services")
                @ParameterizedTest
                @MethodSource("getFiltersThatCanLinkToAService")
                void possibleFilters(List<Filter> filters) {

                    setFilters(filters);
                    Set<String> providersExpected = filters.get(0).getWhitelist();
                    Set<String> expectedServiceTypes = filters.get(1).getWhitelist();
                    Set<String> expectedStatuses = filters.get(2).getWhitelist();
                    AtomicInteger numberOfServicesCompatibleWithFilters = new AtomicInteger(); //necessario per controllare che il numero di servizi compatibili con i filtri
                    //nella lista iniziale sia uguale al numero di servizi della lista filtrata

                    navigationController.getCompleteList().getCountries().forEach(country -> {
                        country.getProviders().forEach(provider -> {
                            if ((providersExpected.contains(country.getName() + "/" + provider.getName())) || (providersExpected.isEmpty())) {
                                provider.getServices().forEach(service -> {
                                    if (((expectedStatuses.contains(service.getStatus())) || (expectedStatuses.isEmpty()))
                                            && ((service.getServiceTypes().stream().anyMatch(currentService -> expectedServiceTypes.contains(currentService))) || (expectedServiceTypes.isEmpty()))) {
                                        numberOfServicesCompatibleWithFilters.getAndIncrement();
                                    }
                                });
                            }
                        });
                    });
                    TrustedList filteredList = navigationController.getFilteredList();
                    assertFalse(filteredList.isEmpty());
                    filteredList.getCountries().forEach(country -> {
                        country.getProviders().forEach(provider -> {
                            assertTrue((providersExpected.contains(country.getName() + "/" + provider.getName())) || (providersExpected.isEmpty()));
                            provider.getServices().forEach(service -> {
                                numberOfServicesCompatibleWithFilters.getAndDecrement();
                                assertTrue((service.getServiceTypes().stream().anyMatch(currentService -> expectedServiceTypes.contains(currentService))) || (expectedServiceTypes.isEmpty()));
                                assertTrue((expectedStatuses.contains(service.getStatus())) || (expectedStatuses.isEmpty()));
                            });
                        });
                    });
                    assertEquals(0, numberOfServicesCompatibleWithFilters.get());
                }

                @DisplayName("and the filters do not link to any service, the filtered list is empty")
                @ParameterizedTest
                @MethodSource("getFiltersThatCannotLinkToAService")
                void impossibleFilters(List<Filter> filters) {
                    setFilters(filters);
                    TrustedList filteredList = navigationController.getFilteredList();
                    assertTrue(filteredList.isEmpty());
                }

            }

            private static Stream<Arguments> getFiltersNull() {
                List<Filter> collectionOfFilters1 = new ArrayList<>();
                Set<String> providerSet1 = null;
                ProviderFilter filterProvider1 = new ProviderFilter();
                filterProvider1.setWhitelist(providerSet1);
                Set<String> serviceTypeSet1 = null;
                ServiceTypeFilter filterServiceType1 = new ServiceTypeFilter();
                filterServiceType1.setWhitelist(serviceTypeSet1);
                Set<String> statusSet1 = null;
                StatusFilter filterStatus1 = new StatusFilter();
                filterStatus1.setWhitelist(statusSet1);
                collectionOfFilters1.add(filterProvider1);
                collectionOfFilters1.add(filterServiceType1);
                collectionOfFilters1.add(filterStatus1);

                List<Filter> collectionOfFilters2 = new ArrayList<>();
                Set<String> providerSet2 = null;
                ProviderFilter filterProvider2 = new ProviderFilter();
                filterProvider2.setWhitelist(providerSet2);
                Set<String> serviceTypeSet2 = null;
                ServiceTypeFilter filterServiceType2 = new ServiceTypeFilter();
                filterServiceType2.setWhitelist(serviceTypeSet2);
                Set<String> statusSet2 = new HashSet<>();
                StatusFilter filterStatus2 = new StatusFilter();
                filterStatus2.setWhitelist(statusSet2);
                collectionOfFilters2.add(filterProvider2);
                collectionOfFilters2.add(filterServiceType2);
                collectionOfFilters2.add(filterStatus2);

                return Stream.of(
                        Arguments.of(collectionOfFilters1),
                        Arguments.of(collectionOfFilters2)
                );
            }

            @DisplayName("when filters are totally or partially null, throws NullPointerException")
            @ParameterizedTest
            @MethodSource("getFiltersNull")
            void nullFilters(List<Filter> filters) {
                setFilters(filters);
                assertThrows(NullPointerException.class, () -> navigationController.getFilteredList());


            }


        }

        @Nested
        @DisplayName("and I use the method buildCompleteList")
        class buildCompleteList {
            @Test
            @DisplayName("with as argument a TrustedListBuilder, should build a complete list")
            void trustedListBuilderAsArgument() throws IOException {
                TrustedListJsonBuilder builder = new TrustedListJsonBuilder();
                Path countries = Path.of("src/test/java/com/trustedservices/navigator/dummyCopyTrustedList/countryListDummy.json");
                Path providers = Path.of("src/test/java/com/trustedservices/navigator/dummyCopyTrustedList/providerListDummy.json");
                builder.setCountriesJson(Files.readString(countries));
                builder.setProvidersJson(Files.readString(providers));
                navigationController.buildCompleteList(builder);
                boolean areListsEquals = navigationController.getCompleteList().equals(builder.build());
                assertTrue(areListsEquals);
            }

            @Test
            @DisplayName("with as argument a null TrustedListBuilder should throw NullPointer Exception")
            void trustedListBuilderNullAsArgument() {
                TrustedListBuilder argumentTrustedListBuilder = null;
                assertThrows(NullPointerException.class, () -> navigationController.buildCompleteList(argumentTrustedListBuilder));
            }
        }
    }
}