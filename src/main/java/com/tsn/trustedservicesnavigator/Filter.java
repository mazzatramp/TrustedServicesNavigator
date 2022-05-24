package com.tsn.trustedservicesnavigator;

import java.util.List;

public abstract class Filter {
    public abstract void applyTo(TrustedList listToFilter);
    public abstract void setWhitelist(List<String> whitelist);
}

