package com.trustedservicesnavigator.frontend;

import com.trustedservicesnavigator.Aiuto;
import com.trustedservicesnavigator.NavigationMediator;
import com.trustedservicesnavigator.domain.TrustedList;
import com.trustedservicesnavigator.frontend.filters.Filter;
import com.trustedservicesnavigator.frontend.filters.ProviderFilter;
import com.trustedservicesnavigator.frontend.filters.ServiceTypeFilter;
import com.trustedservicesnavigator.frontend.filters.StatusFilter;

import java.io.IOException;
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

    private Stream<Filter> filters() {
        Stream.Builder<Filter> filters = Stream.builder();
        filters.add(providerFilter);
        filters.add(serviceTypeFilter);
        filters.add(statusFilter);
        return filters.build();
    }

    public void applyFiltersTo(TrustedList target) {
        filters().forEach(filter -> filter.applyTo(target));
        removeEmptyEntities(target);
        target.fillServiceTypesAndStatuses();
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


    public boolean wouldHaveZeroServices(Filter filter, String newWhitelistItem) throws IOException {
        boolean hasZeroServices;

        List<String> realWhitelist = filter.getWhitelist();
        List<String> testWhitelist = new ArrayList<>(realWhitelist);
        testWhitelist.add(newWhitelistItem);
        filter.setWhitelist(testWhitelist);

        //TrustedList filteredList = navigationMediator.getCompleteList().clone();
        TrustedList filteredList = Aiuto.getWholeList();
        hasZeroServices = haveZeroResultsAppliedTo(filteredList);

        filter.setWhitelist(realWhitelist);
        return hasZeroServices;
    }

    private boolean haveZeroResultsAppliedTo(TrustedList test) {
        filters().forEach(filter -> filter.applyTo(test));
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
