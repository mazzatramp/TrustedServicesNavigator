package com.trustedservicesnavigator.frontend.panes;

import com.trustedservicesnavigator.NavigationMediator;
import com.trustedservicesnavigator.domain.TrustedList;
import com.trustedservicesnavigator.frontend.FilterController;
import com.trustedservicesnavigator.frontend.filters.Filter;
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

    public void fillCountryAndProvidersFilterTreeView(TrustedList dataToShow) {
        providers.fill(dataToShow);
    }
    public void fillServiceTypesFilterTreeView(TrustedList dataToShow) {
        serviceTypes.fill(dataToShow);
    }
    public void fillStatusTreeView(TrustedList dataToShow) {
        statuses.fill(dataToShow);
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
        navigationMediator.readActiveFiltersFrom(this);
        TrustedList temp= navigationMediator.getFilteredList();
        if(!selected.equals(providers))
        refresh(providers, temp);
        if(!selected.equals(serviceTypes))
        refresh(serviceTypes, temp);
        if(!selected.equals(statuses))
        refresh(statuses, temp);
    }

    private void refresh(FilterPane filterPane, TrustedList temporary) {

        List<String> unselectedItems = filterPane.getUnselected();
        List<String> itemsToDisable = new ArrayList<>();

        unselectedItems.forEach(unselected -> {
            Filter filter = filterPane.getAssociatedFilter();
            try {
                if (navigationMediator.getFilterController().wouldHaveZeroServices(filter, unselected, temporary))
                    itemsToDisable.add(unselected);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        filterPane.refreshDisableProperty(itemsToDisable);
    }

    public void resetFilters() {
        providers.setSelectionStatusForAll(false);
        serviceTypes.setSelectionStatusForAll(false);
        statuses.setSelectionStatusForAll(false);
    }

    public void setNavigationMediator(NavigationMediator navigationMediator) {
        this.navigationMediator = navigationMediator;
        FilterController filterController = this.navigationMediator.getFilterController();

        providers.setAssociatedFilter(filterController.getProviderFilter());
        serviceTypes.setAssociatedFilter(filterController.getServiceTypeFilter());
        statuses.setAssociatedFilter(filterController.getStatusFilter());
    }
}
