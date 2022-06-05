package com.trustedservices.domain;

import java.util.*;

public class Country implements Cloneable, Comparable<Country>, TrustedListEntity {
    private final String name;
    private final String code;
    private final Set<Provider> providers;

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
    public List<String> getInformation() {
        return List.of(name, code);
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
    public String toString() {
        return "Country{" +
                "name='" + name + "', " +
                "code='" + code + '\'' +
                '}';
    }

    @Override
    public Country clone() {
        Country countryClone = new Country(name, code);

        this.getProviders().forEach(provider -> {
            Provider providerClone = provider.clone();
            providerClone.setCountry(countryClone);
            countryClone.getProviders().add(providerClone);
        });

        return countryClone;
    }
}
