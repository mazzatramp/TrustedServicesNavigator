package com.tsn.trustedservicesnavigator;

import javafx.scene.control.TreeItem;

public class ServiceTreeItem extends TreeItem<String> {

    private final Service referredService;

    public ServiceTreeItem(Service service) {
        this.referredService = service;

        this.setValue(service.getServiceId() + ": \""
                + service.getName() + "\" "
                + service.getServiceTypes() + ", "
                + service.getStatus());
    }

    public Service getReferredService() {
        return referredService;
    }
}
