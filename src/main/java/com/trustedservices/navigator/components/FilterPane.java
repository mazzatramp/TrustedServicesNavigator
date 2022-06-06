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

/**
 * Abstract class that will be implemented in ProvideFilterPane, ServiceFilterPane and StatusFilterPane
 */
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
            System.err.println("Error while setting the scene in for Filter Pane in FXMLLoader");
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize() {
        setHyperlinksAsAlwaysActive();
        setHyperlinksOnAction();
    }

    /**
     * This method sets the action for the two Hyperlinks by using the method setSelectedForAll, that checks or unchecks all the
     * checkboxes upon given boolean value.
     */
    private void setHyperlinksOnAction() {
        selectAll.setOnAction(actionEvent -> setSelectedForAll(true));
        deselectAll.setOnAction(actionEvent -> setSelectedForAll(false));
    }

    /**
     * Sets all the Hyperlinks as never visited, maintains the same color even after clicks.
     */
    private void setHyperlinksAsAlwaysActive() {
        selectAll.visitedProperty().bind(new SimpleBooleanProperty(false));
        deselectAll.visitedProperty().bind(new SimpleBooleanProperty(false));
    }

    public abstract void fillWith(TrustedList dataToShow);
    public abstract Set<String> getSelectedItems();
    public abstract Set<String> getUnselectedItems();
    public abstract void disable(Collection<String> toDisable);
    protected abstract void setAllCheckBoxStatus(boolean status);

    /**
     * @param changedFilterValue the string of the selected checkbox on its filter pane. Upon selection, if the user is not using the select all feature,
     *                           the method add or removes the parameter from the whitelist of the associated filter.
     *                           After, it uses the method refreshOtherPanes for the dynamic filters, a method that disables checkboxes when
     *                           those would return no result upon filtering.
     *                           The parameter selecting all disables the listener the method SetSelectedForAll
     */
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

    /**
     * The method is used by the two Hyperlinks SelectAll and Deselect all to change the status of the checkboxes, the
     * @param checkStatus is false for deselection, and true for selection, and it's an argument for the function setAllCheckBoxStatus,
     *                    implemented in each filter pane.
     * Then the method sets the whitelist by getting the selected items.
     * The parameter selectingAll is used to not trigger the refresh of the filters for each selection.
     */
    public void setSelectedForAll(boolean checkStatus) {
        selectingAll = true;
        setAllCheckBoxStatus(checkStatus);
        selectingAll = false;
        associatedFilter.setWhitelist(this.getSelectedItems());
        refreshOtherPanes();
    }

    /**
     * Gets the filter accordion that contains all the filters, then casts its method refreshPanesExcept and passes itself a
     * parameter.
     * @see FilterPanesAccordion
     */
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
