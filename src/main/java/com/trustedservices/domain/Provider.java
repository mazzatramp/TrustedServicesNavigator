package com.trustedservices.domain;

import java.util.*;

/**
 * Second layer of the tree, contains provider name, id, trustmark, a set of ServiceTypes, a set of associated services and
 * the country which the provider belongs to.
 */
public class Provider implements Cloneable, Comparable<Provider>, TrustedListEntity {
    private final int providerId;
    private final String name;
    private final String trustmark;
    private final Set<String> serviceTypes;

    private Country country;
    private Set<Service> services;

    public Provider(Country country, int providerId, String name, String trustmark,
                    Set<String> serviceTypes, Set<Service> services
    ){
        this.country = country;
        this.providerId = providerId;
        this.name = name;
        this.trustmark = trustmark;
        this.serviceTypes = serviceTypes;
        this.services = services;
    }

    public Provider(Country country, int providerId, String name, String trustmark) {
        this(country, providerId, name, trustmark, new HashSet<>(), new TreeSet<>());
    }

    public int getProviderId() {
        return providerId;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * @return A string of Name, Trustmark, Country name and code, and number of services.
     * Used in DisplayPane
     * @see com.trustedservices.navigator.components.DisplayPane
     * implemented also in
     * @see Country
     * @see Service
     */
    @Override
    public String getDescription() {
        return  "Name: " + name + "\n" +
                "Trustmark: " + trustmark + "\n\n" +
                "Based in " + country.getName() + " (" + country.getCode() + ")\n" +
                "With " + services.size() + " services displayed.";
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

    public Country getCountry() {
        return country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Provider provider = (Provider) o;

        return Objects.equals(this.getName(), provider.getName())
                && Objects.equals(this.getCountry(), provider.getCountry())
                && Objects.equals(this.getServices(), provider.getServices());
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

    /**
     * @return providerClone using the Object Method Clone, but we implemented a safe clone of the Service List associated, using an override clone method for Service too
     * @see Service
     */
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

    void setCountry(Country country) {
        this.country = country;
    }
}
