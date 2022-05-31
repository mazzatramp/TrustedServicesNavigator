package com.trustedservices.navigator.components;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.util.Callback;

public class FilterTreeCell<T> extends CheckBoxTreeCell<T> implements ChangeListener<Boolean>
{
    protected SimpleBooleanProperty linkedDisabledProperty;

    @Override
    public void updateItem(T item, boolean empty)
    {
        super.updateItem(item, empty);
        if (item == null)
            return;

        TreeItem<T> treeItem = this.treeItemProperty().getValue();
        if(treeItem != null) {
            if(treeItem instanceof FilterTreeItem<T> filterTreeItem) {
                if(linkedDisabledProperty != null) {
                    linkedDisabledProperty.removeListener(this);
                }

                linkedDisabledProperty = filterTreeItem.disabledProperty;
                linkedDisabledProperty.addListener(this);

                this.setDisable(linkedDisabledProperty.get());
            }
        }
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldVal, Boolean newVal)
    {
        this.setDisable(newVal);
    }

    public static Callback<TreeView<String>, TreeCell<String>> forTreeView() {
        return tTreeView -> new FilterTreeCell<>();
    }
}
