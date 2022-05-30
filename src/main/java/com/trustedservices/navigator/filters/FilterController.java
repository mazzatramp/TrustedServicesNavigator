package com.trustedservices.navigator.filters;

import com.trustedservices.domain.TrustedList;
import com.trustedservices.navigator.NavigationMediator;
import com.trustedservices.navigator.filters.Filter;
import com.trustedservices.navigator.filters.ProviderFilter;
import com.trustedservices.navigator.filters.ServiceTypeFilter;
import com.trustedservices.navigator.filters.StatusFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FilterController {
    private final Filter providerFilter, serviceTypeFilter, statusFilter;
    private NavigationMediator navigationMediator;

    public FilterController() {
        providerFilter = new ProviderFilter();
        serviceTypeFilter = new ServiceTypeFilter();
        statusFilter = new StatusFilter();
    }

    private Stream<Filter> getFilters() {
        Stream.Builder<Filter> filters = Stream.builder();
        filters.add(providerFilter);
        filters.add(serviceTypeFilter);
        filters.add(statusFilter);
        return filters.build();
    }

    public void applyFiltersTo(TrustedList target) {
        navigationMediator.updateActiveFiltersFromUserSelection();
        getFilters().forEach(filter -> filter.applyTo(target));
        removeEmptyEntities(target);
        target.updateServiceTypesAndStatuses();
    }

    public Filter getProviderFilter() {
        return providerFilter;
    }
    public Filter getServiceTypeFilter() {
        return serviceTypeFilter;
    }
    public Filter getStatusFilter() {
        return statusFilter;
    }

    public boolean wouldHaveZeroServices(Filter filter, String itemToTest) {
        assert navigationMediator != null;
        boolean hasZeroServices;
        List<String> actualWhitelist = filter.getWhitelist();

        List<String> whitelistForTesting = new ArrayList<>(1);
        whitelistForTesting.add(itemToTest);
        filter.setWhitelist(whitelistForTesting);

        TrustedList toFilter = navigationMediator.getCompleteList().clone();
        hasZeroServices = filtersHaveZeroResultsAppliedTo(toFilter);

        filter.setWhitelist(actualWhitelist);

        return hasZeroServices;
    }

    private boolean filtersHaveZeroResultsAppliedTo(TrustedList test) {
        getFilters().forEach(filter -> filter.applyTo(test));
        removeEmptyEntities(test);

        return test.getCountries().isEmpty();
    }

    private void removeEmptyEntities(TrustedList clone) {
        removeEmptyProviders(clone);
        removeEmptyCountries(clone);
    }

    private void removeEmptyProviders(TrustedList clone) {
        clone.getCountries().forEach(country -> {
            country.getProviders().removeIf(provider -> provider.getServices().isEmpty());
        });
    }

    private void removeEmptyCountries(TrustedList clone) {
        clone.getCountries().removeIf(country -> country.getProviders().isEmpty());
    }

    public void setNavigationMediator(NavigationMediator navigationMediator) {
        this.navigationMediator = navigationMediator;
    }
}
