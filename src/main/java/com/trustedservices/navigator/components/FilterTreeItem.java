package com.trustedservices.navigator.components;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.CheckBoxTreeItem;

/**
 * CheckBoxTreeItem subclass that implements the disabled property. Useful when used in conjunction of FilterTreeCell.
 *
 * The disabled property changes are listened by a FilterTreeCell.
 *
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
