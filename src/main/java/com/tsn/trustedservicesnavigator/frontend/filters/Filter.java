package com.tsn.trustedservicesnavigator.frontend.filters;

import com.tsn.trustedservicesnavigator.backend.TrustedList;

public abstract class Filter {
    public abstract void applyTo(TrustedList listToFilter);
}

