package com.tsn.trustedservicesnavigator;

import java.util.List;
import java.util.Map;

public class FilterController {
    private final CountryProviderFilter countryProviderFilter;
    private final ServiceTypeFilter serviceTypeFilter;

    public FilterController() {
        countryProviderFilter = new CountryProviderFilter();
        serviceTypeFilter = new ServiceTypeFilter();
    }

    public void setCountryProviderWhitelist(Map<String, List<String>> countryProviderWhitelist) {
        countryProviderFilter.setWhitelist(countryProviderWhitelist);
    }

    public void setServiceTypeFilter(List<String> serviceTypeWhitelist) {
        serviceTypeFilter.setWhitelist(serviceTypeWhitelist);
    }

    public TrustedList getFilteredDataFrom(TrustedList target) {
        TrustedList clone = target.clone();
        clone = countryProviderFilter.apply(clone);
        clone = serviceTypeFilter.apply(clone);
        return clone;
    }
}
