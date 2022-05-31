package com.trustedservices.navigator;

import com.trustedservices.domain.TrustedList;
import com.trustedservices.navigator.components.FilterSelectionAccordion;
import com.trustedservices.navigator.web.TrustedListBuilder;

public class NavigationController {

    private WindowController windowController;
    private TrustedList completeList;

    public NavigationController() {
        this.completeList = new TrustedList();
    }

    public void setWindowController(WindowController windowController) {
        this.windowController = windowController;
    }

    public TrustedList getFilteredList() {
    }

    public TrustedList getCompleteList() {
        return completeList;
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

    }
}