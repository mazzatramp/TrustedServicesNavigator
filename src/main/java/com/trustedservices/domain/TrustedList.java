package com.trustedservices.domain;

import java.util.*;

public class TrustedList implements Cloneable {
    private final Set<String> serviceTypes;
    private final Set<String> statuses;

    private TreeSet<Country> countries;

    public TrustedList() {
        this(new TreeSet<>());
    }

    public TrustedList(TreeSet<Country> countries) {
        this.countries = countries;
        this.serviceTypes = new HashSet<>(0);
        this.statuses = new HashSet<>(0);
        this.updateServiceTypesAndStatuses();
    }

    public Set<Country> getCountries() {
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
            clone.countries = new TreeSet<>();
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
    public boolean equals(Object trustedList) {
        if (this == trustedList) return true;
        if (trustedList == null || getClass() != trustedList.getClass()) return false;
        TrustedList list = (TrustedList) trustedList;
        return (this.getCountries().equals(list.getCountries())
                && this.getServiceTypes().equals(list.getServiceTypes())
                && this.getStatuses().equals(list.getStatuses()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(countries);
    }
}