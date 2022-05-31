package com.trustedservices.navigator;

import com.trustedservices.domain.TrustedList;
import com.trustedservices.navigator.filters.FilterController;
import com.trustedservices.navigator.components.FilterSelectionAccordion;
import com.trustedservices.navigator.web.TrustedListBuilder;

public class NavigationMediator {
    private WindowController windowController;
    private FilterController filterController;
    private TrustedList completeList;

    public NavigationMediator() {
        this.completeList = new TrustedList();
    }

    public void setUserInterfaceController(WindowController windowController) {
        this.windowController = windowController;
    }

    public TrustedList getFilteredList() {
        TrustedList clone = completeList.clone();
        this.updateActiveFiltersFromUserSelection();
        filterController.applyFiltersTo(clone);
        return clone;
    }

    public TrustedList getCompleteList() {
        return completeList;
    }

    public FilterController getFilterController() {
        return this.filterController;
    }

    public void updateActiveFiltersFromUserSelection() {
        FilterSelectionAccordion filterSelection = windowController.getFilterAccordion();
        filterController.getProviderFilter().setWhitelist(filterSelection.getSelectedProviders());
        filterController.getStatusFilter().setWhitelist(filterSelection.getSelectedStatuses());
        filterController.getServiceTypeFilter().setWhitelist(filterSelection.getSelectedServiceTypes());

    }

    public void buildCompleteList(TrustedListBuilder builder) {
        completeList = builder.build();
    }

    public void setFilterController(FilterController filterController) {
        this.filterController = filterController;
    }
}