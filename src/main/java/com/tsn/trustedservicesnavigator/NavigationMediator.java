package com.tsn.trustedservicesnavigator;

import javafx.concurrent.Task;

import java.io.IOException;

public class NavigationMediator {
    private UserInterfaceController userInterfaceController;
    private final FilterController filterController;
    private final TrustedList completeList;
    private TrustedList filteredList;

    public NavigationMediator() {
        this.completeList = new TrustedList();
        this.filteredList = new TrustedList();
        this.filterController = new FilterController();
    }

    public void setUserInterfaceController(UserInterfaceController userInterfaceController) {
        this.userInterfaceController = userInterfaceController;
        fillCompleteListFromApiData();
    }

    public TrustedList getFilteredList() {
        readActiveFilters();
        filteredList = filterController.getFilteredDataFrom(completeList);
        return filteredList;
    }

    private void readActiveFilters() {
        filterController.setCountryWhitelist(userInterfaceController.getSelectedCountries());
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