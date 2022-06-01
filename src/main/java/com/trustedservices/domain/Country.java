package com.trustedservices.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Country implements Cloneable, Comparable<Country>, TrustedListEntity {
    private String name;
    private String code;

    private List<Provider> providers;

    @JsonCreator
    public Country(
            @JsonProperty("countryName") String name,
            @JsonProperty("countryCode") String code
    ) {
        this.name = name;
        this.code = code;
        providers = new ArrayList<>(0);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getInformation() {
        List<String> information = new ArrayList<>(2);
        information.add(name);
        information.add(code);
        return information;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List <Provider> getProviders() {
        return providers;
    }

    public void setProviders(ArrayList<Provider> providers) {
        this.providers = providers;
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
            countryClone.setProviders(new ArrayList<>());
            this.getProviders().forEach(
                    provider -> {
                        Provider providerClone = provider.clone();
                        providerClone.setCountry(countryClone);
                        countryClone.getProviders().add(providerClone);
                    });
            return countryClone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
