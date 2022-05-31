package com.trustedservices.navigator.components;

import com.trustedservices.navigator.NavigationController;
import com.trustedservices.domain.TrustedList;
import com.trustedservices.navigator.filters.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class FilterSelectionAccordion extends Accordion {

    @FXML private ProviderFilterPane providers;
    @FXML private StatusFilterPane statuses;
    @FXML private ServiceTypeFilterPane serviceTypes;

    private NavigationController navigationController;

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
        providers.fillWith(data);
        serviceTypes.fillWith(data);
        statuses.fillWith(data);
    }

    public Set<String> getSelectedProviders() {
        return providers.getSelected();
    }
    public Set<String> getSelectedServiceTypes() {
        return serviceTypes.getSelected();
    }
    public Set<String> getSelectedStatuses() {
        return statuses.getSelected();
    }

    public void refreshFiltersExcept(Filter filter) {
        navigationController.updateActiveFiltersFromUserSelection();
        if (!(filter instanceof ProviderFilter))
            disableItemsOf(providers);
        if (!(filter instanceof ServiceTypeFilter))
            disableItemsOf(serviceTypes);
        if (!(filter instanceof StatusFilter))
            disableItemsOf(statuses);
    }

    private void disableItemsOf(FilterPane filterPane) {
        Set<String> unselectedFilterItems = filterPane.getUnselected();
        Set<String> itemsToDisable = new HashSet<>();

        for (String unselected : unselectedFilterItems) {
                itemsToDisable.add(unselected);
        }

        filterPane.disable(itemsToDisable);
    }

    public void resetFilters() {
        providers.setSelectionStatusForAll(false);
        serviceTypes.setSelectionStatusForAll(false);
        statuses.setSelectionStatusForAll(false);
    }

    public void setNavigationMediator(NavigationController navigationController) {
        this.navigationController = navigationController;
    }

    public void linkFilterPanesWithAssociatedFilters(FilterController filterController) {
        providers.setAssociatedFilter(filterController.getProviderFilter());
        serviceTypes.setAssociatedFilter(filterController.getServiceTypeFilter());
        statuses.setAssociatedFilter(filterController.getStatusFilter());
    }
}
