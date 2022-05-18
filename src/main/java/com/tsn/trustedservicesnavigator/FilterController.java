package com.tsn.trustedservicesnavigator;

import java.util.List;

public class FilterController {
    private final CountryFilter countryFilter;

    private final ProviderFilter providerFilter;

    public FilterController() {
        countryFilter = new CountryFilter();
        providerFilter= new ProviderFilter();
    }

    public void setCountryWhitelist(List<String> countryWhitelist) {
        countryFilter.setWhitelist(countryWhitelist);
    }
    public void setProviderWhitelist(List<String> providerWhitelist) {providerFilter.setWhitelist(providerWhitelist);}
    public TrustedList getFilteredDataFrom(TrustedList target) {
        TrustedList clone = target.clone();
        clone = countryFilter.apply(clone);
        clone = providerFilter.apply(clone);
        return clone;
    }
}
