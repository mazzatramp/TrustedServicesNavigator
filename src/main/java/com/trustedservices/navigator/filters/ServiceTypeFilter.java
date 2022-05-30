package com.trustedservices.navigator.filters;

import com.trustedservices.domain.TrustedList;

import java.util.Collections;

public class ServiceTypeFilter extends Filter {
    @Override
    public void filterByWhitelist (TrustedList listToFilter) {
        listToFilter.getCountries().forEach(country -> {
            country.getProviders().removeIf(
                    provider -> Collections.disjoint(whitelist, provider.getServiceTypes())
            );
            country.getProviders().forEach(
                    provider -> provider.getServices().removeIf(
                            service -> Collections.disjoint(whitelist, service.getServiceTypes())
                    )
            );
        });
    }
}
