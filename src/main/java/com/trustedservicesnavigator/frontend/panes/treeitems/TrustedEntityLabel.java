package com.trustedservicesnavigator.frontend.panes.treeitems;

import com.trustedservicesnavigator.domain.TrustedEntity;
import javafx.scene.control.Label;

public class TrustedEntityLabel extends Label {
    private final TrustedEntity refereed;

    public TrustedEntityLabel(TrustedEntity refereed) {
        this.refereed = refereed;
        this.setText(refereed.getInformation());
    }

    public TrustedEntity getRefereed() {
        return refereed;
    }
}
