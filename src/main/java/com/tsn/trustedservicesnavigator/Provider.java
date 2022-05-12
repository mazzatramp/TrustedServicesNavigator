package com.tsn.trustedservicesnavigator;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Provider {
    private String countryCode;
    private int providerId;
    private String name;
    private String trustmark;
    private List<String> serviceTypes;
    private List<Service> services;

    @JsonCreator
    public Provider(@JsonProperty("countryCode") String countryCode, @JsonProperty("tspId") int providerId, @JsonProperty("name") String name, @JsonProperty("trustmark") String trustmark, @JsonProperty("qServiceTTypes") List<String> serviceTypes, @JsonProperty ("services") List<Service> services) {
        this.countryCode=countryCode;
        this.providerId = providerId;
        this.name = name;
        this.trustmark = trustmark;
        this.serviceTypes = serviceTypes;
        this.services= services;
    }


    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrustmark() {
        return trustmark;
    }

    public void setTrustmark(String trustmark) {
        this.trustmark = trustmark;
    }

    public List<String> getServiceTypes() {
        return serviceTypes;
    }

    public void setServiceTypes(ArrayList<String> serviceTypes) {
        this.serviceTypes = serviceTypes;
    }

    public List<Service> getServices() {
        return services;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Provider provider = (Provider) o;
        return providerId == provider.providerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(providerId);
    }

    @Override
    public String toString() {
        return "Provider{" +
                "name='" + name + '\'' +
                ", trustmark='" + trustmark + '\'' +
                '}';
    }
}
