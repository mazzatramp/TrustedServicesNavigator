package com.trustedservices.navigator.components;

import com.trustedservices.domain.TrustedListEntity;
import javafx.scene.control.Label;

public class TrustedEntityLabel extends Label {
    private final TrustedListEntity refereed;

    public TrustedEntityLabel(TrustedListEntity refereed) {
        this.refereed = refereed;
        this.setText(refereed.getName());
    }

    public TrustedListEntity getRefereed() {
        return refereed;
    }
}
