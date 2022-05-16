package com.tsn.trustedservicesnavigator;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

public class UserInterfaceController {
    private NavigationMediator navigationMediator;

    @FXML
    private TreeView<String> displayed;
    @FXML
    private TreeView<CheckBox> countryProviderFilterSelection;
    @FXML
    private ProgressBar progressBar;

    @FXML
    public void initialize() {
        displayed.setRoot(new TreeItem<>("Navigation Root"));
        countryProviderFilterSelection.setRoot(new TreeItem<>(new CheckBox("Filter Root")));
    }

    public void bindProgressBarWith(Task<Void> task) {
        progressBar.visibleProperty().bind(task.runningProperty());
        progressBar.progressProperty().bind(task.progressProperty());
    }

    public void fillMenus() {
        //runLater is needed in order to avoid issues adding a lot of nodes at once
        Platform.runLater(() -> {
            fillNavigationTreeView();
            fillCountryAndProvidersFilterTreeView();
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

    private void fillNavigationTreeView() {
        TreeItem<String> countryTreeItem, providerTreeItem, serviceTreeItem;
        TrustedList dataToShow = navigationMediator.getFilteredList();
        System.out.println(dataToShow.getCountries());

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

    public void updateFilters() {
        if (!progressBar.isVisible()) {
            fillNavigationTreeView();
        }
    }

    public List<String> getSelectedCountries() {
        ObservableList<TreeItem<CheckBox>> countries = countryProviderFilterSelection.getRoot().getChildren();
        List<String> selectedCountries = new ArrayList<>(0);

        for (TreeItem<CheckBox> countryTreeItem : countries) {
            CheckBox countryCheckBox = countryTreeItem.getValue();
            if (countryCheckBox.isSelected()) {
                selectedCountries.add(countryCheckBox.getText());
            }
        }
        return selectedCountries;
    }

    public void setNavigationMediator(NavigationMediator navigationMediator) {
        this.navigationMediator = navigationMediator;
    }
}