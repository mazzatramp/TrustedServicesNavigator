package com.trustedservices.navigator.web;

import com.fasterxml.jackson.databind.*;
import com.trustedservices.domain.*;

import java.io.IOException;
import java.net.URL;
import java.util.TreeSet;

public class TrustedListApiBuilder implements TrustedListBuilder {
    private static final String COUNTRIES_API_ENDPOINT = "https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/countries_list_no_lotl_territory";
    private static final String PROVIDERS_API_ENDPOINT = "https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/tsp_list";

    private TreeSet<Country> countries;
    private TreeSet<Provider> providers;

    @Override
    public TrustedList build(){
        downloadApiData();
        linkCountriesAndProviders();
        return new TrustedList(countries);
    }

    private void downloadApiData() {
        countries = getTreeSetFromEndpoint(COUNTRIES_API_ENDPOINT, Country.class);
        providers = getTreeSetFromEndpoint(PROVIDERS_API_ENDPOINT, Provider.class);
    }

    private void linkCountriesAndProviders() {
        for (Provider provider : providers) {
            Country providerCountry = getCountryFromCode(provider.getCountryCode());

            provider.setCountry(providerCountry);
            providerCountry.getProviders().add(provider);
        }
    }

    private <T> TreeSet<T> getTreeSetFromEndpoint(String endpoint, Class<T> type) {
        try {
            ObjectMapper jsonToObjectMapper = new ObjectMapper();
            URL endpointURL = null;
            endpointURL = new URL(endpoint);
            JavaType treeSetCollection = jsonToObjectMapper.getTypeFactory().constructCollectionType(TreeSet.class, type);
            return jsonToObjectMapper.readValue(endpointURL, treeSetCollection);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private Country getCountryFromCode(String countryCode) {
        for (Country country : countries) {
            if (country.getCode().equals(countryCode))
                return country;
        }

        throw new IllegalArgumentException(countryCode);
    }
}
