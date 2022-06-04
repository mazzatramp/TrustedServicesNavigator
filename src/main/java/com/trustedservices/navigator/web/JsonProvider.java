package com.trustedservices.navigator.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustedservices.domain.*;

import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

public class JsonProvider {
    private int providerId;
    private String countryCode;
    private String name;
    private String trustmark;
    private List<String> serviceTypes;
    private List<JsonService> jsonServices;

    public void createProviderInside(Country country) {
        HashSet<String> serviceTypesHashSet = new HashSet<>(serviceTypes);
        TreeSet<Service> services = new TreeSet<>();

        jsonServices.forEach(jsonService -> services.add(jsonService.createService()));

        Provider provider = new Provider(providerId, name, trustmark, serviceTypesHashSet, services);

        country.getProviders().add(provider);
        provider.setCountry(country);
        services.forEach(service -> service.setProvider(provider));
    }

    @JsonCreator
    public JsonProvider(
            @JsonProperty("countryCode") String countryCode,
            @JsonProperty("tspId") int providerId,
            @JsonProperty("name") String name,
            @JsonProperty("trustmark") String trustmark,
            @JsonProperty("qServiceTypes") List<String> serviceTypes,
            @JsonProperty("services") List<JsonService> jsonServices
    ){
        this.countryCode = countryCode;
        this.providerId = providerId;
        this.name = name;
        this.trustmark = trustmark;
        this.serviceTypes = serviceTypes;
        this.jsonServices = jsonServices;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrustmark() {
        return trustmark;
    }

    public void setTrustmark(String trustmark) {
        this.trustmark = trustmark;
    }

    public List<String> getServiceTypes() {
        return serviceTypes;
    }

    public void setServiceTypes(List<String> serviceTypes) {
        this.serviceTypes = serviceTypes;
    }

    public List<JsonService> getJsonServices() {
        return jsonServices;
    }

    public void setJsonServices(List<JsonService> jsonServices) {
        this.jsonServices = jsonServices;
    }
}
