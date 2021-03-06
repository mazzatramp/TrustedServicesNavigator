package com.trustedservices.navigator.filters;

import com.trustedservices.domain.*;

/**
 * The Status filter browses al the list and eliminates services that don't have the required statuses, which are saved
 * on the filter's WhiteList
 */
public class StatusFilter extends Filter {
    @Override
    public void doWhitelistFiltering(TrustedList toFilter) {
        for (Country country : toFilter.getCountries()) {
            for (Provider provider : country.getProviders()) {
                provider.getServices().removeIf(service -> !getWhitelist().contains(service.getStatus()));
            }
        }
    }
}
