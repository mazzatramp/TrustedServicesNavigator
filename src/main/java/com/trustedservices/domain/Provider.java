package com.trustedservices.domain;

import java.util.*;

public class Provider implements Cloneable, Comparable<Provider>, TrustedListEntity {
    private final int providerId;
    private final String name;
    private final String trustmark;
    private final Set<String> serviceTypes;

    private Country country;
    private Set<Service> services;

    public Provider(int providerId, String name, String trustmark,
                    Set<String> serviceTypes, Set<Service> services
    ){
        this.providerId = providerId;
        this.name = name;
        this.trustmark = trustmark;
        this.serviceTypes = serviceTypes;
        this.services = services;

        this.country = new Country();
    }

    public Provider() {
        this(0, "", "", new HashSet<>(), new TreeSet<>());
    }

    public Provider(int providerId, String name, String trustmark) {
        this(providerId, name, trustmark, new HashSet<>(), new TreeSet<>());
    }

    public String getCountryCode() {
        return country.getCode();
    }

    public int getProviderId() {
        return providerId;
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
    public String getTrustmark() {
        return trustmark;
    }

    public Set<String> getServiceTypes() {
        return serviceTypes;
    }

    public Set<Service> getServices() {
        return services;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Country getCountry() {
        return country;
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
                "providerId=" + providerId +
                ", name='" + name + '\'' +
                ", trustmark='" + trustmark + '\'' +
                ", serviceTypes=" + serviceTypes +
                ", countryCode=" + country.getCode() +
                ", services=" + services +
                '}';
    }

    @Override
    public Provider clone() {
        try {
            Provider providerClone = (Provider) super.clone();
            providerClone.services = new TreeSet<>();
            this.getServices().forEach(service -> {
                Service serviceClone = service.clone();
                providerClone.getServices().add(serviceClone);
                serviceClone.setProvider(providerClone);
            });
            return providerClone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public int compareTo(Provider provider) {
        int nameComparison = this.name.compareTo(provider.name);
        return nameComparison != 0 ? nameComparison : this.trustmark.compareTo(provider.trustmark);
    }
}
