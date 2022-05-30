package com.trustedservices.navigator.components;

import com.trustedservices.domain.TrustedList;
import com.trustedservices.navigator.filters.Filter;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Control;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.List;

public abstract class FilterPane extends TitledPane {
    @FXML
    private AnchorPane filterView;
    @FXML
    private Hyperlink selectAll;
    @FXML
    private Hyperlink deselectAll;

    private Filter associatedFilter;

    public FilterPane() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("filter-pane.fxml"));
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

    protected abstract void setSelectionStatusForAll(boolean status);

    public abstract void fillWith(TrustedList dataToShow);

    public abstract List<String> getSelected();
    public abstract List<String> getUnselected();
    public abstract void disable(List<String> toDisable);

    protected void setFilterView(Control control) {
        AnchorPane.setTopAnchor(control, 0.0);
        AnchorPane.setLeftAnchor(control, 0.0);
        AnchorPane.setRightAnchor(control, 0.0);
        AnchorPane.setBottomAnchor(control, 0.0);
        filterView.getChildren().removeAll();
        filterView.getChildren().add(control);
    }

    public ChangeListener<Boolean> getSelectionListener() {
        return (value, oldValue, newValue) -> {
            FilterSelectionAccordion filterSelectionAccordion = (FilterSelectionAccordion) this.getParent();
            filterSelectionAccordion.refreshFilters();
        };
    }

    public Filter getAssociatedFilter() {
        return associatedFilter;
    }

    public void setAssociatedFilter(Filter associatedFilter) {
        this.associatedFilter = associatedFilter;
    }
}
