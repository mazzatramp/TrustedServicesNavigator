package com.tsn.trustedservicesnavigator;

import javafx.application.Platform;
import javafx.concurrent.Task;

public class UserInterfaceController {
    public DisplayPane displayPane;
    public FilterSelectionAccordion filterSelection;
    private NavigationMediator navigationMediator;

    public void fillFiltersAndDisplay() {
        //runLater is needed in order to avoid issues adding a lot of nodes at once
        Platform.runLater(() -> {
            TrustedList dataAtStartupTime = navigationMediator.getCompleteList();
            displayPane.fillDisplayTreeView(dataAtStartupTime);
            filterSelection.fillCountryAndProvidersFilterTreeView(dataAtStartupTime);
        });
    }

    public void handleFilterClick() {
        if (displayPane.canShowResults()) {
            navigationMediator.readActiveFiltersFrom(filterSelection);
            TrustedList filteredList = navigationMediator.getFilteredList();
            displayPane.fillDisplayTreeView(filteredList);
        }
    }

    public void setNavigationMediator(NavigationMediator navigationMediator) {
        this.navigationMediator = navigationMediator;
    }

    public void bindProgressBarWith(Task task) {
        displayPane.bindProgressBarWith(task);
    }
}