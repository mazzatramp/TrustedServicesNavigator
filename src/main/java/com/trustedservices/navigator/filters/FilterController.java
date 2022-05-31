package com.trustedservices.navigator.filters;

import com.trustedservices.domain.TrustedList;
import com.trustedservices.navigator.NavigationController;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class FilterController {
    private final Filter providerFilter, serviceTypeFilter, statusFilter;
    private NavigationController navigationController;

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
        navigationController.updateActiveFiltersFromUserSelection();
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
        assert navigationController != null;
        boolean hasZeroServices;
        Set<String> actualWhitelist = filter.getWhitelist();

        Set<String> whitelistForTesting = new HashSet<>(1);
        whitelistForTesting.add(itemToTest);
        filter.setWhitelist(whitelistForTesting);

        TrustedList toFilter = navigationController.getCompleteList().clone();
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

    public void setNavigationController(NavigationController navigationController) {
        this.navigationController = navigationController;
    }
}
