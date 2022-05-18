package com.tsn.trustedservicesnavigator;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInterfaceController {
    private NavigationMediator navigationMediator;

    @FXML
    private TreeView<String> displayed;
    @FXML
    private TreeView<CheckBox> countryProviderFilterSelection;

    @FXML
    private TreeView<CheckBox> serviceTypeFilterSelection;

    @FXML
    private ProgressBar progressBar;

    @FXML
    public void initialize() {
        displayed.setRoot(new TreeItem<>("Navigation Root"));
        countryProviderFilterSelection.setRoot(new TreeItem<>(new CheckBox("Filter Root")));
        serviceTypeFilterSelection.setRoot(new TreeItem<>(new CheckBox("Service Root")));
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
            fillServiceTypesFilterTreeView();
        });
    }

    private void fillCountryAndProvidersFilterTreeView() {
        TreeItem<CheckBox> countryTreeItem, providerTreeItem;
        TrustedList dataToShow = navigationMediator.getCompleteList();

        for (Country country : dataToShow.getCountries()) {
            countryTreeItem = new TreeItem<>(new CheckBox(country.getName()));
            for (Provider provider : country.getProviders()) {
                providerTreeItem = new TreeItem<>(new CheckBox(provider.getName()));
                countryTreeItem.getChildren().add(providerTreeItem);
            }
            countryProviderFilterSelection.getRoot().getChildren().add(countryTreeItem);
        }
    }

    private void fillServiceTypesFilterTreeView() {
        for (String serviceType : navigationMediator.getAllServiceTypes())
            serviceTypeFilterSelection.getRoot().getChildren().add(new TreeItem<>(new CheckBox(serviceType)));
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

    public Map<String, List<String>> getSelectedCountriesAndProviders() {
        ObservableList<TreeItem<CheckBox>> countries = countryProviderFilterSelection.getRoot().getChildren();
        Map<String, List<String>> selectedCountries = new HashMap<>(0);

        for (TreeItem<CheckBox> countryTreeItem : countries) {
            CheckBox countryCheckBox = countryTreeItem.getValue();
            if (countryCheckBox.isSelected()) {
                List<String> providers = new ArrayList<>();
                for (TreeItem<CheckBox> providerTreeItem : countryTreeItem.getChildren())
                    if (providerTreeItem.getValue().isSelected()) providers.add(providerTreeItem.getValue().getText());
                selectedCountries.put(countryCheckBox.getText(), providers);
            }
        }
        return selectedCountries;
    }

    public List<String> getSelectedServiceTypes() {
        return null;
    }

    public void setNavigationMediator(NavigationMediator navigationMediator) {
        this.navigationMediator = navigationMediator;
    }

    public void updateFilters(ActionEvent actionEvent) {
        if (!progressBar.isVisible()) {
            fillDisplayTreeView();
        }
    }
}