package com.trustedservices.domain;

import com.fasterxml.jackson.annotation.*;

import java.util.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Provider implements Cloneable, Comparable<Provider>, TrustedListEntity {
    private Country country;

    private int providerId;
    private String name;
    private String trustmark;
    private Set<String> serviceTypes;
    private Set<Service> services;

    @JsonCreator
    public Provider(
            @JsonProperty("countryCode") String countryCode,
            @JsonProperty("tspId") int providerId,
            @JsonProperty("name") String name,
            @JsonProperty("trustmark") String trustmark,
            @JsonProperty("qServiceTypes") Set<String> serviceTypes,
            @JsonProperty("services") Set<Service> services
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
    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getInformation() {
        List<String> information = new ArrayList<>(3);
        information.add(name);
        information.add(serviceTypes.toString());
        return information;
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

    public Set<String> getServiceTypes() {
        return serviceTypes;
    }

    public void setServiceTypes(Set<String> serviceTypes) {
        this.serviceTypes = serviceTypes;
    }

    public Set<Service> getServices() {
        return services;
    }

    public void setServices(Set<Service> services) {
        this.services = services;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Provider provider = (Provider) o;
        return Objects.equals(this.name, provider.name)
                && Objects.equals(this.country, provider.country);
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
            providerClone.setServices(new TreeSet<>());
            providerClone.setCountry(country);
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

    @Override
    public int compareTo(Provider provider) {
        return this.name.compareTo(provider.name);
    }
}
