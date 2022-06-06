package com.trustedservices.navigator.components;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.CheckBoxTreeItem;

/**
 * We created this class to extend the CheckBoxTreeItem class because we needed the features disableProperty e and setDisabled,
 * two essential feature for the implementation of the dynamic filter, on the checkBoxes of the ProviderFilterPane Tree.
 * @see FilterTreeCell 
 */
public class FilterTreeItem<T> extends CheckBoxTreeItem<T> {
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
