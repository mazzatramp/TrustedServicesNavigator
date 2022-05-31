package com.trustedservices.navigator.components;

import com.trustedservices.domain.TrustedEntity;
import javafx.scene.control.Label;

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
