package com.trustedservices.navigator.components;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.CheckBoxTreeItem;

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
