package com.tsn.trustedservicesnavigator;

import java.util.List;

public class FilterController {
    private final FilterByCountry countryFilter;

    public FilterController() {
        countryFilter = new FilterByCountry();
    }

    public void setCountryFilter(List<String> countryWhitelist) {
        countryFilter.setWhitelist(countryWhitelist);
    }

    public TrustedList getFilteredDataFrom(TrustedList target) {
        TrustedList clone = target.clone();
        clone = countryFilter.apply(clone);
        return clone;
    }
}
