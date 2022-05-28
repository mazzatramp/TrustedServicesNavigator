package com.trustedservicesnavigator.frontend.filters;

import com.trustedservicesnavigator.domain.TrustedList;

import java.util.ArrayList;
import java.util.List;

public abstract class Filter {

    protected List<String> whitelist;

    public Filter() {
        this.whitelist = new ArrayList<>(0);
    }

    public void applyTo(TrustedList listToFilter) {
        if (!whitelist.isEmpty())
            filterByWhitelist(listToFilter);
    }

    public void setWhitelist(List<String> whitelist) {
        this.whitelist = whitelist;
    }

    public List<String> getWhitelist() {
        return whitelist;
    }

    protected abstract void filterByWhitelist(TrustedList listToFilter);

    public boolean wouldHaveZeroResultsAppliedTo(TrustedList filteredList) {
        applyTo(filteredList);
        return filteredList.getCountries().isEmpty();
    }
}

