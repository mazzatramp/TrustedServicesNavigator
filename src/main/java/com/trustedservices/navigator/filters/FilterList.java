package com.trustedservices.navigator.filters;

import com.trustedservices.domain.TrustedList;

import java.util.ArrayList;
import java.util.Collection;

public class FilterList extends ArrayList<Filter> {
    public FilterList() {
        super();
    }

    public FilterList(Collection<? extends Filter> c) {
        super(c);
    }

    public TrustedList getFilteredListFrom(TrustedList list) {
        TrustedList filteredList = list.clone();
        this.forEach(filter -> filter.applyTo(filteredList));
        filteredList.updateServiceTypesAndStatuses();
        return filteredList;
    }
}
