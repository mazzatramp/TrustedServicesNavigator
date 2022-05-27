package com.tsn.trustedservicesnavigator;

import com.tsn.trustedservicesnavigator.backend.TrustedList;
import com.tsn.trustedservicesnavigator.frontend.FilterController;
import com.tsn.trustedservicesnavigator.frontend.UserInterfaceController;
import com.tsn.trustedservicesnavigator.frontend.panes.FilterSelectionAccordion;
import javafx.concurrent.Task;

import java.io.IOException;

public class NavigationMediator {
    private UserInterfaceController userInterfaceController;
    private final FilterController filterController;
    private final TrustedList completeList;

    public NavigationMediator() {
        this.filterController = new FilterController();
        this.completeList = new TrustedList();
    }

    public void setUserInterfaceController(UserInterfaceController userInterfaceController) {
        this.userInterfaceController = userInterfaceController;
        fillCompleteListFromApiData();
    }

    public TrustedList getFilteredList() {
        return filterController.getFilteredDataFrom(completeList);
    }

    public void readActiveFiltersFrom(FilterSelectionAccordion filterSelection) {
        filterController.setCountryProviderWhitelist(filterSelection.getSelectedCountriesAndProviders());
        filterController.setServiceTypeWhitelist(filterSelection.getSelectedServiceTypes());
        filterController.setStatusWhitelist(filterSelection.getSelectedStatuses());
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
}