package com.tsn.trustedservicesnavigator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TrustedList {
    private static final String COUNTRIES_API_ENDPOINT = "https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/countries_list";
    private static final String PROVIDERS_API_ENDPOINT = "https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/tsp_list";

    private static TrustedList instance;
    private List<Country> countries;

    private TrustedList() {
        this.countries = new ArrayList<>(0);
    }

    public static TrustedList getInstance() {
        if (instance == null) {
            instance = new TrustedList();
        }
        return instance;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void fillWithApiData() throws Exception {
        countries = getApiCountriesData();
        List<Provider> apiProviders = getApiProvidersData();

        linkCountriesAndProviders(apiProviders);
    }

    private void linkCountriesAndProviders(List<Provider> providersToLink) {
        for (Provider provider : providersToLink) {
            Country providerCountry = getCountryFromCode(provider.getCountryCode());

            provider.setCountry(providerCountry);
            providerCountry.getProviders().add(provider);
        }
    }

    private Country getCountryFromCode(String countryCode) {
        for (Country country : countries) {
            if (country.getCode().equals(countryCode))
                return country;
        }

        throw new IllegalArgumentException(countryCode);
    }

    private List<Country> getApiCountriesData() throws IOException {
        HttpURLConnection countriesApiConnection = openConnection(COUNTRIES_API_ENDPOINT);
        int responseCode = countriesApiConnection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return buildCountriesFromResponse(countriesApiConnection);
        }

        throw new ConnectException("Failed connection to the api, response code: " + responseCode);
    }

    private List<Provider> getApiProvidersData() throws Exception{

        HttpURLConnection providersApiConnection = openConnection(PROVIDERS_API_ENDPOINT);
        int responseCode = providersApiConnection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return buildProvidersFromResponse(providersApiConnection);
        }

        throw new ConnectException("Failed connection to the api, response code: " + responseCode);
    }

    private List<Country> buildCountriesFromResponse(HttpURLConnection countriesApiConnection) throws IOException {
        ObjectMapper jsonToObjectMapper = new ObjectMapper();

        String jsonCountryList = getResponseFromConnection(countriesApiConnection);

        return jsonToObjectMapper.readValue(jsonCountryList, new TypeReference<>(){});
    }

    private List<Provider> buildProvidersFromResponse(HttpURLConnection providersApiConnection) throws IOException {
        ObjectMapper jsonToObjectMapper = new ObjectMapper();
        String jsonProvidersList = getResponseFromConnection(providersApiConnection);

        return jsonToObjectMapper.readValue(jsonProvidersList, new TypeReference<>(){});
    }

    private String getResponseFromConnection(HttpURLConnection apiConnection) throws IOException {
        String responseLine;
        BufferedReader responseReader = new BufferedReader(new InputStreamReader(apiConnection.getInputStream()));
        StringBuilder response = new StringBuilder();

        while ((responseLine = responseReader.readLine()) != null) {
            response.append(responseLine);
        };

        return response.toString();
    }

    private HttpURLConnection openConnection(String apiEndpoint) {
        try {
            URL apiUrl = new URL(apiEndpoint);
            HttpURLConnection apiConnection = (HttpURLConnection) apiUrl.openConnection();
            apiConnection.setRequestMethod("GET");
            apiConnection.setRequestProperty("User-Request", "Mozilla 5.0");
            return apiConnection;
        } catch (Exception e) {
            System.out.println("Unable to connect with " + apiEndpoint);
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
    }
}