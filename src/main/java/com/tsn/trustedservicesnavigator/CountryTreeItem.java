package com.tsn.trustedservicesnavigator;

import javafx.scene.control.TreeItem;

public class CountryTreeItem extends TreeItem<String> {

    private final Country referredCountry;

    public CountryTreeItem(Country country) {
        this.referredCountry = country;

        setValue(country.getName());

        for (Provider providerToShow : country.getProviders()) {
            this.getChildren().add(new ProviderTreeItem(providerToShow));
        }
    }

    public Country getReferredCountry() {
        return referredCountry;
    }
}
