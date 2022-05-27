package com.tsn.trustedservicesnavigator.frontend.panes.treeitems;

import com.tsn.trustedservicesnavigator.backend.Provider;
import javafx.scene.control.TreeItem;

public class ProviderTreeItem extends TreeItem<String> {

    private final Provider referredProvider;

    private String formattedText;

    public ProviderTreeItem(Provider referredProvider) {
        this.referredProvider = referredProvider;

        setValue(referredProvider.getName());

        // TODO: debating if this should really go in the Service class and be done with it
        // either as a toString result or a custom method. If this was to go in the Service class
        // it needs to be cached for sure tho. See ServiceTreeItem too.
        StringBuilder builder = new StringBuilder();
        builder.append("NAME: ");
        builder.append(referredProvider.getName());
        builder.append("\nTRUST-MARK: ");
        builder.append(referredProvider.getTrustmark());
        builder.append("\nCOUNTRY: ");
        builder.append(referredProvider.getCountryCode());
        builder.append("\n");
        formattedText = builder.toString();

        referredProvider.getServices().forEach(service -> {
            this.getChildren().add(new ServiceTreeItem(service));
        });
    }

    public Provider getReferredProvider() {
        return referredProvider;
    }
    public String getFormattedText() { return formattedText; }
}
