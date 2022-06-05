package com.trustedservices.navigator.filters;

import com.trustedservices.domain.Country;
import com.trustedservices.domain.Provider;
import com.trustedservices.domain.TrustedList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ProviderFilter extends Filter {
    @Override
    protected void filterByWhitelist(TrustedList listToFilter) {
        for (Country country : listToFilter.getCountries()) {
            country.getProviders().removeIf(provider ->
                    !getWhitelist().contains(country.getName() + "/" + provider.getName()));
        }
    }
}