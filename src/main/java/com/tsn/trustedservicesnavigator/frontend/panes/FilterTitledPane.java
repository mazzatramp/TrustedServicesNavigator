package com.tsn.trustedservicesnavigator.frontend.panes;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Control;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public abstract class FilterTitledPane extends TitledPane {
    @FXML
    private AnchorPane filterView;
    @FXML
    private Hyperlink selectAll;
    @FXML
    private Hyperlink deselectAll;

    public FilterTitledPane() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("filter-titled-pane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        selectAll.setOnAction(actionEvent -> {
            setSelectionStatusForAll(true);
            selectAll.setVisited(false);
        });

        deselectAll.setOnAction(actionEvent -> {
            setSelectionStatusForAll(false);
            deselectAll.setVisited(false);
        });
    }

    protected abstract void setSelectionStatusForAll(boolean b);

    protected void setFilterView(Control control) {
        AnchorPane.setTopAnchor(control, 0.0);
        AnchorPane.setLeftAnchor(control, 0.0);
        AnchorPane.setRightAnchor(control, 0.0);
        AnchorPane.setBottomAnchor(control, 0.0);
        filterView.getChildren().add(control);
    }
}
