package com.trustedservices.navigator.components;

import com.trustedservices.domain.Country;
import com.trustedservices.domain.Provider;
import com.trustedservices.domain.TrustedList;
import com.trustedservices.domain.TrustedListEntity;
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

    private static final String RESOURCE_FILE_NAME = "display-pane.fxml";

    @FXML private TreeView<Label> displayed;
    @FXML private Label emptyDisplayMessage;
    @FXML private ProgressBar downloadBar;

    private WindowController windowController;

    public DisplayPane() {
        loadFXMLResource();
        initializeEmptyDisplayMessage();
        initializeDownloadBar();

        EventHandler<MouseEvent> mouseEventEventHandler = this::handleMouseClick;
        displayed.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEventEventHandler);
    }

    private void initializeDownloadBar() {
        downloadBar.getParent().visibleProperty().bind(downloadBar.visibleProperty());
    }

    private void initializeEmptyDisplayMessage() {
        emptyDisplayMessage.setText(
                "There are no services respecting the selected filter.\n" +
                "Please change or reset filters."
        );
    }

    private void loadFXMLResource() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(DisplayPane.RESOURCE_FILE_NAME));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            System.err.println("Exception in loading fxml resource.\n" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void bindProgressBarWith(Task<Void> downloadTask) {
        downloadBar.visibleProperty().bind(downloadTask.runningProperty());
        downloadBar.progressProperty().bind(downloadTask.progressProperty());
    }

    public void fillWith(TrustedList dataToShow) {
        emptyDisplayMessage.setVisible(dataToShow.isEmpty());
        TreeItem<Label> root = getRootFrom(dataToShow);
        displayed.setRoot(root);
    }

    private TreeItem<Label> getRootFrom(TrustedList dataToShow) {
        TreeItem<Label> root = new TreeItem<>();
        dataToShow.getCountries().forEach(country -> {
            TreeItem<Label> countryTreeItem = newCountryItemFrom(country);
            root.getChildren().add(countryTreeItem);
        });
        return root;
    }

    private TreeItem<Label> newCountryItemFrom(Country country) {
        TreeItem<Label> countryTreeItem = createTreeItemFrom(country);

        country.getProviders().forEach(provider -> {
            TreeItem<Label> providerTreeItem = createProviderItemFrom(provider);
            countryTreeItem.getChildren().add(providerTreeItem);
        });

        return countryTreeItem;
    }

    private TreeItem<Label> createProviderItemFrom(Provider provider) {
        TreeItem<Label> providerTreeItem = createTreeItemFrom(provider);

        provider.getServices().forEach(service -> {
            TreeItem<Label> serviceTreeItem = createTreeItemFrom(service);
            providerTreeItem.getChildren().add(serviceTreeItem);
        });

        return providerTreeItem;
    }

    private TreeItem<Label> createTreeItemFrom(TrustedListEntity entity) {
        return new TreeItem<>(new TrustedEntityLabel(entity));
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
                windowController.openInfoPaneWithInfo(selected.getRefereed().getInformation());
            }
        }
    }
}
