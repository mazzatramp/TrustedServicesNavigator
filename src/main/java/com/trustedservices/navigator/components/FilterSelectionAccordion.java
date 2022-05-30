package com.trustedservices.navigator.components;

import com.trustedservices.navigator.NavigationMediator;
import com.trustedservices.domain.TrustedList;
import com.trustedservices.navigator.filters.FilterController;
import com.trustedservices.navigator.filters.Filter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilterSelectionAccordion extends Accordion {

    @FXML
    private ProviderFilterPane providers;
    @FXML
    private StatusFilterPane statuses;
    @FXML
    private ServiceTypeFilterPane serviceTypes;

    private NavigationMediator navigationMediator;

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

    public List<String> getSelectedProviders() {
        return providers.getSelected();
    }
    public List<String> getSelectedServiceTypes() {
        return serviceTypes.getSelected();
    }
    public List<String> getSelectedStatuses() {
        return statuses.getSelected();
    }

    public void refreshFilters(Filter selected) {
        navigationMediator.updateActiveFiltersFromUserSelection();
        TrustedList temp= navigationMediator.getFilteredList();
        disableItemsOf(providers, temp);
        disableItemsOf(serviceTypes, temp);
        disableItemsOf(statuses, temp);
    }

    private void disableItemsOf(FilterPane filterPane, TrustedList filtered) {
        List<String> unselectedFilterItems = filterPane.getUnselected();
        List<String> itemsToDisable = new ArrayList<>();

        for (String unselected : unselectedFilterItems) {
            Filter filter = filterPane.getAssociatedFilter();
            if (navigationMediator.getFilterController().wouldHaveZeroServices(filter, unselected, filtered))
                itemsToDisable.add(unselected);
        }

        filterPane.disable(itemsToDisable);
    }

    public void resetFilters() {
        providers.setSelectionStatusForAll(false);
        serviceTypes.setSelectionStatusForAll(false);
        statuses.setSelectionStatusForAll(false);
    }

    public void setNavigationMediator(NavigationMediator navigationMediator) {
        this.navigationMediator = navigationMediator;
        linkFilterPanesWithAssociatedFilters(navigationMediator.getFilterController());
    }

    public void linkFilterPanesWithAssociatedFilters(FilterController filterController) {
        providers.setAssociatedFilter(filterController.getProviderFilter());
        serviceTypes.setAssociatedFilter(filterController.getServiceTypeFilter());
        statuses.setAssociatedFilter(filterController.getStatusFilter());
    }
}
