package com.tsn.trustedservicesnavigator.frontend;

import com.tsn.trustedservicesnavigator.NavigationMediator;
import com.tsn.trustedservicesnavigator.backend.TrustedList;
import com.tsn.trustedservicesnavigator.frontend.panes.DisplayPane;
import com.tsn.trustedservicesnavigator.frontend.panes.FilterSelectionAccordion;
import com.tsn.trustedservicesnavigator.frontend.panes.InfoPane;
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
    private SplitPane splitPane;
    @FXML
    private InfoPane infoPane;
    @FXML
    public Hyperlink resetFilters;

    private NavigationMediator navigationMediator;

    @FXML
    public void initialize() {
        displayPane.setUserInterfaceController(this);
        infoPane.setUserInterfaceController(this);
        setInfoPaneVisible(false);
    }

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

    public void openInfoPaneWithInfo(String providerInfo, String serviceInfo) {
        infoPane.setInfo(providerInfo, serviceInfo);
        setInfoPaneVisible(true);
    }

    public void setInfoPaneVisible(boolean visible) {
        if (infoPane.isVisible() && visible) return;
        infoPane.setVisible(visible);
        if (visible) splitPane.getItems().add(infoPane);
        else splitPane.getItems().remove(infoPane);
    }

    public void bindProgressBarWith(Task task) {
        displayPane.bindProgressBarWith(task);
    }

    public void resetAllFilter() {
        filterSelection.resetFilters();
    }
}