package com.trustedservices.navigator.filters;

import com.trustedservices.domain.TrustedList;

import java.util.HashSet;
import java.util.Set;

/**
 * Abstract class for Filters. The filtering methods differ for each filter, but implements for everyone a disposal
 * of empty items after filtering.
 */
public abstract class Filter {

    private Set<String> whitelist;

    public Filter() {
        this.whitelist = new HashSet<>();
    }

    public void setWhitelist(Set<String> whitelist) {
        this.whitelist = whitelist;
    }

    public Set<String> getWhitelist() {
        return whitelist;
    }
    protected abstract void filterByWhitelist(TrustedList listToFilter);

    /**
     * Method that applies the filters to the
     * @param listToFilter
     * After filtering, removes empty entities inside the list.
     */
    public void applyTo(TrustedList listToFilter) {
        if (!whitelist.isEmpty()) {
            filterByWhitelist(listToFilter);
            removeEmptyEntities(listToFilter);
        }
    }

    private void removeEmptyEntities(TrustedList list) {
        removeEmptyProviders(list);
        removeEmptyCountries(list);
    }

    private void removeEmptyProviders(TrustedList list) {
        list.getCountries().forEach(country -> {
            country.getProviders().removeIf(provider -> provider.getServices().isEmpty());
        });
    }

    private void removeEmptyCountries(TrustedList list) {
        list.getCountries().removeIf(country -> country.getProviders().isEmpty());
    }
}

