package com.trustedservices.domain;

import java.util.*;

public class TrustedList implements Cloneable {

    private final Set<String> serviceTypes;
    private final Set<String> statuses;

    private List<Country> countries;

    public TrustedList() {
        this.countries = new ArrayList<>(0);
        this.serviceTypes = new HashSet<>(0);
        this.statuses = new HashSet<>(0);
    }

    public TrustedList(List<Country> countries) {
        this();
        this.countries = countries;
        this.updateServiceTypesAndStatuses();
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void updateServiceTypesAndStatuses() {
        statuses.clear();
        serviceTypes.clear();
        countries.forEach(country -> {
            country.getProviders().forEach(provider -> {
                provider.getServices().forEach(service -> {
                    serviceTypes.addAll(service.getServiceTypes());
                    statuses.add(service.getStatus());
                });
            });
        });
    }

    @Override
    public TrustedList clone() {
        try {
            TrustedList clone = (TrustedList) super.clone();
            clone.countries = new ArrayList<>();
            this.countries.forEach(
                    country -> clone.getCountries().add(country.clone())
            );
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<String> getServiceTypes() {
        return serviceTypes;
    }
    public Set<String> getStatuses() {
        return statuses;
    }

    @Override
    public boolean equals(Object trustedList ) {
        if (this == trustedList) return true;
        if (trustedList == null || getClass() != trustedList.getClass()) return false;
        TrustedList list = (TrustedList) trustedList;
        return (this.getCountries().equals(list.getCountries())
                && this.getServiceTypes().equals(list.getServiceTypes())
                && this.getStatuses().equals(((list).getStatuses())));
    }

    @Override
    public int hashCode() {
        return Objects.hash(countries);
    }
}