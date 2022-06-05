package com.trustedservices.domain;

import java.util.*;

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

    @Override
    public String getHumanInformation() {
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
        return Objects.equals(this.code, country.code);
    }

    @Override
    public int compareTo(Country country) {
        return this.code.compareTo(country.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + "', " +
                "code='" + code + '\'' +
                '}';
    }

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
            throw new RuntimeException(e);
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
