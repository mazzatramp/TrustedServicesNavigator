package com.tsn.trustedservicesnavigator;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class TrustedList implements Cloneable {
    private static final String COUNTRIES_API_ENDPOINT = "https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/countries_list_no_lotl_territory";
    private static final String PROVIDERS_API_ENDPOINT = "https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/tsp_list";

    private final Set<String> serviceTypes;
    private final Set<String> statuses;

    private List<Country> countries;

    public TrustedList() {
        this.countries = new ArrayList<>(0);
        this.serviceTypes = new HashSet<>(0);
        this.statuses = new HashSet<>(0);
    }
    public TrustedList(List<Country> countries) {
        this(); //chiamata al primo costruttore
        this.countries=countries;
        this.fillServiceTypesAndStatuses();;
    }
    public List<Country> getCountries() {
        return countries;
    }

    public void downloadApiData() throws IOException {
        countries = buildJSONFromURL(COUNTRIES_API_ENDPOINT, Country.class);
        List<Provider> apiProviders = buildJSONFromURL(PROVIDERS_API_ENDPOINT, Provider.class);
        linkCountriesAndProviders(apiProviders);
        fillServiceTypesAndStatuses();
    }

    public void fillServiceTypesAndStatuses() {
        statuses.clear();
        serviceTypes.clear();
        countries.forEach(country -> {
            //System.out.println(country);
            country.getProviders().forEach(provider -> {
                //System.out.println(provider);
                provider.getServices().forEach(service -> {
                   // System.out.println("ecco gli statuses che aggiungo " + service.getStatus());
                   // System.out.println("ecco i serviceTypes che aggiungo " + service.getServiceTypes());
                    serviceTypes.addAll(service.getServiceTypes());
                    statuses.add(service.getStatus());
                });
            });
        });
        //System.out.println(statuses);
        //System.out.println(serviceTypes);
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
            this.countries.forEach(
                    country -> clone.getCountries().add(country.clone())
            );
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<String> getServiceTypes() {
        return serviceTypes;
    }
    public Set<String> getStatuses() {
        return statuses;
    }

    //VOGLIAMO DAVVERO CHE EQUALS SI COMPORTI COSI?
    @Override
    public boolean equals(Object trustedList ) {
        if (this == trustedList) return true;
        if (trustedList == null || getClass() != trustedList.getClass()) return false;
        TrustedList list = (TrustedList) trustedList;
        // return Objects.equals(countries, list.countries);
        return (this.getCountries().equals(list.getCountries())
                && this.getServiceTypes().equals(list.getServiceTypes())
                && this.getStatuses().equals(((list).getStatuses())));
    }

    //A CHE SERVE?
    @Override
    public int hashCode() {
        return Objects.hash(countries);
    }
}