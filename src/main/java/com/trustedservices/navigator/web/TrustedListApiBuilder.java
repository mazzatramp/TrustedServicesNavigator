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
        String countriesJson = getResponse(COUNTRIES_API_ENDPOINT);
        String providersJson = getResponse(PROVIDERS_API_ENDPOINT);
        super.setCountriesJson(countriesJson);
        super.setProvidersJson(providersJson);
        return super.build();
    }

    private String getResponse(String endpoint) {
        try {
            URL apiUrl = new URL(endpoint);
            HttpsURLConnection connection = (HttpsURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = reader.readLine()) != null) {
                    content.append(inputLine);
                }
                reader.close();
                connection.disconnect();

                return content.toString();
            } else {
                return "";
            }
        } catch (IOException e) {
            System.err.println("Error downloading file from " + endpoint);
            throw new RuntimeException(e);
        }
    }
}
