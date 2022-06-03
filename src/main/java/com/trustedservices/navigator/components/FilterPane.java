package com.trustedservices.navigator.components;

import com.trustedservices.domain.TrustedList;
import com.trustedservices.navigator.filters.Filter;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Control;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ProgressIndicator;
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
    @FXML private ProgressIndicator progressIndicator;

    private Filter associatedFilter;

    public FilterPane() {
        loadFXMLResource();

        selectAll.setOnAction(actionEvent -> setAllCheckBoxStatus(true));
        deselectAll.setOnAction(actionEvent -> setAllCheckBoxStatus(false));
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
        filterView.getChildren().add(control);
    }

    public ChangeListener<Boolean> getSelectionListener() {
        return (value, wasSelected, isSelected) -> {
            this.getAssociatedFilter().setWhitelist(this.getSelectedItems());
            FilterPanesAccordion filterPanes = (FilterPanesAccordion) this.getParent();
            filterPanes.refreshPanesExcept(this);
        };
    }

    public Filter getAssociatedFilter() {
        return associatedFilter;
    }

    protected void setAssociatedFilter(Filter associatedFilter) {
        this.associatedFilter = associatedFilter;
    }

    private void setAllCheckBoxStatus(boolean checkStatus) {
        selectAll.setVisited(false);
        deselectAll.setVisited(false);

        if (progressIndicator.isVisible())
            return;

        Thread settingThread = new Thread(new Task<Void>() {
            @Override
            protected Void call() {
                filterView.setDisable(true);
                progressIndicator.setVisible(true);

                setSelectionStatusForAll(checkStatus);

                filterView.setDisable(false);
                progressIndicator.setVisible(false);
                return null;
            }
        });
        settingThread.start();
    }

}
