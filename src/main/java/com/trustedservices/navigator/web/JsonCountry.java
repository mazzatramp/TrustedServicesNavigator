package com.trustedservices.navigator.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustedservices.domain.Country;

public class JsonCountry {
    private String name;
    private String code;

    public Country createCountry() {
        return new Country(name, code);
    }

    @JsonCreator
    public JsonCountry(
            @JsonProperty("countryName") String name,
            @JsonProperty("countryCode") String code
    ) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    @JsonSetter
    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    @JsonSetter
    public void setCode(String code) {
        this.code = code;
    }
}
