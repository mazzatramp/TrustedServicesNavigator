package com.trustedservicesnavigator.frontend.filters;

import com.trustedservicesnavigator.domain.TrustedList;

import java.util.ArrayList;

public class StatusFilter extends Filter {

    public StatusFilter() {
        this.whitelist = new ArrayList<>(0);
    }

    @Override
    public void filterByWhitelist (TrustedList listToFilter) {
        listToFilter.getCountries().forEach(country -> {
            country.getProviders().forEach(provider -> provider.getServices().removeIf(service -> !whitelist.contains(service.getStatus())));
        });
    }
}
