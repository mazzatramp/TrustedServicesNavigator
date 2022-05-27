package com.tsn.trustedservicesnavigator.frontend;

import com.tsn.trustedservicesnavigator.frontend.filters.ServiceTypeFilter;
import com.tsn.trustedservicesnavigator.frontend.filters.StatusFilter;
import com.tsn.trustedservicesnavigator.backend.TrustedList;
import com.tsn.trustedservicesnavigator.frontend.filters.CountryProviderFilter;
import com.tsn.trustedservicesnavigator.frontend.filters.Filter;

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
        filters().forEach(filter -> filter.applyTo(clone));
        removeEmptyEntities(clone);
        return clone;
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
