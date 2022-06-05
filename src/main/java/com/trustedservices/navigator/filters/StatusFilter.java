package com.trustedservices.navigator.filters;

import com.trustedservices.domain.*;

public class StatusFilter extends Filter {
    @Override
    public void filterByWhitelist (TrustedList listToFilter) {
        for (Country country : listToFilter.getCountries()) {
            for (Provider provider : country.getProviders()) {
                provider.getServices().removeIf(service -> !getWhitelist().contains(service.getStatus()));
            }
        }
    }
}
