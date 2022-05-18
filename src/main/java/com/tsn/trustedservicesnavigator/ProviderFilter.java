package com.tsn.trustedservicesnavigator;

import java.util.ArrayList;
import java.util.List;

public class ProviderFilter extends Filter{
    public List<String> whitelist;

    public ProviderFilter() {
        this.whitelist=new ArrayList<String>(0);
    }

    public void setWhitelist(List<String> whitelist) {
        this.whitelist = whitelist;
    }

    @Override
    public TrustedList apply(TrustedList listToFilter) {
        if (whitelist.isEmpty())
            return listToFilter;
        else
            return filterbyWhiteList(listToFilter);
    }

    public TrustedList filterbyWhiteList(TrustedList filteredList) {
        for (Country country: filteredList.getCountries()) {
            country.getProviders().removeIf(provider -> !whitelist.contains(provider.getName()));
        }
    return filteredList;
    }
}
