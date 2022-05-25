package com.tsn.trustedservicesnavigator;

import java.util.ArrayList;
import java.util.List;

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
