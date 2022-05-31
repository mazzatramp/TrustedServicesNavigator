package com.trustedservices.navigator;

import com.trustedservices.domain.*;
import com.trustedservices.navigator.components.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class WindowController {
    @FXML private DisplayPane displayPane;
    @FXML private FilterSelectionAccordion filterSelection;
    @FXML private SplitPane splitPane;
    @FXML private InfoPane infoPane;
    @FXML public Hyperlink resetFilters;

    private NavigationController navigationController;

    @FXML
    public void initialize() {
        infoPane.setWindowController(this);
        displayPane.setWindowController(this);
        setInfoPaneVisible(false);
    }

    public void fillFiltersAndDisplay() {
        //runLater is needed in order to avoid issues adding a lot of nodes at once
        Platform.runLater(() -> {
            TrustedList dataAtStartupTime = navigationMediator.getCompleteList();
            displayPane.fillDisplayTreeView(dataAtStartupTime);
            filterSelection.fillFilterPanesWith(dataAtStartupTime);
        });
    }

    public void handleFilterClick() {
        if (displayPane.canShowResults()) {
            navigationMediator.updateActiveFiltersFromUserSelection();
            displayPane.fillDisplayTreeView(filteredList);
            TrustedList filteredList = navigationController.getFilteredList();
        }
    }

    public void handleResetFiltersClick() {
        filterSelection.resetFilters();
    }

    public void openInfoPaneWithInfo(List<String> info) {
        infoPane.setInfo(info);
        setInfoPaneVisible(true);
    }

    public void setInfoPaneVisible(boolean visible) {
        if (infoPane.isVisible() && visible) return;
        infoPane.setVisible(visible);
        if (visible) splitPane.getItems().add(infoPane);
        else splitPane.getItems().remove(infoPane);
    }

    public void bindProgressBarWith(Task<Void> task) {
        displayPane.bindProgressBarWith(task);
    }

    public void handleResetFiltersClick() {
        filterSelection.resetFilters();
    }

    public FilterSelectionAccordion getFilterAccordion() {
        return this.filterSelection;
    }

    public void setNavigationController(NavigationController navigationController) {
        this.navigationController = navigationController;
    }
}
