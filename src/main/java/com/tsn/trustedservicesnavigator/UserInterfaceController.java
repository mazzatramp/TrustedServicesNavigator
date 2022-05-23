package com.tsn.trustedservicesnavigator;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    }

    public void bindProgressBarWith(Task task) {
        displayPane.bindProgressBarWith(task);
    }

    public void resetAllFilter() {
        filterSelection.resetFilters();
    }
}