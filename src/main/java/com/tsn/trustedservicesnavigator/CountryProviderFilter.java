package com.tsn.trustedservicesnavigator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountryProviderFilter extends Filter {

    private Map<String, List<String>> whitelist;

    public CountryProviderFilter() {
        this.whitelist = new HashMap<>(0);
    }

    @Override
    public void applyTo(TrustedList listToFilter) {
        if (!whitelist.isEmpty()) {
            filterByWhitelist(listToFilter);
        }
    }

    private void filterByWhitelist(TrustedList listToFilter) {
        listToFilter.getCountries().removeIf(country -> !whitelist.containsKey(country.getName()));
        listToFilter.getCountries().forEach(country -> {
            List<String> providers = whitelist.get(country.getName());
            country.getProviders().removeIf(provider -> !providers.contains(provider.getName()));
        });
    }

    public void setWhitelist(Map<String, List<String>> whitelist) {
        this.whitelist = whitelist;
    }
}
