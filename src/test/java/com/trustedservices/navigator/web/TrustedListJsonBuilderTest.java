package com.trustedservices.navigator.web;

import com.trustedservices.domain.Country;
import com.trustedservices.domain.Provider;
import com.trustedservices.domain.Service;
import com.trustedservices.domain.TrustedList;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A TrustedListJsonBuilder")
class TrustedListJsonBuilderTest {
    TrustedListJsonBuilder trustedJsonBuilder;

    @Test
    @DisplayName("is created with TrustedListJsonBuilder()")
    void isInstantiatedWithNewClass() {
        new TrustedListJsonBuilder();
    }

    @Nested
    @DisplayName("when new")
    class WhenNew {

        @BeforeEach
        void createAnEmptyJsonBuilder() {
            trustedJsonBuilder = new TrustedListJsonBuilder();
        }

        @Test
        @DisplayName("using an empty builder should throw IllegalStateException")
        void useEmptyJsonBuilder() {
            assertThrows(IllegalStateException.class, () -> trustedJsonBuilder.build());
        }
    }

    @Nested
    @DisplayName("when set up with good json data")
    class WhenSetUp {

        @BeforeEach
        void createAndSetupJsonBuilder() throws IOException {
            trustedJsonBuilder = new TrustedListJsonBuilder();

            Path countries = Path.of("src/test/java/com/trustedservices/navigator/dummyCopyTrustedList/partialCountryListDummy.json");
            Path providers = Path.of("src/test/java/com/trustedservices/navigator/dummyCopyTrustedList/partialProviderListDummy.json");
            trustedJsonBuilder.setCountriesJson(Files.readString(countries));
            trustedJsonBuilder.setProvidersJson(Files.readString(providers));
        }

        @Test
        @DisplayName("building the trusted list should not throw any exception")
        void buildWithGoodBuilderNoException() {
            assertDoesNotThrow(() -> trustedJsonBuilder.build());
        }

        @Test
        @DisplayName("the trusted list built should not be null")
        void builtTrustedListShouldNotBeNull() {
            TrustedList trustedList = trustedJsonBuilder.build();
            assertNotNull(trustedList);
        }

        @Test
        @DisplayName("the trusted list built should be equal to the trusted list with the values from the JSON")
        void builtRightTrustedList() {
            TrustedList trustedList = trustedJsonBuilder.build();

            TreeSet<Country> listOfCountries = new TreeSet<>();
            Country country1 = new Country("Austria", "AT");
            TreeSet<String> serviceTypesDatakom = new TreeSet<>();
            serviceTypesDatakom.add("QCertESig");
            TreeSet<String> serviceTypesA_sign_premium_CA = new TreeSet<>();
            serviceTypesA_sign_premium_CA.add("QCertESig");
            TreeSet<Service> servicesDatakom = new TreeSet<>();
            Provider Datakom_Austria_GmbH = new Provider(country1, 3, "Datakom Austria GmbH", "VATAT-U44837307", serviceTypesDatakom, servicesDatakom);
            Service A_sign_premium_CA = new Service(Datakom_Austria_GmbH, 1, "a-sign Premium CA", "http://uri.etsi.org/TrstSvc/Svctype/CA/QC", "withdrawn", serviceTypesA_sign_premium_CA);
            servicesDatakom.add(A_sign_premium_CA);
            country1.getProviders().add(Datakom_Austria_GmbH);

            Country country2 = new Country("Belgium", "Be");
            TreeSet<String> serviceTypesConnective = new TreeSet<>();
            serviceTypesConnective.add("QValQESig");
            serviceTypesConnective.add("QValQESeal");
            TreeSet<String> serviceTypesConnective_Validation_Service = new TreeSet<>();
            serviceTypesConnective_Validation_Service.add("QValQESig");
            serviceTypesConnective_Validation_Service.add("QValQESeal");
            TreeSet<Service> servicesConnective = new TreeSet<>();
            Provider Connective = new Provider(country2, 13, "CONNECTIVE", "VATBE-0467046486", serviceTypesConnective, servicesConnective);
            Service Connective_Validation_Service = new Service(Connective, 1, "Connective Validation Service", "http://uri.etsi.org/TrstSvc/Svctype/QESValidation/Q", "granted", serviceTypesConnective_Validation_Service);
            servicesConnective.add(Connective_Validation_Service);
            country2.getProviders().add(Connective);
            listOfCountries.add(country1);
            listOfCountries.add(country2);
            TrustedList testTrustedList = new TrustedList(listOfCountries);
            testTrustedList.updateServiceTypesAndStatuses();

            assertEquals(trustedList, testTrustedList);

        }
    }

}
