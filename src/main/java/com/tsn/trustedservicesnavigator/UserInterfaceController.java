package com.tsn.trustedservicesnavigator;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;

import java.util.ArrayList;
import java.util.List;

public class UserInterfaceController {
    private NavigationMediator navigationMediator;

    @FXML
    private TreeView<String> displayed;
    @FXML
    private TreeView<String> countryProviderFilterSelection;
    @FXML
    private ProgressBar progressBar;

    @FXML
    public void initialize() {
        displayed.setRoot(new TreeItem<>("Navigation Root"));
        countryProviderFilterSelection.setRoot(new CheckBoxTreeItem<>("Country Root"));
        countryProviderFilterSelection.setCellFactory(CheckBoxTreeCell.<String>forTreeView());
    }

    public void bindProgressBarWith(Task<Void> task) {
        progressBar.visibleProperty().bind(task.runningProperty());
        progressBar.progressProperty().bind(task.progressProperty());
    }

    public void fillFiltersAndDisplay() {
        //runLater is needed in order to avoid issues adding a lot of nodes at once
        Platform.runLater(() -> {
            fillDisplayTreeView();
            fillCountryAndProvidersFilterTreeView();
        });
    }

    private void fillCountryAndProvidersFilterTreeView() {
        CheckBoxTreeItem<String> countryTreeItem, providerTreeItem;
        TrustedList dataToShow = navigationMediator.getCompleteList();

        for (Country country : dataToShow.getCountries()) {
            countryTreeItem = new CheckBoxTreeItem(country.getName());
            for (Provider provider : country.getProviders()) {
                providerTreeItem = new CheckBoxTreeItem(provider.getName());
                countryTreeItem.getChildren().add(providerTreeItem);
            }
            countryProviderFilterSelection.getRoot().getChildren().add(countryTreeItem);
        }

    }

    private void fillDisplayTreeView() {
        TreeItem<String> countryTreeItem, providerTreeItem, serviceTreeItem;
        TrustedList dataToShow = navigationMediator.getFilteredList();

        displayed.setRoot(new TreeItem<>());
        for (Country countryToShow : dataToShow.getCountries()) {
            countryTreeItem = new TreeItem<>(countryToShow.getName());
            for (Provider providerToShow : countryToShow.getProviders()) {
                providerTreeItem = new TreeItem<>(providerToShow.getName());
                for (Service serviceToShow : providerToShow.getServices()) {
                    serviceTreeItem = new TreeItem<>(serviceToShow.getName());
                    providerTreeItem.getChildren().add(serviceTreeItem);
                }
                countryTreeItem.getChildren().add(providerTreeItem);
            }
            displayed.getRoot().getChildren().add(countryTreeItem);
        }
    }

    public void handleFilterClick() {
        if (!progressBar.isVisible()) {
            fillDisplayTreeView();
        }
    }

    public List<String> getSelectedCountries() {
        ObservableList<TreeItem<String>> countries = countryProviderFilterSelection.getRoot().getChildren();
        List<String> selectedCountries = new ArrayList<>(0);

        for (TreeItem<String> countryTreeItem : countries) {
            CheckBoxTreeItem c = (CheckBoxTreeItem) countryTreeItem;
            String countryCheckBox = (String) c.getValue();
            if (c.isSelected()) {
                selectedCountries.add(countryCheckBox);
            }
        }
        return selectedCountries;
    }

    public void setNavigationMediator(NavigationMediator navigationMediator) {
        this.navigationMediator = navigationMediator;
    }
}