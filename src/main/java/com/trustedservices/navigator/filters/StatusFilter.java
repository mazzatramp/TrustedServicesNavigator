package com.trustedservices.navigator.filters;

import com.trustedservices.domain.TrustedList;

public class StatusFilter extends Filter {
    @Override
    public void filterByWhitelist (TrustedList listToFilter) {
        listToFilter.getCountries().forEach(country -> {
            country.getProviders().forEach(provider -> provider.getServices().removeIf(service -> !whitelist.contains(service.getStatus())));
        });
    }
}
