package com.trustedservices.navigator;

import com.trustedservices.domain.TrustedList;
import com.trustedservices.navigator.filters.Filter;
import com.trustedservices.navigator.filters.FilterList;
import com.trustedservices.navigator.web.TrustedListBuilder;

/**
 * This class gets the built list from the TrustedListApiBuilder, gets the filtered list from the filters and acts as a mediator
 * between front-end and back-end
 */
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
        try {
            completeList = builder.build();
        }catch(RuntimeException e) {
            System.err.println("Connection failed, no response from API");
            throw new RuntimeException(e);
        }

    }

    public FilterList getFilters() {
        return filters;
    }
}