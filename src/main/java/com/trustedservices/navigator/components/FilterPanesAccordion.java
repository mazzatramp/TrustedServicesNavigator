package com.trustedservices.navigator.components;

import com.trustedservices.domain.TrustedList;
import com.trustedservices.navigator.filters.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FilterPanesAccordion extends Accordion {

    @FXML private ProviderFilterPane providers;
    @FXML private StatusFilterPane statuses;
    @FXML private ServiceTypeFilterPane serviceTypes;

    private TrustedList completeList;

    public FilterPanesAccordion() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("filter-selection-accordion.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void fillFilterPanesWith(TrustedList completeList) {
        this.completeList = completeList;
        providers.fillWith(completeList);
        serviceTypes.fillWith(completeList);
        statuses.fillWith(completeList);
    }

    public void refreshPanesExcept(FilterPane notToRefresh) {
        if (!(notToRefresh instanceof ProviderFilterPane))
            disableItemsOf(providers);
        if (!(notToRefresh instanceof ServiceTypeFilterPane))
            disableItemsOf(serviceTypes);
        if (!(notToRefresh instanceof StatusFilterPane))
            disableItemsOf(statuses);
    }

    private void disableItemsOf(FilterPane filterPane) {
        Set<String> unselectedFilterItems = filterPane.getUnselectedItems();
        Set<String> itemsToDisable = new HashSet<>();

        for (String unselectedFilterItem : unselectedFilterItems) {
            Set<String> oldWhitelist = filterPane.getAssociatedFilter().getWhitelist();

            Set<String> testWhitelist = new HashSet<>(1);
            testWhitelist.add(unselectedFilterItem);
            filterPane.getAssociatedFilter().setWhitelist(testWhitelist);

            FilterList filters = new FilterList(getAssociatedFilters());
            TrustedList filtered = filters.getFilteredListFrom(completeList);
            filterPane.getAssociatedFilter().setWhitelist(oldWhitelist);

            if (filtered.getCountries().isEmpty())
                itemsToDisable.add(unselectedFilterItem);
        }

        filterPane.disable(itemsToDisable);
    }

    public void resetFilters() {
        providers.selectAllFilters(false);
        serviceTypes.selectAllFilters(false);
        statuses.selectAllFilters(false);
    }

    public ArrayList<Filter> getAssociatedFilters() {
        ArrayList<Filter> filters = new ArrayList<>(3);
        filters.add(providers.getAssociatedFilter());
        filters.add(statuses.getAssociatedFilter());
        filters.add(serviceTypes.getAssociatedFilter());
        return filters;
    }
}
