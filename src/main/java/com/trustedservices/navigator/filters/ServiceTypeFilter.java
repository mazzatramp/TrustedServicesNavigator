package com.trustedservices.navigator.filters;

import com.trustedservices.domain.Country;
import com.trustedservices.domain.Provider;
import com.trustedservices.domain.TrustedList;

import java.util.Collections;
import java.util.Set;

/**
 * The ServiceTypes filter browses the list and removes providers and services if they don't have the required serviceTypes,
 * which are on the ServiceTypeFilter's whiteList
 */
public class ServiceTypeFilter extends Filter {
    @Override
    public void filterByWhitelist (TrustedList listToFilter) {
        for (Country country : listToFilter.getCountries()) {
            country.getProviders().removeIf(provider -> areNotInWhitelist(provider.getServiceTypes()));
            for (Provider provider : country.getProviders()) {
                provider.getServices().removeIf(service -> areNotInWhitelist(service.getServiceTypes()));
            }
        }
    }

    private boolean areNotInWhitelist(Set<String> serviceTypes) {
        return Collections.disjoint(getWhitelist(), serviceTypes);
    }
}
