package com.trustedservices.navigator.web;

import com.trustedservices.TestTrustedList;

import com.trustedservices.domain.TrustedList;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    @DisplayName("when new") //here the builder is tested without json data
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

            Path countries = Paths.get("src/test/java/com/trustedservices/navigator/dummyCopyTrustedList/partialCountryListDummy.json");
            Path providers = Paths.get("src/test/java/com/trustedservices/navigator/dummyCopyTrustedList/partialProviderListDummy.json");

            StringBuilder content = new StringBuilder();
            Files.lines(countries).forEach(line -> content.append(line).append("\n"));
            trustedJsonBuilder.setCountriesJson(content.toString());

            Files.lines(providers).forEach(line -> content.append(line).append("\n"));
            trustedJsonBuilder.setProvidersJson(content.toString());
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
            TrustedList testTrustedList = TestTrustedList.getTrustedListWith("RealCountryWithOneRealProviderWitAllServices1",
                    "RealCountryWithOneRealProviderWitAllServices2");
            assertEquals(trustedList, testTrustedList);

        }
    }

}
