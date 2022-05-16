package com.tsn.trustedservicesnavigator;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Provider implements Cloneable {
    private Country country;

    private int providerId;
    private String name;
    private String trustmark;
    private List<String> serviceTypes;
    private List<Service> services;

    @JsonCreator
    public Provider(
            @JsonProperty("countryCode") String countryCode,
            @JsonProperty("tspId") int providerId,
            @JsonProperty("name") String name,
            @JsonProperty("trustmark") String trustmark,
            @JsonProperty("qServiceTTypes") List<String> serviceTypes,
            @JsonProperty("services") List<Service> services
    ){
        this.country = new Country("", countryCode);
        this.providerId = providerId;
        this.name = name;
        this.trustmark = trustmark;
        this.serviceTypes = serviceTypes;
        this.services = services;
        for (Service service : services) {
            service.setProvider(this);
        }
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getCountryCode() {
        return country.getCode();
    }

    public void setCountryCode(String countryCode) {
        this.country = new Country("", countryCode);
    }

    public int getProviderId() {
        return providerId;
    }
    public void setProviderId(int providerId) {
        this.providerId = providerId;
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

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Provider provider = (Provider) o;
        return providerId == provider.providerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(providerId);
    }

    @Override
    public String toString() {
        return "Provider{" +
                "name='" + name + '\'' +
                ", trustmark='" + trustmark + '\'' +
                '}';
    }

    @Override
    public Provider clone() {
        try {
            Provider providerClone = (Provider) super.clone();
            providerClone.setServices(new ArrayList<>());
            providerClone.setCountry(null);
            this.getServices().forEach(
                    service -> {
                        Service serviceClone = service.clone();
                        serviceClone.setProvider(providerClone);
                        providerClone.getServices().add(serviceClone);
                    });
            return providerClone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
