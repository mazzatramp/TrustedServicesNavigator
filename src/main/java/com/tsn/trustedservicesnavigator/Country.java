package com.tsn.trustedservicesnavigator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Country {
    private String name;
    private String code;
    @JsonIgnore
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

    @JsonSetter("countryName")
    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    @JsonSetter("countryCode")
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
}
