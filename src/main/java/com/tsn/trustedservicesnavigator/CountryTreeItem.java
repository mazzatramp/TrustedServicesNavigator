package com.tsn.trustedservicesnavigator;

import javafx.scene.control.TreeItem;

public class CountryTreeItem extends TreeItem<String> {

    private final Country referredCountry;

    public CountryTreeItem(Country country) {
        this.referredCountry = country;
        setValue(referredCountry.getName());

        referredCountry.getProviders().forEach(provider -> {
            this.getChildren().add(new ProviderTreeItem(provider));
        });
    }

    public Country getReferredCountry() {
        return referredCountry;
    }
}
