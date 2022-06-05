package com.trustedservices.navigator;

import com.trustedservices.domain.TrustedList;
import com.trustedservices.navigator.components.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class WindowController {

    @FXML private DisplayPane displayPane;
    @FXML private FilterPanesAccordion filterPanes;
    @FXML private SplitPane displayAndInfoPanes;
    @FXML private InfoPane infoPane;

    @FXML public Hyperlink resetFilters;

    private NavigationController navigationController;

    @FXML
    public void initialize() {
        displayPane.setWindowController(this);
        initializeInfoPane();
    }

    public void setNavigationController(NavigationController navigationController) {
        this.navigationController = navigationController;
    }

    public void fillDisplayAndFiltersViews() {
        //runLater is needed in order to avoid issues adding a lot of nodes at once
        Platform.runLater(() -> {
            TrustedList completeList = navigationController.getCompleteList();
            displayPane.fillWith(completeList);
            filterPanes.fillFilterPanesWith(completeList);
            navigationController.getFilters().addAll(filterPanes.getAssociatedFilters());
        });
    }

    @FXML
    public void handleFilterClick() {
        if (displayPane.canShowResults()) {
            TrustedList filteredList = navigationController.getFilteredList();
            displayPane.fillWith(filteredList);
        }
    }

    @FXML
    public void handleResetFilters() {
        filterPanes.resetFilters();
        handleFilterClick();
    }

    public void openInfoPaneWithInfo(String info) {
        infoPane.setVisible(true);
        infoPane.setInfo(info);
    }

    private void initializeInfoPane() {
        infoPane.visibleProperty().addListener((value, wasVisible, isVisible) -> {
            if (isVisible)
                displayAndInfoPanes.getItems().add(infoPane);
            else
                displayAndInfoPanes.getItems().remove(infoPane);
        });
        infoPane.setVisible(false);
    }

    public void bindProgressBarWith(Task<Void> task) {
        displayPane.bindProgressBarWith(task);
    }
}
