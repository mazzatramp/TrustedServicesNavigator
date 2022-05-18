package com.tsn.trustedservicesnavigator;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TrustedList implements Cloneable {
    private static final String COUNTRIES_API_ENDPOINT = "https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/countries_list_no_lotl_territory";
    private static final String PROVIDERS_API_ENDPOINT = "https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/tsp_list";

    private List<Country> countries;

    public TrustedList() {
        this.countries = new ArrayList<>(0);
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void downloadApiData() throws IOException {
        countries = buildJSONFromURL(COUNTRIES_API_ENDPOINT, Country.class);
        List<Provider> apiProviders = buildJSONFromURL(PROVIDERS_API_ENDPOINT, Provider.class);
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

    private <T> List<T> buildJSONFromURL(String endpoint, Class<T> type) throws IOException {
        ObjectMapper jsonToObjectMapper = new ObjectMapper();
        return jsonToObjectMapper.readValue(
                new URL(endpoint),
                jsonToObjectMapper.getTypeFactory().constructCollectionType(List.class, type)
        );
    }

    @Override
    public TrustedList clone() {
        try {
            TrustedList clone = (TrustedList) super.clone();
            clone.countries = new ArrayList<>();
            this.countries.forEach(country -> clone.getCountries().add(country.clone()));
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}