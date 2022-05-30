package com.trustedservices.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trustedservices.domain.Country;
import com.trustedservices.domain.Provider;
import com.trustedservices.domain.TrustedList;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class TrustedListApiBuilder implements TrustedListBuilder {
    private static final String COUNTRIES_API_ENDPOINT = "https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/countries_list_no_lotl_territory";
    private static final String PROVIDERS_API_ENDPOINT = "https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/tsp_list";

    private List<Country> countries;
    private List<Provider> providers;

    @Override
    public TrustedList build(){
        try {
            downloadApiData();
            return new TrustedList(countries);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void downloadApiData() throws IOException {
        countries = buildJSONFromURL(COUNTRIES_API_ENDPOINT, Country.class);
        providers = buildJSONFromURL(PROVIDERS_API_ENDPOINT, Provider.class);
        linkCountriesAndProviders();
    }

    private void linkCountriesAndProviders() {
        for (Provider provider : providers) {
            Country providerCountry = getCountryFromCode(provider.getCountryCode());

            provider.setCountry(providerCountry);
            providerCountry.getProviders().add(provider);
        }
    }

    private <T> List<T> buildJSONFromURL(String endpoint, Class<T> type) throws IOException {
        ObjectMapper jsonToObjectMapper = new ObjectMapper();
        return jsonToObjectMapper.readValue(
                new URL(endpoint),
                jsonToObjectMapper.getTypeFactory().constructCollectionType(List.class, type)
        );
    }

    private Country getCountryFromCode(String countryCode) {
        for (Country country : countries) {
            if (country.getCode().equals(countryCode))
                return country;
        }

        throw new IllegalArgumentException(countryCode);
    }
}
