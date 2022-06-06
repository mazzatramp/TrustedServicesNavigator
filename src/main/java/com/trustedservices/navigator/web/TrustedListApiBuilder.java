package com.trustedservices.navigator.web;

import com.trustedservices.domain.TrustedList;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.URL;

public class TrustedListApiBuilder extends TrustedListJsonBuilder {
    private static final String COUNTRIES_API_ENDPOINT = "https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/countries_list_no_lotl_territory";
    private static final String PROVIDERS_API_ENDPOINT = "https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/tsp_list";

    /**
     * This constructor uses the method readJsonFromUrl, then sets the string to build the list, then uses the super class constructor
     * method to build the
     * @return TrustedList
     */
    @Override
    public TrustedList build() {
        String countriesJson = readJsonFromUrl(COUNTRIES_API_ENDPOINT);
        String providersJson = readJsonFromUrl(PROVIDERS_API_ENDPOINT);
        super.setCountriesJson(countriesJson);
        super.setProvidersJson(providersJson);
        return super.build();
    }

    private String readJsonFromUrl(String endpoint) {
        try {
            return getResponse(endpoint);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getResponse(String endpoint) throws IOException {
        HttpsURLConnection apiConnection = getHttpsURLConnection(endpoint);
        apiConnection.connect();

        String content = readResponse(apiConnection.getInputStream());

        apiConnection.disconnect();
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
