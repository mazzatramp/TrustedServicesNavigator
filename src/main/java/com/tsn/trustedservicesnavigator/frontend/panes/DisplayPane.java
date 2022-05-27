package com.tsn.trustedservicesnavigator.frontend.panes;

import com.tsn.trustedservicesnavigator.backend.Provider;
import com.tsn.trustedservicesnavigator.frontend.UserInterfaceController;
import com.tsn.trustedservicesnavigator.frontend.panes.treeitems.CountryTreeItem;
import com.tsn.trustedservicesnavigator.backend.TrustedList;
import com.tsn.trustedservicesnavigator.frontend.panes.treeitems.ProviderTreeItem;
import com.tsn.trustedservicesnavigator.frontend.panes.treeitems.ServiceTreeItem;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ServiceLoader;

public class DisplayPane extends AnchorPane {
    @FXML
    private TreeView<String> displayed;
    @FXML
    private ProgressBar downloadBar;

    private UserInterfaceController userInterfaceController;

    public DisplayPane() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("display-pane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        EventHandler<MouseEvent> mouseEventEventHandler = (MouseEvent e) -> handleMouseClick(e);
        displayed.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEventEventHandler);
    }

    public void bindProgressBarWith(Task<Void> downloadTask) {
        downloadBar.visibleProperty().bind(downloadTask.runningProperty());
        downloadBar.progressProperty().bind(downloadTask.progressProperty());
    }

    public void fillDisplayTreeView(TrustedList dataToShow) {
        displayed.setRoot(new TreeItem<>());
        dataToShow.getCountries().forEach(country -> {
            displayed.getRoot().getChildren().add(new CountryTreeItem(country));
        });
    }

    public boolean canShowResults() {
        return !downloadBar.isVisible();
    }

    public void setUserInterfaceController(UserInterfaceController userInterfaceController) {
        this.userInterfaceController = userInterfaceController;
    }

    private void handleMouseClick(MouseEvent event) {
        Node node = event.getPickResult().getIntersectedNode();
        if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
            if (displayed.getSelectionModel().getSelectedItem() instanceof ProviderTreeItem selected) {
                userInterfaceController.openInfoPaneWithInfo(selected.getFormattedText(), null);
            } else if (displayed.getSelectionModel().getSelectedItem() instanceof ServiceTreeItem selected) {
                String parent = ((ProviderTreeItem)selected.getParent()).getFormattedText();
                userInterfaceController.openInfoPaneWithInfo(parent, selected.getFormattedText());
            }
        }
    }
}
