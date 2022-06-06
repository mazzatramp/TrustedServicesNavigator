package com.trustedservices.navigator.components;

import com.trustedservices.domain.TrustedListEntity;
import javafx.scene.control.Label;

/**
 * Creates Labels for each TrustedEntity carrying the name and a reference of it, which is useful to get information about the clicked item
 * @see DisplayPane
 * @see InfoPane
 */
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
