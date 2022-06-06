package com.trustedservices.navigator;

import com.trustedservices.domain.TrustedList;
import com.trustedservices.navigator.filters.ProviderFilter;
import com.trustedservices.navigator.filters.ServiceTypeFilter;
import com.trustedservices.navigator.filters.StatusFilter;
import com.trustedservices.navigator.web.TrustedListBuilder;
import com.trustedservices.navigator.web.TrustedListJsonBuilder;
import org.junit.jupiter.api.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

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

        @Test
        @DisplayName("and I use the method getFilteredList after setting filters and building a complete list should return filtered list")
        void getFilteredList() throws IOException {
            TrustedListJsonBuilder builder = new TrustedListJsonBuilder();
            Path countries = Path.of("src/test/java/com/trustedservices/navigator/dummyCopyTrustedList/countryListDummy.json");
            Path providers = Path.of("src/test/java/com/trustedservices/navigator/dummyCopyTrustedList/providerListDummy.json");
            builder.setCountriesJsonString(Files.readString(countries));
            builder.setProvidersJsonString(Files.readString(providers));
            navigationController.buildCompleteList(builder);

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
            navigationController.getFilters().add(filterProvider);
            navigationController.getFilters().add(filterServiceType);
            navigationController.getFilters().add(filterStatus);

            Set<String> providersExpected = navigationController.getFilters().get(0).getWhitelist();
            Set<String> expectedServiceTypes = navigationController.getFilters().get(1).getWhitelist();
            Set<String> expectedStatuses = navigationController.getFilters().get(2).getWhitelist();
            TrustedList filteredList = navigationController.getFilteredList();
            filteredList.getCountries().forEach(country -> {
                country.getProviders().forEach(provider -> {
                    assertTrue(providersExpected.contains(country.getName() + "/" + provider.getName()));
                    provider.getServices().forEach(service -> {
                        assertTrue(service.getServiceTypes().stream().toList().stream().anyMatch(servizio -> expectedServiceTypes.contains(servizio)));
                        assertTrue(expectedStatuses.contains(service.getStatus()));
                    });
                });
            });
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
                builder.setCountriesJsonString(Files.readString(countries));
                builder.setProvidersJsonString(Files.readString(providers));
                navigationController.buildCompleteList(builder);
                System.out.println(navigationController.getCompleteList().getCountries());
                System.out.println(builder.build().getCountries());
                boolean areListsEquals = navigationController.getCompleteList().equals(builder.build());
                assertTrue(areListsEquals);
            }
            @Test
            @DisplayName("with as argument a null TrustedListBuilder should throw NullPointer Exception")
            void trustedListBuilderNullAsArgument(){
                TrustedListBuilder argumentTrustedListBuilder= null;
                assertThrows(NullPointerException.class, () -> navigationController.buildCompleteList(argumentTrustedListBuilder));
            }
        }
    }
}