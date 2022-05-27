package com.tsn.trustedservicesnavigator.frontend.panes.treeitems;

import com.tsn.trustedservicesnavigator.backend.Service;
import javafx.scene.control.TreeItem;

public class ServiceTreeItem extends TreeItem<String> {

    private final Service referredService;
    private String formattedText;

    public ServiceTreeItem(Service service) {
        this.referredService = service;
        this.setValue(referredService.getName());

        // TODO: debating if this should really go in the Service class and be done with it
        // either as a toString result or a custom method. If this was to go in the Service class
        // it needs to be cached for sure tho. See ProviderTreeItem too.
        StringBuilder builder = new StringBuilder();
        builder.append("NAME: ");
        builder.append(referredService.getName());
        builder.append("\nTYPE: ");
        builder.append(referredService.getType());
        builder.append("\nSTATUS: ");
        builder.append(referredService.getStatus());
        builder.append("\nSERVICE TYPES: ");
        for (String type : referredService.getServiceTypes())
            builder.append(type + " ");
        builder.append("\n");
        formattedText = builder.toString();
    }

    public Service getReferredService() {
        return referredService;
    }

    public String getFormattedText() { return formattedText; }
}
