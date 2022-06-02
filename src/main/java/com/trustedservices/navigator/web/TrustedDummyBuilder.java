package com.trustedservices.navigator.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trustedservices.domain.Country;
import com.trustedservices.domain.Provider;
import com.trustedservices.domain.TrustedList;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TrustedDummyBuilder implements TrustedListBuilder{
    private static final String COUNTRIES_DUMMY_ENDPOINT= "src/test/java/com/trustedservices/navigator/dummyCopyTrustedList/countryListDummy.json";
     private static final String PROVIDERS_DUMMY_ENDPOINT= "src/test/java/com/trustedservices/navigator/dummyCopyTrustedList/providerListDummy.json";

    private List<Country> countries;
    private List<Provider> providers;
    @Override
    public TrustedList build() {
    try {
        buildDataFromJson();
        return new TrustedList(countries);
    }catch (Exception j) {
        System.err.println("Impossibile leggere json");
        }
        throw new RuntimeException();
    }
    public void buildDataFromJson() throws IOException {
        try {
            countries = readJson(COUNTRIES_DUMMY_ENDPOINT, Country.class);
            providers = readJson(PROVIDERS_DUMMY_ENDPOINT, Provider.class);
            linkCountriesAndProviders();
        }catch(Exception e) {
            System.err.println("Impossibile leggere json");
            throw new RuntimeException(e);
        }
    }

    private <T> List<T> readJson(String endpoint, Class<T> type) throws IOException {
        ObjectMapper jsonToObjectMapper = new ObjectMapper();
        return jsonToObjectMapper.readValue(new File(endpoint), jsonToObjectMapper.getTypeFactory().constructCollectionType(List.class, type));
    }

    private void linkCountriesAndProviders() {
        for (Provider provider : providers) {
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
}
