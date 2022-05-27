package com.tsn.trustedservicesnavigator.frontend.filters;

import com.tsn.trustedservicesnavigator.backend.TrustedList;
import com.tsn.trustedservicesnavigator.frontend.filters.Filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServiceTypeFilter extends Filter {
    public List<String> whitelist;

    public ServiceTypeFilter() {
        this.whitelist = new ArrayList<>(0);
    }

    @Override
    public void applyTo(TrustedList listToFilter) {
        if (!whitelist.isEmpty()) {
            filterByWhitelist(listToFilter);
        }
    }

    public void setWhitelist(List<String> whitelist) {
        this.whitelist = whitelist;
    }

    public void filterByWhitelist (TrustedList listToFilter) {
        listToFilter.getCountries().forEach(country -> {
            country.getProviders().removeIf(provider -> Collections.disjoint(whitelist, provider.getServiceTypes()));
            country.getProviders().forEach(provider -> provider.getServices().removeIf(service -> Collections.disjoint(whitelist, service.getServiceTypes())));
        });
    }
}
