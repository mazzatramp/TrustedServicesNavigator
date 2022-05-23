package com.tsn.trustedservicesnavigator;

import javafx.scene.control.TreeItem;

public class ProviderTreeItem extends TreeItem<String> {

    private final Provider referredProvider;

    public ProviderTreeItem(Provider referredProvider) {
        this.referredProvider = referredProvider;

        setValue(referredProvider.getName());

        referredProvider.getServices().forEach(service -> {
            this.getChildren().add(new ServiceTreeItem(service));
        });
    }

    public Provider getReferredProvider() {
        return referredProvider;
    }
}
