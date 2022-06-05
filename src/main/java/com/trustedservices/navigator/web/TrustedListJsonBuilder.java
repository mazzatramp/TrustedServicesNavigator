package com.trustedservices.navigator.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trustedservices.domain.*;

import java.util.*;

public class TrustedListJsonBuilder implements TrustedListBuilder {

    private List<JsonCountry> jsonCountries;
    private List<JsonProvider> jsonProviders;

    private Set<Country> countries;

    private String countriesJsonString;
    private String providersJsonString;

    public TrustedListJsonBuilder() {
        jsonCountries = new ArrayList<>();
        jsonProviders = new ArrayList<>();
        countries = new TreeSet<>();
    }

    public void setCountriesJsonString(String countriesJsonString) {
        this.countriesJsonString = countriesJsonString;
    }

    public void setProvidersJsonString(String providersJsonString) {
        this.providersJsonString = providersJsonString;
    }

    @Override
    public TrustedList build() {
        if (!jsonStringSet())
            throw new IllegalStateException("Json strings not set.");

        readJsonData();
        return getFilledTrustedListFromReadData();
    }

    private TrustedList getFilledTrustedListFromReadData() {
        countries = getCountriesFromReadData();
        fillCountriesWithProviders();
        return new TrustedList(countries);
    }

    private boolean jsonStringSet() {
        return countriesJsonString != null && providersJsonString != null;
    }

    private Set<Country> getCountriesFromReadData() {
        jsonCountries.forEach(jsonCountry -> countries.add(jsonCountry.createCountry()));
        return countries;
    }

    private void readJsonData() {
        try {

            jsonCountries = readJson(countriesJsonString, JsonCountry.class);
            jsonProviders = readJson(providersJsonString, JsonProvider.class);
        } catch (JsonProcessingException e) {
            System.err.println("Error Processing json: " + e.getMessage());

            throw new RuntimeException(e);
        }
    }

    private <T> List<T> readJson(String json, Class<T> type) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(
                json,
                objectMapper.getTypeFactory().constructCollectionType(List.class, type)
        );
    }

    private void fillCountriesWithProviders() {
        for (JsonProvider jsonProvider : jsonProviders) {
            Country itsCountry = getCountryFromCode(jsonProvider.getCountryCode());
            jsonProvider.createProviderIn(itsCountry);
        }
    }

    private Country getCountryFromCode(String countryCode) {
        for (Country country : countries)
            if (country.getCode().equals(countryCode))
                return country;

        throw new RuntimeException("Can't find " + countryCode + " from the countries");
    }
}
