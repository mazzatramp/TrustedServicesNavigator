package com.trustedservices.navigator.filters;

import com.trustedservices.domain.TrustedList;

public class ProviderFilter extends Filter {
    @Override
    protected void filterByWhitelist(TrustedList listToFilter) {
        listToFilter.getCountries().forEach(country -> {
            country.getProviders().removeIf(provider -> !whitelist.contains(country.getName() + "/" +provider.getName()));
        });
    }
}
