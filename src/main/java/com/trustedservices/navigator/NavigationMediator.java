package com.trustedservices.navigator;

import com.trustedservices.domain.TrustedList;
import com.trustedservices.navigator.filters.FilterController;
import com.trustedservices.navigator.components.FilterSelectionAccordion;
import com.trustedservices.web.TrustedListApiBuilder;
import com.trustedservices.web.TrustedListBuilder;

public class NavigationMediator {
    private UserInterfaceController userInterfaceController;
    private FilterController filterController;
    private TrustedList completeList;

    public NavigationMediator() {
        this.filterController = new FilterController();
        filterController.setNavigationMediator(this);
        this.completeList = new TrustedList();
    }

    public void setUserInterfaceController(UserInterfaceController userInterfaceController) {
        this.userInterfaceController = userInterfaceController;
    }

    public TrustedList getFilteredList() {
        TrustedList clone = completeList.clone();
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
        FilterSelectionAccordion filterSelection = userInterfaceController.getFilterAccordion();
        filterController.getProviderFilter().setWhitelist(filterSelection.getSelectedProviders());
        filterController.getStatusFilter().setWhitelist(filterSelection.getSelectedStatuses());
        filterController.getServiceTypeFilter().setWhitelist(filterSelection.getSelectedServiceTypes());

    }

    public void buildCompleteList(TrustedListBuilder builder) {
        completeList = builder.build();
    }
}