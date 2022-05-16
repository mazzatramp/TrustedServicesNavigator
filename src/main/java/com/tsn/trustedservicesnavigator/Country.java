package com.tsn.trustedservicesnavigator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Country implements Cloneable {
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

    public String getName() {
        return name;
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
        return Objects.equals(code, country.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, code);
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
