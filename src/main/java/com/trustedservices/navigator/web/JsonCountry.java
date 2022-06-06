package com.trustedservices.navigator.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustedservices.domain.Country;

/**
 * Class created to get the attributes of a country from a json file and create a new Country safely (with this system it's hard
 * to modify items of the TrustedList, or add external objects).
 */
public class JsonCountry {
    private String name;
    private String code;

    public Country createCountry() {
        return new Country(name, code);
    }

    /**
     * Reads name and country code from the json using the tags countryName and countryCode and constructs a new JsonCountry
     */
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
    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
}
