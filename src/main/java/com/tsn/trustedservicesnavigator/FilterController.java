package com.tsn.trustedservicesnavigator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FilterController {
    private final CountryProviderFilter countryProviderFilter;
    private final ServiceTypeFilter serviceTypeFilter;
    private final StatusFilter statusFilter;

    public FilterController() {
        countryProviderFilter = new CountryProviderFilter();
        serviceTypeFilter = new ServiceTypeFilter();
        statusFilter = new StatusFilter();
    }

    private ArrayList<Filter> filters() {
        ArrayList<Filter> filters = new ArrayList<>(0);
        filters.add(countryProviderFilter);
        filters.add(serviceTypeFilter);
        filters.add(statusFilter);
        return filters;
    }

    public void setCountryProviderWhitelist(Map<String, List<String>> countryProviderWhitelist) {
        countryProviderFilter.setWhitelist(countryProviderWhitelist);
    }

    public void setServiceTypeWhitelist(List<String> serviceTypeWhitelist) {
        serviceTypeFilter.setWhitelist(serviceTypeWhitelist);
    }

    public void setStatusWhitelist(List<String> statusWhitelist) {
        statusFilter.setWhitelist(statusWhitelist);
    }
    public TrustedList getFilteredDataFrom(TrustedList target) {
        TrustedList clone = target.clone();
        filters().forEach(
                filter -> filter.applyTo(clone)
        );
        clone.getCountries().forEach(country -> country.getProviders().removeIf(provider -> provider.getServices().isEmpty()));
        clone.getCountries().removeIf(country -> country.getProviders().isEmpty());
        return clone;
    }
}
