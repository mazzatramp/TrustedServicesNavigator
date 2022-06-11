package com.trustedservices.navigator.components;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.util.Callback;

/**
 * A class containing a CheckBoxTreeCell implementation that supports the disabled property.
 *
 * It is recommended to use the class together with FilterTreeItem.
 *
 * The Cell is listening to the FilterTreeItem disabled property, and render the cell as disabled
 * when the property is set to true.
 *
 * @see FilterTreeItem
 */
public class FilterTreeCell<T> extends CheckBoxTreeCell<T> implements ChangeListener<Boolean>
{
    protected SimpleBooleanProperty disabledProperty;

    /**
     * Creates a cell factory for use in a TreeView control. This cell factory assumes that the TreeView root and all
     * children are instances of FilterTreeItem or CheckBoxTreeItem, rather than the default TreeItem class that is
     * used normally. Using CheckBoxTreeItem is safe, but disabling it isn't possible.
     *
     * @return A Callback that will return a TreeCell that is able to work on the type of element contained within
     * the TreeView root, and all of its children (recursively).
     */
    public static Callback<TreeView<String>, TreeCell<String>> forTreeView() {
        return treeView -> new FilterTreeCell<>();
    }

    public FilterTreeCell() {
        super();
        this.disabledProperty = new SimpleBooleanProperty(false);
    }

    /**
     * The updateItem method should not be called by developers, but it is the best method for developers to override to allow for them to customise the visuals of the cell.
     * @param item The new item for the cell
     * @param empty whether or not this cell represents data from the list. If it is empty,
     *              then it does not represent any domain data, but is a cell being used to render an "empty" row
     */
    @Override
    public void updateItem(T item, boolean empty)
    {
        super.updateItem(item, empty);

        if (!empty && hasFilterTreeItemProperty()) {
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
                && (this.treeItemProperty().getValue() instanceof FilterTreeItem);
    }

    /**
     * This method is called when a FilterTreeItem disabled property changes, and updates the cell's disabled property.
     * @param observable The ObservableValue which value changed
     * @param oldVal The old value
     * @param newVal The new value
     */
    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldVal, Boolean newVal) {
        this.setDisable(newVal);
    }
}
