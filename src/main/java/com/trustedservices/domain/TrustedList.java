package com.trustedservices.domain;

import java.util.*;

/**
 * It has been decided to organize the TrustedList in a treeView, so this class represents the root of the tree.
 * It contains a set of countries, and two set of Strings, serviceTypes and statuses, that get filled with the
 * method updateServiceTypeAndStatuses. The sets serviceTypes and statuses will be used to filter the list and fill filter panels.
 * The Trusted List gets filled by two different classes,
 * @see com.trustedservices.navigator.web.TrustedListJsonBuilder
 * @see com.trustedservices.navigator.web.TrustedListApiBuilder
 */
public class TrustedList implements Cloneable {
    private final Set<String> serviceTypes;
    private final Set<String> statuses;

    private TreeSet<Country> countries;

    public TrustedList() {
        this.countries = new TreeSet<>();
        this.serviceTypes = new TreeSet<>();
        this.statuses = new TreeSet<>();
    }

    public TrustedList(TreeSet<Country> countries) {
        this();
        this.countries = countries;
        this.updateServiceTypesAndStatuses();
    }

    public TreeSet<Country> getCountries() {
        return countries;
    }

    /**
     * Method that browses the list get all the Service Types and Statuses, be the list complete or filtered
     * @see com.trustedservices.navigator.filters.FilterList
     */
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

    /**
     * @return A clone of the TrustedList. Uses all the methods Override of the lower classes for each level
     * @see Country
     * @see Provider
     * @see Service
     */
    @Override
    public TrustedList clone() {
        try {
            TrustedList clonedList = (TrustedList) super.clone();
            clonedList.countries = new TreeSet<>();
            this.countries.forEach(
                    country -> clonedList.getCountries().add(country.clone())
            );
            return clonedList;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
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


    public boolean isEmpty() {
        return getCountries().isEmpty();
    }
}