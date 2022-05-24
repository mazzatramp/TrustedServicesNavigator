package com.tsn.trustedservicesnavigator;

import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;

public class TrustedEntityLabel extends Label {
    private final TrustedEntity refereed;

    public TrustedEntityLabel(TrustedEntity refereed) {
        this.refereed = refereed;
        this.setText(refereed.getName());
    }

    public TrustedEntity getRefereed() {
        return refereed;
    }
}
