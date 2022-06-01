package com.trustedservices.navigator.components;

import com.trustedservices.domain.TrustedList;
import com.trustedservices.navigator.filters.Filter;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

public abstract class FilterPane extends TitledPane {
    private static final String RESOURCE_FILE_NAME = "filter-pane.fxml";

    @FXML private AnchorPane filterView;
    @FXML private Hyperlink selectAll;
    @FXML private Hyperlink deselectAll;

    private Filter associatedFilter;

    public FilterPane() {
        loadFXMLResource();

        selectAll.setOnAction(actionEvent -> {
            setSelectionStatusForAll(true);
            selectAll.setVisited(false);
        });

        deselectAll.setOnAction(actionEvent -> {
            setSelectionStatusForAll(false);
            deselectAll.setVisited(false);
        });
    }

    private void loadFXMLResource() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FilterPane.RESOURCE_FILE_NAME));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract void setSelectionStatusForAll(boolean status);

    public abstract void fillWith(TrustedList dataToShow);

    public abstract Set<String> getSelectedItems();
    public abstract Set<String> getUnselectedItems();
    public abstract void disable(Collection<String> toDisable);

    protected void setFilterView(Control control) {
        AnchorPane.setTopAnchor(control, 0.0);
        AnchorPane.setLeftAnchor(control, 0.0);
        AnchorPane.setRightAnchor(control, 0.0);
        AnchorPane.setBottomAnchor(control, 0.0);
        filterView.getChildren().removeAll();
        filterView.getChildren().add(control);
    }

    public ChangeListener<Boolean> getSelectionListener() {
        return (value, wasSelected, isSelected) -> {
            this.getAssociatedFilter().setWhitelist(this.getSelectedItems());
            FilterPanesAccordion filterPanesAccordion = (FilterPanesAccordion) this.getParent();
            filterPanesAccordion.refreshFilterPanesExcept(this);
        };
    }

    public Filter getAssociatedFilter() {
        return associatedFilter;
    }

    protected void setAssociatedFilter(Filter associatedFilter) {
        this.associatedFilter = associatedFilter;
    }
}
