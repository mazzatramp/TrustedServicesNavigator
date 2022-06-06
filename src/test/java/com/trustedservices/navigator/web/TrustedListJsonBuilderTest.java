package com.trustedservices.navigator.web;

import com.trustedservices.domain.TrustedList;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A TrustedListJsonBuilder")
class TrustedListJsonBuilderTest {
    TrustedListJsonBuilder trustedJsonBuilder;

    @Test
    @DisplayName("is created")
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

            Path countries = Path.of("src/test/java/com/trustedservices/navigator/dummyCopyTrustedList/countryListDummy.json");
            Path providers = Path.of("src/test/java/com/trustedservices/navigator/dummyCopyTrustedList/providerListDummy.json");
            trustedJsonBuilder.setCountriesJson(Files.readString(countries));
            trustedJsonBuilder.setProvidersJson(Files.readString(providers));
        }

        @Test
        @DisplayName("building the trusted list should not throw any exception")
        void buildWithGoodBuilderNoException() {
            assertDoesNotThrow(() -> trustedJsonBuilder.build());
        }
        @Test
        @DisplayName("The trustedList should not be empty")
        void buildWithGoodBuilderNotEmpty() {
            TrustedList trustedList= trustedJsonBuilder.build();
            assertFalse(trustedList.isEmpty());

        }
    }


}
