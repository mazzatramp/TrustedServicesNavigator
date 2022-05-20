package com.tsn.trustedservicesnavigator;

import javafx.scene.control.TreeItem;

public class ProviderTreeItem extends TreeItem<String> {

    private final Provider referredProvider;

    public ProviderTreeItem(Provider referredProvider) {
        this.referredProvider = referredProvider;

        setValue(referredProvider.getName());

        for (Service serviceToShow : referredProvider.getServices()) {
            this.getChildren().add(new ServiceTreeItem(serviceToShow));
        }
    }

    public Provider getReferredProvider() {
        return referredProvider;
    }
}
