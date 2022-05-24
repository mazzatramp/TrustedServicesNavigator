package com.tsn.trustedservicesnavigator;

import java.util.stream.Stream;

public class FilterController {
    private final ProviderFilter countryProviderFilter;
    private final ServiceTypeFilter serviceTypeFilter;
    private final StatusFilter statusFilter;

    public FilterController() {
        countryProviderFilter = new ProviderFilter();
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

    public TrustedList getFilteredDataFrom(TrustedList target) {
        TrustedList clone = target.clone();
        filters().forEach(filter -> filter.applyTo(clone));
        removeEmptyEntities(clone);
        return clone;
    }

    public Filter getCountryProviderFilter() {
        return countryProviderFilter;
    }

    public Filter getServiceTypeFilter() {
        return serviceTypeFilter;
    }

    public Filter getStatusFilter() {
        return statusFilter;
    }

    private void removeEmptyEntities(TrustedList clone) {
        removeEmptyProviders(clone);
        removeEmptyCountries(clone);
    }

    private void removeEmptyCountries(TrustedList clone) {
        clone.getCountries().removeIf(country -> country.getProviders().isEmpty());
    }

    private void removeEmptyProviders(TrustedList clone) {
        clone.getCountries().forEach(country -> {
            country.getProviders().removeIf(provider -> provider.getServices().isEmpty());
        });
    }
}
