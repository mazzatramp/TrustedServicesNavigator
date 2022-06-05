package com.trustedservices.navigator.components;

import com.trustedservices.domain.TrustedList;
import com.trustedservices.navigator.filters.Filter;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Control;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

public abstract class FilterPane extends TitledPane {
    private static final String FXML_RESOURCE_FILE_NAME = "filter-pane.fxml";

    @FXML private AnchorPane filterView;
    @FXML private Hyperlink selectAll;
    @FXML private Hyperlink deselectAll;

    private Filter associatedFilter;
    private boolean selectingAll;

    public FilterPane() {
        loadFXMLResource();
    }

    private void loadFXMLResource() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXML_RESOURCE_FILE_NAME));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize() {
        setHyperlinksAsAlwaysActive();
        setHyperlinksOnAction();
    }

    private void setHyperlinksOnAction() {
        selectAll.setOnAction(actionEvent -> setSelectedForAll(true));
        deselectAll.setOnAction(actionEvent -> setSelectedForAll(false));
    }

    private void setHyperlinksAsAlwaysActive() {
        selectAll.visitedProperty().bind(new SimpleBooleanProperty(false));
        deselectAll.visitedProperty().bind(new SimpleBooleanProperty(false));
    }

    public abstract void fillWith(TrustedList dataToShow);
    public abstract Set<String> getSelectedItems();
    public abstract Set<String> getUnselectedItems();
    public abstract void disable(Collection<String> toDisable);
    protected abstract void setAllCheckBoxStatus(boolean status);

    public ChangeListener<Boolean> handleFilterChange(String changedFilterValue) {
        return (value, wasSelected, isSelected) -> {
            if (!selectingAll) {
                if (isSelected)
                    this.getAssociatedFilter().getWhitelist().add(changedFilterValue);
                else
                    this.getAssociatedFilter().getWhitelist().remove(changedFilterValue);
                refreshOtherPanes();
            }
        };
    }

    public void setSelectedForAll(boolean checkStatus) {
        selectingAll = true;
        setAllCheckBoxStatus(checkStatus);
        selectingAll = false;
        associatedFilter.setWhitelist(this.getSelectedItems());
        refreshOtherPanes();
    }

    private void refreshOtherPanes() {
        FilterPanesAccordion filterPanes = (FilterPanesAccordion) this.getParent();
        filterPanes.refreshPanesExcept(this);
    }

    protected void setFilterView(Control control) {
        AnchorPane.setTopAnchor(control, 0.0);
        AnchorPane.setLeftAnchor(control, 0.0);
        AnchorPane.setRightAnchor(control, 0.0);
        AnchorPane.setBottomAnchor(control, 0.0);
        filterView.getChildren().add(control);
    }

    public Filter getAssociatedFilter() {
        return associatedFilter;
    }

    protected void setAssociatedFilter(Filter associatedFilter) {
        this.associatedFilter = associatedFilter;
    }
}
