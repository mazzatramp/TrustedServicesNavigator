package com.tsn.trustedservicesnavigator;

import java.util.List;

public class FilterController {
    private final CountryFilter countryFilter;


    public FilterController() {
        countryFilter = new CountryFilter();
    }

    public void setCountryWhitelist(List<String> countryWhitelist) {
        countryFilter.setWhitelist(countryWhitelist);
    }

    public TrustedList getFilteredDataFrom(TrustedList target) {
        TrustedList clone = target.clone();
        clone = countryFilter.apply(clone);
        return clone;
    }
}
