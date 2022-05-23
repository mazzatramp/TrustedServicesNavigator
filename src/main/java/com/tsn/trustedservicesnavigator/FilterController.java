package com.tsn.trustedservicesnavigator;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class FilterController {
    private final CountryProviderFilter countryProviderFilter;
    private final ServiceTypeFilter serviceTypeFilter;
    private final StatusFilter statusFilter;

    public FilterController() {
        countryProviderFilter = new CountryProviderFilter();
        serviceTypeFilter = new ServiceTypeFilter();
        statusFilter = new StatusFilter();
    }

    private Stream<Filter> filters() {
        Stream.Builder<Filter> filters = Stream.builder();
        filters.add(countryProviderFilter);
        filters.add(serviceTypeFilter);
        filters.add(statusFilter);
        return filters.build();
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
        return clone;
    }
}
