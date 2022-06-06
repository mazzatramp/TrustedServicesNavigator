package com.trustedservices.navigator.components;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.CheckBoxTreeItem;

/**
 * The dynamic filter disables the items of the filter pane that if added as a search parameter would get no results. This feature
 * has not been implemented on a CheckBoxTreeItem, and this class has this purpose.
 * @see FilterTreeCell
 */
public class FilterTreeItem<T> extends CheckBoxTreeItem<T> {
    /**
     * It declares a new boolean property that is implemented in
     * @see FilterTreeCell
     */
    public SimpleBooleanProperty disabledProperty = new SimpleBooleanProperty(false);

    public FilterTreeItem() {
        super();
    }

    public FilterTreeItem(T t) {
        super(t);
    }

    public boolean isDisabled() {
        return disabledProperty.get();
    }

    public void setDisabled(boolean disabled) {
        disabledProperty.set(disabled);
    }
}
