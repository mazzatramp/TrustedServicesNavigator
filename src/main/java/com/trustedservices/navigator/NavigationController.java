package com.trustedservices.navigator;

import com.trustedservices.domain.TrustedList;
import com.trustedservices.navigator.filters.FilterList;
import com.trustedservices.navigator.web.TrustedListBuilder;

public class NavigationController {

    private final FilterList filters;
    private TrustedList completeList;

    public NavigationController() {
        this.completeList = new TrustedList();
        this.filters = new FilterList();
    }

    public TrustedList getFilteredList() {
        return filters.getFilteredListFrom(completeList);
    }

    public TrustedList getCompleteList() {
        return completeList;
    }

    public void buildCompleteList(TrustedListBuilder builder) {
        completeList = builder.build();
    }

    public FilterList getFilters() {
        return filters;
    }
}