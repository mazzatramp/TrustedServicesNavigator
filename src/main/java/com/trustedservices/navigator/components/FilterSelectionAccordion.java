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

public class FilterSelectionAccordion extends Accordion {

    @FXML private ProviderFilterPane providers;
    @FXML private StatusFilterPane statuses;
    @FXML private ServiceTypeFilterPane serviceTypes;

    private TrustedList data;

    public FilterSelectionAccordion() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("filter-selection-accordion.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void fillFilterPanesWith(TrustedList data) {
        this.data = data;
        providers.fillWith(data);
        serviceTypes.fillWith(data);
        statuses.fillWith(data);
    }

    public void refreshFilterPanes() {
        disableItemsOf(providers);
        disableItemsOf(serviceTypes);
        disableItemsOf(statuses);
    }

    public void refreshFilterPanesExcept(FilterPane filterPane) {
        if (!(filterPane instanceof ProviderFilterPane))
            disableItemsOf(providers);
        if (!(filterPane instanceof ServiceTypeFilterPane))
            disableItemsOf(serviceTypes);
        if (!(filterPane instanceof StatusFilterPane))
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
            TrustedList filtered = filters.getFilteredListFrom(data);
            filterPane.getAssociatedFilter().setWhitelist(oldWhitelist);

            if (filtered.getCountries().isEmpty())
                itemsToDisable.add(unselectedFilterItem);
        }

        filterPane.disable(itemsToDisable);
    }

    public void resetFilters() {
        providers.setSelectionStatusForAll(false);
        serviceTypes.setSelectionStatusForAll(false);
        statuses.setSelectionStatusForAll(false);
    }

    public ArrayList<Filter> getAssociatedFilters() {
        ArrayList<Filter> filters = new ArrayList<>(3);
        filters.add(providers.getAssociatedFilter());
        filters.add(statuses.getAssociatedFilter());
        filters.add(serviceTypes.getAssociatedFilter());
        return filters;
    }
}
