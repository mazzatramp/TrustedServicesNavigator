package com.trustedservices.navigator.filters;

import com.trustedservices.domain.Country;
import com.trustedservices.domain.Provider;
import com.trustedservices.domain.TrustedList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * The provider filter uses the provider's name and country to filter the list. It browses the list and removes the objects
 * if they aren't on the whiteList
 */
public class ProviderFilter extends Filter {
    @Override
    protected void filterByWhitelist(TrustedList listToFilter) {
        for (Country country : listToFilter.getCountries()) {
            country.getProviders().removeIf(provider ->
                    !getWhitelist().contains(country.getName() + "/" + provider.getName()));
        }
    }
}