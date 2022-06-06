package com.trustedservices.domain;

import java.util.*;

/**
 * The first layer of the tree, each country contains its name, country code and a list of its associated providers.
 */
public class Country implements Cloneable, Comparable<Country>, TrustedListEntity {
    private final String name;
    private final String code;
    private Set<Provider> providers;

    public Country(String name, String code) {
        this.name = name;
        this.code = code;
        this.providers = new TreeSet<>();
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * @return A string of Name, Country code, number of providers and number of services.
     * Used in Display Pane
     * @see com.trustedservices.navigator.components.DisplayPane
     * implemented also in
     * @see Provider
     * @see Service
     */
    @Override
    public String getDescription() {
        return name + " (" + code + ")\n\n" +
                "With " + providers.size() + " providers displayed\n" +
                "And " + countServices() + " services displayed\n";
    }

    public String getCode() {
        return code;
    }

    public Set<Provider> getProviders() {
        return providers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(this.getCode(), country.getCode())
               && Objects.equals(this.getName(), country.getName())
               && Objects.equals(this.getProviders(), country.getProviders());
    }

    @Override
    public int compareTo(Country country) {
        return this.code.compareTo(country.code);
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + "', " +
                "code='" + code + '\'' +
                '}';
    }

    /**
     * @return clonedCountry using the Object method clone, but we implemented a safe clone of the provider List associated, using an override Clone method for that too
     * @see Provider
     * @see Service
     */
    @Override
    public Country clone() {
        try {
            Country countryClone = (Country) super.clone();
            countryClone.providers = new TreeSet<>();

            this.getProviders().forEach(provider -> {
                Provider providerClone = provider.clone();
                providerClone.setCountry(countryClone);
                countryClone.getProviders().add(providerClone);
            });

            return countryClone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    private int countServices() {
        int count = 0;
        for (Provider provider: providers) {
            count += provider.getServices().size();
        }
        return count;
    }
}
