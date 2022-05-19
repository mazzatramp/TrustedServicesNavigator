package com.tsn.trustedservicesnavigator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FilterController {
    private final CountryProviderFilter countryProviderFilter;
    private final ServiceTypeFilter serviceTypeFilter;

    public FilterController() {
        countryProviderFilter = new CountryProviderFilter();
        serviceTypeFilter = new ServiceTypeFilter();
    }

    private ArrayList<Filter> filters() {
        ArrayList<Filter> filters = new ArrayList<>(0);
        filters.add(countryProviderFilter);
        filters.add(serviceTypeFilter);
        return filters;
    }

    public void setCountryProviderWhitelist(Map<String, List<String>> countryProviderWhitelist) {
        countryProviderFilter.setWhitelist(countryProviderWhitelist);
    }

    public void setServiceTypeFilter(List<String> serviceTypeWhitelist) {
        serviceTypeFilter.setWhitelist(serviceTypeWhitelist);
    }

    public TrustedList getFilteredDataFrom(TrustedList target) {
        TrustedList clone = target.clone();
        filters().forEach(
                filter -> filter.applyTo(clone)
        );
        return clone;
    }
}
