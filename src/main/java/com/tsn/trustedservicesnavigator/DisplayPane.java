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
    private ProgressBar downloadBar;

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

    public void bindProgressBarWith(Task<Void> downloadTask) {
        downloadBar.visibleProperty().bind(downloadTask.runningProperty());
        downloadBar.progressProperty().bind(downloadTask.progressProperty());
    }

    public void fillDisplayTreeView(TrustedList dataToShow) {
        TreeItem<String> root = new TreeItem<>();
        dataToShow.getCountries().forEach(country -> {
            TrustedEntityTreeItem countryTreeItem = new TrustedEntityTreeItem(country);
            country.getProviders().forEach(provider -> {
                TrustedEntityTreeItem providerTreeItem = new TrustedEntityTreeItem(provider);
                provider.getServices().forEach(service -> {
                    TrustedEntityTreeItem serviceTreeItem = new TrustedEntityTreeItem(service);
                    providerTreeItem.getChildren().add(serviceTreeItem);
                });
                countryTreeItem.getChildren().add(providerTreeItem);
            });
            root.getChildren().add(countryTreeItem);
        });
        displayed.setRoot(root);
    }

    public boolean canShowResults() {
        return !downloadBar.isVisible();
    }
}
