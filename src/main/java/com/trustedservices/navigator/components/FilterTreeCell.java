package com.trustedservices.navigator.components;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.util.Callback;

/**
 * This class implements the disableProperty on a single CheckBoxTreeCell
 */
public class FilterTreeCell<T> extends CheckBoxTreeCell<T> implements ChangeListener<Boolean>
{
    protected SimpleBooleanProperty disabledProperty;

    @Override
    public void updateItem(T item, boolean empty)
    {
        super.updateItem(item, empty); //sets item as treeItemProperty

        if (hasFilterTreeItemProperty()) {
            bindDisabledProperty();
            this.setDisable(disabledProperty.get());
        }
    }

    private void bindDisabledProperty() {
        if (disabledProperty != null)
            disabledProperty.removeListener(this);
        
        FilterTreeItem<T> filterTreeItem = (FilterTreeItem<T>) this.treeItemProperty().getValue();
        disabledProperty = filterTreeItem.disabledProperty;
        disabledProperty.addListener(this);
    }

    private boolean hasFilterTreeItemProperty() {
        return (this.treeItemProperty() != null)
                && (this.treeItemProperty().getValue() != null)
                && (this.treeItemProperty().getValue() instanceof FilterTreeItem<T>);
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldVal, Boolean newVal) {
        this.setDisable(newVal);
    }

    public static Callback<TreeView<String>, TreeCell<String>> forTreeView() {
        return tTreeView -> new FilterTreeCell<>();
    }
}
