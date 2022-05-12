package com.tsn.trustedservicesnavigator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TrustedList {
    private static final String COUNTRIES_API_ENDPOINT = "https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/countries_list";
    private static final String PROVIDERS_API_ENDPOINT = "https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/tsp_list";

    private static TrustedList instance;
    private List<Country> countries;

    public TrustedList() {
        this.countries = new ArrayList<>(0);

        try {
            this.fillWithApiData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        List<Country> apiCountries = getApiCountriesData();
        List<Provider> apiProviders = getApiProvidersData();

        fillTrustedListWithCountries(apiCountries);
        fillCountriesWithProviders(apiCountries, apiProviders);
    }

    private void fillCountriesWithProviders(List<Country> apiCountries, List<Provider> apiProviders) {
        for (Provider provider : apiProviders) {
            String providerCountryCode = provider.getCountryCode();

            getCountryFromCode(providerCountryCode).getProviders().add(provider);
        }
    }

    private Country getCountryFromCode(String countryCode) {
        for (Country country : countries) {
            if (country.getCode().equals(countryCode))
                return country;
        }
        return null;
    }


    private void fillTrustedListWithCountries(List<Country> apiCountries) {
        countries = apiCountries;
    }

    private List<Country> getApiCountriesData() throws IOException {
        HttpURLConnection countriesApiConnection = openConnection(COUNTRIES_API_ENDPOINT);
        int responseCode = countriesApiConnection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return buildCountriesFromResponse(countriesApiConnection);
        }
        return new ArrayList<>(0);
    }

    private List<Country> buildCountriesFromResponse(HttpURLConnection countriesApiConnection) throws IOException {
        ObjectMapper jsonToObjectMapper = new ObjectMapper();

        String jsonCountryList = getResponseFromConnection(countriesApiConnection);

        List<Country> countryData = jsonToObjectMapper.readValue(
                jsonCountryList,
                jsonToObjectMapper.getTypeFactory().constructCollectionType(List.class, Country.class)
        );

        return countryData;
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
        }
        return null;
    }

    private List<Provider> getApiProvidersData() throws Exception{

        HttpURLConnection providersApiConnection = openConnection(PROVIDERS_API_ENDPOINT);
        int responseCode = providersApiConnection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return buildProvidersFromResponse(providersApiConnection);
        }

        return null;
    }

    private List<Provider> buildProvidersFromResponse(HttpURLConnection providersApiConnection) throws IOException {
        ObjectMapper jsonToObjectMapper = new ObjectMapper();
        String jsonProvidersList = getResponseFromConnection(providersApiConnection);

        List<Provider> providers = jsonToObjectMapper.readValue(
                jsonProvidersList,
                new TypeReference<List<Provider>>() {}
        );

        return providers;
    }
}