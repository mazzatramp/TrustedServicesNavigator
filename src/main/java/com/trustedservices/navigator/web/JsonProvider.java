package com.trustedservices.navigator.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustedservices.domain.*;

import java.util.List;

/**
 * Class created to read the attributes of a Provider from a json file and create a new Provider safely  (with this system it's hard
 *  * to modify items of the TrustedList, or add external objects).
 */
public class JsonProvider {
    private int providerId;
    private String countryCode;
    private String name;
    private String trustmark;
    private List<String> serviceTypes;
    private List<JsonService> jsonServices;

    /**
     * @param country creates a new provider inside a country, then uses the method createServiceIn to create a list of
     * services inside it
     * @see JsonCountry
     * @see JsonService
     */
    public void createProviderIn(Country country) {
        Provider createdProvider = new Provider(country, providerId, name, trustmark);
        createdProvider.getServiceTypes().addAll(serviceTypes);
        this.jsonServices.forEach(jsonService -> jsonService.createServiceIn(createdProvider));
        country.getProviders().add(createdProvider);
    }

    /**
     * Reads the parameters from the json property to identify the attributes of a provider and constructs a new JsonProvider
     */
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
