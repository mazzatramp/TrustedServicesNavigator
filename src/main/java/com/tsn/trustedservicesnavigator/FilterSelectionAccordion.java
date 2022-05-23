package com.tsn.trustedservicesnavigator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FilterSelectionAccordion extends Accordion {

    @FXML
    private CountryProviderFilterTitledPane countryAndProviders;
    @FXML
    private StatusFilterTitledPane statuses;
    @FXML
    private ServiceTypeFilterTitledPane serviceTypes;

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
        countryAndProviders.fill(dataToShow);
    }

    public void fillServiceTypesFilterTreeView(TrustedList dataToShow) {
        serviceTypes.fill(dataToShow);
    }

    public void fillStatusTreeView(TrustedList dataToShow) {
        statuses.fill(dataToShow);
    }

    public Map<String, List<String>> getSelectedCountriesAndProviders() {
        return countryAndProviders.getSelected();
    }

    public List<String> getSelectedServiceTypes() {
        return serviceTypes.getSelected();
    }

    public List<String> getSelectedStatuses() {
        return statuses.getSelected();
    }

    public void resetFilters() {
        countryAndProviders.setSelectionStatusForAll(false);
        serviceTypes.setSelectionStatusForAll(false);
        statuses.setSelectionStatusForAll(false);
    }
}
