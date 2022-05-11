package com.tsn.trustedservicesnavigator;

import java.util.ArrayList;
import java.util.Objects;

public class Provider {
    private int providerId;
    private String name;
    private String countryCode;
    private String trustmark;
    private ArrayList<String> serviceTypes;
    private ArrayList<Service> services;

    public Provider(int providerId, String name, String countryCode, String trustmark, ArrayList<String> serviceTypes) {
        this.providerId = providerId;
        this.name = name;
        this.countryCode = countryCode;
        this.trustmark = trustmark;
        this.serviceTypes = serviceTypes;
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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getTrustmark() {
        return trustmark;
    }

    public void setTrustmark(String trustmark) {
        this.trustmark = trustmark;
    }

    public ArrayList<String> getServiceTypes() {
        return serviceTypes;
    }

    public void setServiceTypes(ArrayList<String> serviceTypes) {
        this.serviceTypes = serviceTypes;
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Provider provider = (Provider) o;
        return providerId == provider.providerId && Objects.equals(countryCode, provider.countryCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(providerId, countryCode);
    }

    @Override
    public String toString() {
        return "Provider{" +
                "name='" + name + '\'' +
                ", trustmark='" + trustmark + '\'' +
                '}';
    }
}
