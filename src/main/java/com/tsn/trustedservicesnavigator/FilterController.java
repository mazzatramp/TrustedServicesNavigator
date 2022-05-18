package com.tsn.trustedservicesnavigator;

import java.util.List;
import java.util.Map;

public class FilterController {
    private final CountryProviderFilter countryProviderFilter;

    public FilterController() {
        countryProviderFilter = new CountryProviderFilter();
    }

    public void setCountryProviderWhitelist(Map<String, List<String>> countryProviderWhitelist) {
        countryProviderFilter.setWhitelist(countryProviderWhitelist);
    }
    public TrustedList getFilteredDataFrom(TrustedList target) {
        TrustedList clone = target.clone();
        clone = countryProviderFilter.apply(clone);
        return clone;
    }
}
