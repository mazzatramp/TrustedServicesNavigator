package com.tsn.trustedservicesnavigator;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class UserInterfaceController {
    @FXML
    private DisplayPane displayPane;
    @FXML
    private FilterSelectionAccordion filterSelection;
    @FXML
    public Hyperlink resetFilters;

    private NavigationMediator navigationMediator;

    public void fillFiltersAndDisplay() {
        //runLater is needed in order to avoid issues adding a lot of nodes at once
        Platform.runLater(() -> {
            TrustedList dataAtStartupTime = navigationMediator.getCompleteList();
            displayPane.fillDisplayTreeView(dataAtStartupTime);
            filterSelection.fillCountryAndProvidersFilterTreeView(dataAtStartupTime);
            filterSelection.fillServiceTypesFilterTreeView(dataAtStartupTime);
            filterSelection.fillStatusTreeView(dataAtStartupTime);
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
        this.filterSelection.setNavigationMediator(navigationMediator);
    }

    public void bindProgressBarWith(Task<Void> task) {
        displayPane.bindProgressBarWith(task);
    }

    public void resetAllFilter() {
        filterSelection.resetFilters();
    }

    public FilterSelectionAccordion getFilterAccordion() {
        return this.filterSelection;
    }
}