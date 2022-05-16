package com.tsn.trustedservicesnavigator;

import java.util.ArrayList;
import java.util.List;

public class FilterByCountry extends Filter {

    private List<String> whitelist;

    public FilterByCountry() {
        this.whitelist = new ArrayList<>(0);
    }

    @Override
    public TrustedList apply(TrustedList listToFilter) {
        if (whitelist.isEmpty()) {
            return listToFilter;
        } else {
            return filterByWhitelist(listToFilter);
        }
    }

    private TrustedList filterByWhitelist(TrustedList listToFilter) {
        listToFilter.getCountries().removeIf(
                country -> !whitelist.contains(country.getName())
        );
        return listToFilter;
    }

    public void setWhitelist(List<String> whitelist) {
        this.whitelist = whitelist;
    }
}
