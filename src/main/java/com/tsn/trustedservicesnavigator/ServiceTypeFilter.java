package com.tsn.trustedservicesnavigator;

import java.util.ArrayList;
import java.util.List;

public class ServiceTypeFilter extends Filter {
    public List<String> whitelist;

    public ServiceTypeFilter() {
        this.whitelist= new ArrayList<String>(0);
    }

    @Override
    public TrustedList applyTo(TrustedList listToFilter) {
      if (whitelist.isEmpty()) return listToFilter;
      else return filterByWhitelist(listToFilter);
    }

    public void setWhitelist(List<String> whitelist) {
        this.whitelist = whitelist;
    }

    public TrustedList filterByWhitelist (TrustedList listToFilter) {

        return listToFilter;
    }
}
