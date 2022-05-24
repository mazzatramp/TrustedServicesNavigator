package com.tsn.trustedservicesnavigator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProviderFilter extends Filter {

    private List<String> whitelist;

    public ProviderFilter() {
        this.whitelist = new ArrayList<>();
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

    private void filterByWhitelist(TrustedList listToFilter) {
        listToFilter.getCountries().forEach(country -> {
            country.getProviders().removeIf(provider -> !whitelist.contains(provider.getName()));
        });
    }
}
