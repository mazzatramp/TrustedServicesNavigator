package com.trustedservices.navigator.web;

import com.trustedservices.domain.TrustedList;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.URL;

public class TrustedListApiBuilder extends TrustedListJsonBuilder {
    private static final String COUNTRIES_API_ENDPOINT = "https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/countries_list_no_lotl_territory";
    private static final String PROVIDERS_API_ENDPOINT = "https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/tsp_list";

    @Override
    public TrustedList build() {
        String countriesJson = getResponseFromUrl(COUNTRIES_API_ENDPOINT);
        String providersJson = getResponseFromUrl(PROVIDERS_API_ENDPOINT);
        super.setCountriesJsonString(countriesJson);
        super.setProvidersJsonString(providersJson);
        return super.build();
    }

    private String getResponseFromUrl(String endpoint) {
        try {
            return getContent(endpoint);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private String getContent(String endpoint) throws IOException {
        HttpsURLConnection connection = getHttpsURLConnection(endpoint);
        connection.connect();

        if (connection.getResponseCode() != HttpsURLConnection.HTTP_OK)
            throw new ConnectException();

        BufferedReader contentReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        StringBuilder content = new StringBuilder();
        contentReader.lines().forEach(content::append);
        contentReader.close();

        connection.disconnect();
        return content.toString();
    }

    private HttpsURLConnection getHttpsURLConnection(String endpoint) throws IOException {
        URL apiUrl = new URL(endpoint);
        HttpsURLConnection connection = (HttpsURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("GET");

        return connection;
    }
}
