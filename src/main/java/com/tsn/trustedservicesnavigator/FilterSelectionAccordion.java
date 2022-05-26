package com.tsn.trustedservicesnavigator;

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

    public void refreshFilters() {
        navigationMediator.readActiveFiltersFrom(this);
        refresh(providers);
        refresh(serviceTypes);
        refresh(statuses);
    }

    private void refresh(FilterPane filterPane) {

        List<String> unselectedItems = filterPane.getUnselected();
        List<String> itemsToDisable = new ArrayList<>();

        unselectedItems.forEach(unselected -> {
            Filter filter = filterPane.getAssociatedFilter();
            try {
                if (navigationMediator.getFilterController().wouldHaveZeroServices(filter, unselected))
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
