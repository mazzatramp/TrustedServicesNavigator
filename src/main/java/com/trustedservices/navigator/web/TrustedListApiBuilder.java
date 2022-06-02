package com.trustedservices.navigator.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trustedservices.domain.Country;
import com.trustedservices.domain.Provider;
import com.trustedservices.domain.TrustedList;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class TrustedListApiBuilder extends TrustedJsonBuilder {
    private static final String COUNTRIES_API_ENDPOINT = "https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/countries_list_no_lotl_territory";
    private static final String PROVIDERS_API_ENDPOINT = "https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/tsp_list";

   private List<Country> countries;
    private List<Provider> providers;

    @Override
    public TrustedList build(){
        try {
            buildApiData();
            return new TrustedList(countries);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public File downloadFile (URL url, String filename) throws IOException {
        FileUtils.copyURLToFile(url, new File(filename));
        return new File(filename);
    }
    private void buildApiData() throws IOException {
        File CountryList=downloadFile(new URL(COUNTRIES_API_ENDPOINT), "countryList");
        File ProviderList=downloadFile(new URL(PROVIDERS_API_ENDPOINT), "providerList");
        countries = readJson(CountryList, Country.class);
        providers = readJson(ProviderList, Provider.class);
        linkCountriesAndProviders();
    }

    private <T> List<T> readJson(File file, Class<T> type) throws IOException {
        ObjectMapper jsonToObjectMapper = new ObjectMapper();
        return jsonToObjectMapper.readValue(file, jsonToObjectMapper.getTypeFactory().constructCollectionType(List.class, type));
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
