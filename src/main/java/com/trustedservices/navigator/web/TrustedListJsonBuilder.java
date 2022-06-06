package com.trustedservices.navigator.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trustedservices.domain.*;

import java.util.*;

/**
 * This class builds the TrustedList using
 * @see JsonCountry
 * @see JsonProvider
 * @see JsonService
 * and gets the data by reading a string of json data
 */
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

    /**
     * @return checks that the strings have been set and returns a boolean to state it
     */
    private boolean jsonStringSet() {
        return countriesJsonString != null && providersJsonString != null;
    }

    private Set<Country> getCountriesFromReadData() {
        jsonCountries.forEach(jsonCountry -> countries.add(jsonCountry.createCountry()));
        return countries;
    }

    /**
     * This method casts another method, readJson, and builds two lists, jsonCountries and jsonProviders
     */
    private void readJsonData() {
        try {

            jsonCountries = readJson(countriesJsonString, JsonCountry.class);
            jsonProviders = readJson(providersJsonString, JsonProvider.class);
        } catch (JsonProcessingException e) {
            System.err.println("Error Processing json: " + e.getMessage());

            throw new RuntimeException(e);
        }
    }

    /**
     * @param json reads a string of json data that processes to build
     * @param <T> a given class
     * @return an object of the given class
     * @throws JsonProcessingException
     * if the data has not the correct format, which is set in the constructor of the given class
     */
    private <T> List<T> readJson(String json, Class<T> type) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(
                json,
                objectMapper.getTypeFactory().constructCollectionType(List.class, type)
        );
    }

    /**
     * method that associates each JsonCountry with its JsonProviders, given the country code of both
     */
    private void fillCountriesWithProviders() {
        for (JsonProvider jsonProvider : jsonProviders) {
            try {
                Country itsCountry = getCountryFromCode(jsonProvider.getCountryCode());
                jsonProvider.createProviderIn(itsCountry);
                } catch (IllegalArgumentException exception){
                    System.err.println("Can't find a country code to match the country");
                    throw new RuntimeException();
                    }
        }
    }

    private Country getCountryFromCode(String countryCode) {
        for (Country country : countries)
            if (country.getCode().equals(countryCode))
                return country;

        throw new IllegalArgumentException();
    }
}
