package com.tsn.trustedservicesnavigator;

import java.util.ArrayList;

public class ProviderFilter extends Filter {
    @Override
    protected void filterByWhitelist(TrustedList listToFilter) {
        listToFilter.getCountries().forEach(country -> {
            country.getProviders().removeIf(provider -> !whitelist.contains(provider.getName()));
        });
    }
}
