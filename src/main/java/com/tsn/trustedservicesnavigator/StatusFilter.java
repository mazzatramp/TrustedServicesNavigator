package com.tsn.trustedservicesnavigator;

import java.util.ArrayList;
import java.util.List;

public class StatusFilter extends Filter {
    public List<String> whitelist;

    public StatusFilter() {
        this.whitelist = new ArrayList<>(0);
    }

    @Override
    public void applyTo(TrustedList listToFilter) {
        if (!whitelist.isEmpty()) {
            filterByWhitelist(listToFilter);
        }
    }

    @Override
    public void setWhitelist(List<String> whitelist) {
        this.whitelist = whitelist;
    }

    public void filterByWhitelist (TrustedList listToFilter) {
        listToFilter.getCountries().forEach(country -> {
            country.getProviders().forEach(provider -> provider.getServices().removeIf(service -> !whitelist.contains(service.getStatus())));
        });
    }
}
