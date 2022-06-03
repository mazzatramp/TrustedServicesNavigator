package com.trustedservices.navigator.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trustedservices.domain.Country;
import com.trustedservices.domain.Provider;
import com.trustedservices.domain.TrustedList;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class TrustedListJsonBuilder implements TrustedListBuilder {

    private Set<Country> countries;
    private Set<Provider> providers;

    private String countriesJson;
    private String providersJson;

    public void setCountriesJson(String countriesJson) {
        this.countriesJson = countriesJson;
    }

    public void setProvidersJson(String providersJson) {
        this.providersJson = providersJson;
    }

    @Override
    public TrustedList build() {
        if (countriesJson == null || providersJson == null)
            throw new IllegalStateException("Json strings not set");

        readJsonData();
        return new TrustedList(countries);
    }

    private void readJsonData() {
        countries = readJson(countriesJson, Country.class);
        providers = readJson(providersJson, Provider.class);
        linkCountriesAndProviders();
    }

    private <T> TreeSet<T> readJson(String json, Class<T> type) {
        try {
            ObjectMapper jsonToObjectMapper = new ObjectMapper();
            return jsonToObjectMapper.readValue(json, jsonToObjectMapper.getTypeFactory().constructCollectionType(TreeSet.class, type));
        } catch (IOException e) {
            System.out.println("Error reading json.");
            throw new RuntimeException(e);
        }
    }

    private void linkCountriesAndProviders() {
        for (Provider provider : providers) {
            Country providerCountry = getCountryFromCode(provider.getCountryCode());

            provider.setCountry(providerCountry);
            providerCountry.getProviders().add(provider);
        }
    }

    private Country getCountryFromCode(String countryCode) {
        for (Country country : countries)
            if (country.getCode().equals(countryCode))
                return country;

        throw new IllegalArgumentException(countryCode);
    }
}
