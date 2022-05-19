package com.tsn.trustedservicesnavigator;

import javafx.concurrent.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    }

    public TrustedList getFilteredList() {
        return filterController.getFilteredDataFrom(completeList);
    }

    private void readActiveFilters(FilterSelectionAccordion filterSelection) {
        filterController.setCountryProviderWhitelist(userInterfaceController.getSelectedCountriesAndProviders());
    }

    public TrustedList getCompleteList() {
        return completeList;
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