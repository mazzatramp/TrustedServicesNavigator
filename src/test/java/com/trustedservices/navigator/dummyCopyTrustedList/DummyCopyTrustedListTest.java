package com.trustedservices.navigator.dummyCopyTrustedList;

import com.trustedservices.domain.TrustedList;
import com.trustedservices.navigator.web.TrustedListApiBuilder;
import com.trustedservices.navigator.web.TrustedListJsonBuilder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DummyCopyTrustedListTest {
    //This class do not test proper application code. It tests whether the complete dummy trusted list, generated from countryListDummy.json
    // and providerListDummy.json, is not kept up to date.
    //This can be an interesting finding because a lot of tests use the complete dummy trusted list. This is because the application code proper use of
    //the whole trusted list is a very common scenario, thus we want this scenario to be as tested as possible.
    @Disabled
    @Test
    @DisplayName("If the complete dummy trusted list is wanted to be up to date with the API, the trustedList built from APIBuilder should be the same as the one built with the JsonBuilder from countryListDummy and providerListDummy")
    void equalListsFromDifferentBuilders() throws IOException {

        TrustedListApiBuilder trustedListApiBuilder = new TrustedListApiBuilder();
        final String COUNTRIES_API_ENDPOINT = "https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/countries_list_no_lotl_territory";
        final String PROVIDERS_API_ENDPOINT = "https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/tsp_list";
        String countriesJson = readJsonFromUrl(COUNTRIES_API_ENDPOINT);
        String providersJson = readJsonFromUrl(PROVIDERS_API_ENDPOINT);
        trustedListApiBuilder.setCountriesJson(countriesJson);
        trustedListApiBuilder.setProvidersJson(providersJson);
        TrustedList trustedListAPI = trustedListApiBuilder.build();

        TrustedListJsonBuilder trustedJsonBuilder = new TrustedListJsonBuilder();
        Path countries = Path.of("src/test/java/com/trustedservices/navigator/dummyCopyTrustedList/countryListDummy.json");
        Path providers = Path.of("src/test/java/com/trustedservices/navigator/dummyCopyTrustedList/providerListDummy.json");
        trustedJsonBuilder.setCountriesJson(Files.readString(countries));
        trustedJsonBuilder.setProvidersJson(Files.readString(providers));
        TrustedList trustedListDummy = trustedJsonBuilder.build();

        boolean areListsEqual = trustedListDummy.equals(trustedListAPI);
        assertTrue(areListsEqual, "JSON dummy files with the complete list are not up to date with the API");

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
