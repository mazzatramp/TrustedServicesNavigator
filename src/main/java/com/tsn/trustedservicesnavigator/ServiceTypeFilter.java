package com.tsn.trustedservicesnavigator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServiceTypeFilter extends Filter {

    @Override
    public void filterByWhitelist (TrustedList listToFilter) {
        listToFilter.getCountries().forEach(country -> {
            country.getProviders().removeIf(provider -> Collections.disjoint(whitelist, provider.getServiceTypes()));
            country.getProviders().forEach(provider -> provider.getServices().removeIf(service -> Collections.disjoint(whitelist, service.getServiceTypes())));
        });
    }
}
