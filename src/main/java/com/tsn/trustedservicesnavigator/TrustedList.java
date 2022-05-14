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
import java.util.Arrays;
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
        countries = buildCountriesFromURL(COUNTRIES_API_ENDPOINT);
        List<Provider> apiProviders = buildProvidersFromURL(PROVIDERS_API_ENDPOINT);
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

    private List<Country> buildCountriesFromURL(String endpoint) throws IOException {
        ObjectMapper jsonToObjectMapper = new ObjectMapper();
        return jsonToObjectMapper.readValue(new URL(endpoint), new TypeReference<>(){});
    }

    private List<Provider> buildProvidersFromURL(String endpoint) throws IOException {
        ObjectMapper jsonToObjectMapper = new ObjectMapper();
        return jsonToObjectMapper.readValue(new URL(endpoint), new TypeReference<>(){});
    }
}