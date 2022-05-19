package com.tsn.trustedservicesnavigator;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DisplayPane extends AnchorPane {

    @FXML
    private TreeView<String> displayed;

    @FXML
    private ProgressBar progressBar;

    public DisplayPane() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("display-pane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void bindProgressBarWith(Task<Void> task) {
        progressBar.visibleProperty().bind(task.runningProperty());
        progressBar.progressProperty().bind(task.progressProperty());
    }

    public void fillDisplayTreeView(TrustedList dataToShow) {
        TreeItem<String> countryTreeItem, providerTreeItem, serviceTreeItem;

        displayed.setRoot(new TreeItem<>());
        for (Country countryToShow : dataToShow.getCountries()) {
            countryTreeItem = getFilledCountryItem(countryToShow);
            displayed.getRoot().getChildren().add(countryTreeItem);
        }
    }

    private TreeItem<String> getFilledCountryItem(Country countryToShow) {
        TreeItem<String> countryTreeItem, providerTreeItem;

        countryTreeItem = new TreeItem<>(countryToShow.getName());
        for (Provider providerToShow : countryToShow.getProviders()) {
            providerTreeItem = getFilledProviderItem(providerToShow);
            countryTreeItem.getChildren().add(providerTreeItem);
        }
        return countryTreeItem;
    }

    private TreeItem<String> getFilledProviderItem(Provider providerToShow) {
        TreeItem<String> providerTreeItem, serviceTreeItem;

        providerTreeItem = new TreeItem<>(providerToShow.getName());
        for (Service serviceToShow : providerToShow.getServices()) {
            serviceTreeItem = new TreeItem<>(serviceToShow.getName());
            providerTreeItem.getChildren().add(serviceTreeItem);
        }
        return providerTreeItem;
    }

    public boolean canShowResults() {
        return !progressBar.isVisible();
    }
}
