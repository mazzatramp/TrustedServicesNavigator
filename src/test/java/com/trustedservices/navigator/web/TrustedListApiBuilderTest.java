package com.trustedservices.navigator.web;

import com.trustedservices.domain.TrustedList;
import org.junit.jupiter.api.*;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A TrustedListApiBuilder")
class TrustedListApiBuilderTest {
    TrustedListApiBuilder trustedListApiBuilder;

    @Test
    @DisplayName("is created with TrustedListApiBuilder()")
    void constructorTest() {
        new TrustedListApiBuilder();
    }

    @Nested
    @DisplayName("when new")
    class WhenNew {

        @BeforeEach
        void createAnEmptyApiBuilder() {
            trustedListApiBuilder = new TrustedListApiBuilder();
        }

        @Test
        @DisplayName("using an empty builder should throw NullPointerException")
        void useEmptyApiBuilder() {
            trustedListApiBuilder = null;
            assertThrows(NullPointerException.class, () -> trustedListApiBuilder.build());
        }

        @Disabled // can be disabled because it is a time-consuming test.
        @Nested
        @DisplayName("when set up with good json data")
        class WhenSetUp {

            @BeforeEach
            void createAndSetupApiBuilder() {
                final String COUNTRIES_API_ENDPOINT = "https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/countries_list_no_lotl_territory";
                final String PROVIDERS_API_ENDPOINT = "https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/tsp_list";
                String countriesJson = readJsonFromUrl(COUNTRIES_API_ENDPOINT);
                String providersJson = readJsonFromUrl(PROVIDERS_API_ENDPOINT);
                trustedListApiBuilder.setCountriesJson(countriesJson);
                trustedListApiBuilder.setProvidersJson(providersJson);
            }

            @Test
            @DisplayName("building the trusted list should not throw any exception")
            void buildWithGoodBuilderNoException() {
                assertDoesNotThrow(() -> trustedListApiBuilder.build());
            }

            @Test
            @DisplayName("The trustedList built should not be null")
            void buildWithGoodBuilderNotNullList() {
                TrustedList trustedList = trustedListApiBuilder.build();
                assertNotNull(trustedList);

            }

        }

        private String readJsonFromUrl(String endpoint) {
            try {
                return getResponse(endpoint);
            } catch (IOException e) {
                System.err.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }

        private String getResponse(String endpoint) throws IOException {
            HttpsURLConnection connection = getHttpsURLConnection(endpoint);
            connection.connect();

            if (connection.getResponseCode() != HttpsURLConnection.HTTP_OK)
                throw new ConnectException("Connection response code: " + connection.getResponseMessage());

            String content = readResponse(connection.getInputStream());

            connection.disconnect();
            return content;
        }

        private String readResponse(InputStream response) throws IOException {
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(response));
            StringBuilder responseBuilder = new StringBuilder();
            responseReader.lines().forEach(responseBuilder::append);
            responseReader.close();
            return responseBuilder.toString();
        }

        private HttpsURLConnection getHttpsURLConnection(String endpoint) throws IOException {
            URL apiUrl = new URL(endpoint);
            HttpsURLConnection connection = (HttpsURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");

            return connection;
        }
    }
}
