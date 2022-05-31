package com.trustedservices.navigator.filters;

import com.trustedservices.domain.TrustedList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Filter {

    protected Set<String> whitelist;

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

