package com.trustedservices.navigator.components;

import com.trustedservices.domain.TrustedList;
import com.trustedservices.navigator.WindowController;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class DisplayPane extends AnchorPane {

    @FXML
    private TreeView<Label> displayed;

    @FXML
    private ProgressBar downloadBar;

    private WindowController windowController;

    public DisplayPane() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("display-pane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        EventHandler<MouseEvent> mouseEventEventHandler = this::handleMouseClick;
        displayed.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEventEventHandler);
    }

    public void bindProgressBarWith(Task<Void> downloadTask) {
        downloadBar.visibleProperty().bind(downloadTask.runningProperty());
        downloadBar.progressProperty().bind(downloadTask.progressProperty());
    }

    public void fillWith(TrustedList dataToShow) {
        TreeItem<Label> root = new TreeItem<>();
        dataToShow.getCountries().forEach(country -> {
            TrustedEntityLabel countryLabel = new TrustedEntityLabel(country);
            TreeItem<Label> countryTreeItem = new TreeItem<>(countryLabel);
            country.getProviders().forEach(provider -> {
                TrustedEntityLabel providerLabel = new TrustedEntityLabel(provider);
                TreeItem<Label> providerTreeItem = new TreeItem<>(providerLabel);
                provider.getServices().forEach(service -> {
                    TrustedEntityLabel serviceLabel = new TrustedEntityLabel(service);
                    TreeItem<Label> serviceTreeItem = new TreeItem<>(serviceLabel);
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

    public void setWindowController(WindowController windowController) {
        this.windowController = windowController;
    }

    private void handleMouseClick(MouseEvent event) {
        Node node = event.getPickResult().getIntersectedNode();
        if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
            if (displayed.getSelectionModel().getSelectedItem().getValue() instanceof TrustedEntityLabel selected) {
                windowController.openInfoPaneWithInfo(selected.getInformation());
            }
        }
    }
}
