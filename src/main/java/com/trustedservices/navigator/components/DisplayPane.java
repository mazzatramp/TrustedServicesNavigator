package com.trustedservices.navigator.components;

import com.trustedservices.domain.*;
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

/**
 * This class builds and fills the panel that shows the complete TrustedList and the results after filtering.
 * It's organized in a TreeView, using the classes of the domain, following their enclosure.
 */
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

    /**
     * Creates a Loading Bar
     */
    private void initializeDownloadBar() {
        downloadBar.getParent().visibleProperty().bind(downloadBar.visibleProperty());
    }

    private void initializeEmptyDisplayMessage() {
        emptyDisplayMessage.setText(
            "There are no services respecting the selected filter.\n" +
            "Please change or reset filters."
        );
    }

    /**
     * Function that asks for data to fill the display
     * @see com.trustedservices.navigator.TrustedServicesNavigatorApplication
     */
    private void loadFXMLResource() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(DisplayPane.RESOURCE_FILE_NAME));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            System.err.println("Error while setting the scene for Display Pane with FXMLLoader.\n" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * @param downloadTask  Binds the Loading Bar to the data download
     */
    public void bindProgressBarWith(Task<Void> downloadTask) {
        downloadBar.visibleProperty().bind(downloadTask.runningProperty());
        downloadBar.progressProperty().bind(downloadTask.progressProperty());
    }

    /**
     * @param dataToShow a version of the list that is empty once you start, and filtered after. It calls the method getRoot to create
     * the rootTreeItem and countries, then sets it.
     * Shows a message if dataToShow is Empty
     */
    public void fillWith(TrustedList dataToShow) {
        emptyDisplayMessage.setVisible(dataToShow.isEmpty());
        TreeItem<Label> root = createRootFrom(dataToShow);
        displayed.setRoot(root);
    }

    /**
     * @param dataToShow creates the tree using the TrustedList. For each country it contains, it builds a branch on the Tree using
     *                   following method: createCountryItemFrom
     * @return root, the filled Tree to show in the display pane
     */
    private TreeItem<Label> createRootFrom(TrustedList dataToShow) {
        TreeItem<Label> root = new TreeItem<>();
        dataToShow.getCountries().forEach(country -> {
            TreeItem<Label> countryTreeItem = createCountryItemFrom(country);
            root.getChildren().add(countryTreeItem);
        });
        return root;
    }

    /**
     * @param country It creates a new TreeItem upon receiving a country. Each country has its own providers, so it uses the method
     *                createProviderItemFrom to create its children for each provider, and then adds the children to the Parent object.
     * @return  a Country TreeItem, that has its children, the providers, to add to the root.
     */
    private TreeItem<Label> createCountryItemFrom(Country country) {
        TreeItem<Label> countryTreeItem = createTreeItemFrom(country);

        country.getProviders().forEach(provider -> {
            TreeItem<Label> providerTreeItem = createProviderItemFrom(provider);
            countryTreeItem.getChildren().add(providerTreeItem);
        });

        return countryTreeItem;
    }

    /**
     * @param provider Creates a providerTreeItem, which contains its own services, so the function calls the method createTreeItemFrom to create the
     *                 services and add them to the parent object in the tree.
     * @return  a provider TreeItem, with its children, the services.
     */
    private TreeItem<Label> createProviderItemFrom(Provider provider) {
        TreeItem<Label> providerTreeItem = createTreeItemFrom(provider);

        provider.getServices().forEach(service -> {
            TreeItem<Label> serviceTreeItem = createTreeItemFrom(service);
            providerTreeItem.getChildren().add(serviceTreeItem);
        });

        return providerTreeItem;
    }

    /**
     * @param entity creates a TreeItem using a TrustedListEntity item, that could be a country, a provider or a service.
     * @return the new TreeItem
     */
    private TreeItem<Label> createTreeItemFrom(TrustedListEntity entity) {
        return new TreeItem<>(new TrustedEntityLabel(entity));
    }

    public boolean canShowResults() {
        return !downloadBar.isVisible();
    }

    public void setWindowController(WindowController windowController) {
        this.windowController = windowController;
    }

    /**
     * @param event Handler for click on an item.
     * This method opens upon click the info panel with the description about the selected item.
     * It uses the method getDescription, implemented in Country, Provider and Service.
     * @see Country
     * @see Provider
     * @see Service
     */
    private void handleMouseClick(MouseEvent event) {
        Node node = event.getPickResult().getIntersectedNode();
        if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
            if (displayed.getSelectionModel().getSelectedItem().getValue() instanceof TrustedEntityLabel selected) {
                windowController.openInfoPaneWithInfo(selected.getRefereed().getDescription());
            }
        }
    }
}
