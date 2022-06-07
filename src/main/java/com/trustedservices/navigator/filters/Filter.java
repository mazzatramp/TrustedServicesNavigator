package com.trustedservices.navigator.filters;

import com.trustedservices.domain.Country;
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
    protected abstract void doWhitelistFiltering(TrustedList toFilter);

    /**
     * Method that applies the filters to the
     * @param toFilter
     * After filtering, removes empty entities inside the list.
     */
    public void applyTo(TrustedList toFilter) {
        if (!whitelist.isEmpty()) {
            doWhitelistFiltering(toFilter);
            removeEmptyEntities(toFilter);
        }
    }

    private void removeEmptyEntities(TrustedList trustedList) {
        removeEmptyProviders(trustedList);
        removeEmptyCountries(trustedList);
    }

    private void removeEmptyProviders(TrustedList trustedList) {
        for (Country country : trustedList.getCountries())
            country.getProviders().removeIf(provider -> provider.getServices().isEmpty());
    }

    private void removeEmptyCountries(TrustedList trustedList) {
        trustedList.getCountries().removeIf(country -> country.getProviders().isEmpty());
    }
}

