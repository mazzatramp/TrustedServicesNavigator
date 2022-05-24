package com.tsn.trustedservicesnavigator;

import javafx.scene.control.TreeItem;

public class TrustedEntityTreeItem extends TreeItem<String> {
    private TrustedEntity refereed;

    public TrustedEntityTreeItem(TrustedEntity refereed) {
        this.refereed = refereed;
        this.setValue(refereed.getDisplayableName());
    }

    public TrustedEntity getRefereed() {
        return refereed;
    }
}
