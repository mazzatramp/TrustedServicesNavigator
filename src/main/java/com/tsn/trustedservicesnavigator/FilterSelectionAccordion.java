package com.tsn.trustedservicesnavigator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.List;

public class FilterSelectionAccordion extends Accordion {

    @FXML
    private ProviderFilterPane providers;
    @FXML
    private StatusFilterPane statuses;
    @FXML
    private ServiceTypeFilterPane serviceTypes;

    @FXML
    private HBox countryHyperlinksBox;

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

    public void resetFilters() {
        providers.setSelectionStatusForAll(false);
        serviceTypes.setSelectionStatusForAll(false);
        statuses.setSelectionStatusForAll(false);
    }
}
