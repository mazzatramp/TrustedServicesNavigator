package com.trustedservicesnavigator;

import com.trustedservicesnavigator.domain.TrustedList;
import com.trustedservicesnavigator.frontend.FilterController;
import com.trustedservicesnavigator.frontend.UserInterfaceController;
import com.trustedservicesnavigator.frontend.panes.FilterSelectionAccordion;
import javafx.concurrent.Task;

import java.io.IOException;

public class NavigationMediator {
    private UserInterfaceController userInterfaceController;
    private final FilterController filterController;
    private final TrustedList completeList;

    public NavigationMediator() {
        this.filterController = new FilterController();
        filterController.setNavigationMediator(this);
        this.completeList = new TrustedList();
    }

    public void setUserInterfaceController(UserInterfaceController userInterfaceController) {
        this.userInterfaceController = userInterfaceController;
        fillCompleteListFromApiData();
    }

    public TrustedList getFilteredList() {
        TrustedList clone = completeList.clone();
        filterController.applyFiltersTo(clone);
        return clone;
    }

    public TrustedList getCompleteList() {
        return completeList;
    }

    public void fillCompleteListFromApiData() {
        Task<Void> downloadingApiData = getDownloadApiDataTask();

        userInterfaceController.bindProgressBarWith(downloadingApiData);

        Thread th = new Thread(downloadingApiData);
        th.start();
    }

    private Task<Void> getDownloadApiDataTask() {
        return new Task<>() {
            @Override
            protected Void call() {
                try {
                    completeList.downloadApiData();
                    userInterfaceController.fillFiltersAndDisplay();
                } catch (IOException e) {
                    System.err.println("Can't download Api Data.");
                    System.err.println(e.getMessage());
                    throw new RuntimeException(e);
                }
                return null;
            }
        };
    }

    public FilterController getFilterController() {
        return this.filterController;
    }

    public void readActiveFiltersFrom(FilterSelectionAccordion filterSelectionAccordion) {
        FilterSelectionAccordion filterSelection = userInterfaceController.getFilterAccordion();
        filterController.getProviderFilter().setWhitelist(filterSelection.getSelectedProviders());
        filterController.getStatusFilter().setWhitelist(filterSelection.getSelectedStatuses());
        filterController.getServiceTypeFilter().setWhitelist(filterSelection.getSelectedServiceTypes());

    }
}