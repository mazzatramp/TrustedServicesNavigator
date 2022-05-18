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
    private final Set<String> serviceTypes;
    private TrustedList filteredList;

    public NavigationMediator() {
        this.completeList = new TrustedList();
        this.filteredList = new TrustedList();
        this.serviceTypes = new HashSet<>();
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
        filterController.setCountryProviderWhitelist(userInterfaceController.getSelectedCountriesAndProviders());
    }

    public TrustedList getCompleteList() {
        return completeList;
    }
    public Set<String> getAllServiceTypes() {
        return serviceTypes;
    }

    public void fillCompleteListFromApiData() {
        Task<Void> downloadingApiData = getDownloadApiDataTask();

        userInterfaceController.bindProgressBarWith(downloadingApiData);

        Thread th = new Thread(downloadingApiData);
        th.start();
    }


    private void fillMetadata() {
        completeList.getCountries().forEach(country -> {
            country.getProviders().forEach(provider -> {
                serviceTypes.addAll(provider.getServiceTypes());
                System.out.println(serviceTypes);
                System.out.println("rasars");
            });
        });
    }

    private Task<Void> getDownloadApiDataTask() {
        return new Task<>() {
            @Override
            protected Void call() {
                try {
                    completeList.downloadApiData();
                    userInterfaceController.fillFiltersAndDisplay();
                    fillMetadata();
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