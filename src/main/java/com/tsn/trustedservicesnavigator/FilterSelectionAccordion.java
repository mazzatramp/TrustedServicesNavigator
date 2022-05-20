package com.tsn.trustedservicesnavigator;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterSelectionAccordion extends Accordion {

    @FXML
    private TreeView<String> countriesAndProviders;
    @FXML
    private ListView<CheckBox> serviceTypes;
    @FXML
    private ListView<CheckBox> statuses;

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

    @FXML
    public void initialize() {
        countriesAndProviders.setRoot(new TreeItem<>("Country Root"));
        countriesAndProviders.setCellFactory(CheckBoxTreeCell.forTreeView());
    }

    public void fillCountryAndProvidersFilterTreeView(TrustedList dataToShow) {
        CheckBoxTreeItem<String> countryTreeItem, providerTreeItem;

        for (Country country : dataToShow.getCountries()) {
            countryTreeItem = new CheckBoxTreeItem(country.getName());
            for (Provider provider : country.getProviders()) {
                providerTreeItem = new CheckBoxTreeItem(provider.getName());
                countryTreeItem.getChildren().add(providerTreeItem);
            }
            countriesAndProviders.getRoot().getChildren().add(countryTreeItem);
        }

        countriesAndProviders.refresh();
    }

    public void fillServiceTypesFilterTreeView(TrustedList dataToShow) {
        dataToShow.getServiceTypes().stream().sorted().forEach(
                serviceType -> serviceTypes.getItems().add(new CheckBox(serviceType))
        );
    }

    public void fillStatusTreeView(TrustedList dataToShow) {
        dataToShow.getStatuses().stream().sorted().forEach(
                status -> statuses.getItems().add(new CheckBox(status))
        );
    }
    public Map<String, List<String>> getSelectedCountriesAndProviders() {
        ObservableList<TreeItem<String>> countries = countriesAndProviders.getRoot().getChildren();
        Map<String, List<String>> selectedCountriesAndProviders = new HashMap<>(0);

        for (TreeItem<String> countryTreeItem : countries) {
            CheckBoxTreeItem countryCheckBox = (CheckBoxTreeItem)countryTreeItem;
            String countryName = (String) countryCheckBox.getValue();
            if (countryCheckBox.isSelected() || countryCheckBox.isIndeterminate()) {
                List<String> providers = new ArrayList<>();
                for (TreeItem<String> providerTreeItem : countryTreeItem.getChildren()) {
                    CheckBoxTreeItem providerCheckBox = (CheckBoxTreeItem) providerTreeItem;
                    String providerName = (String) providerCheckBox.getValue();
                    if (providerCheckBox.isSelected()) {
                        providers.add(providerName);
                    }
                }
                selectedCountriesAndProviders.put(countryName, providers);
            }
        }

        return selectedCountriesAndProviders;
    }

    public List<String> getSelectedServiceTypes() {
        List<String> selectedServiceTypes = new ArrayList<>(0);

        serviceTypes.getItems().forEach(serviceType -> {
            if (serviceType.isSelected()) {
                selectedServiceTypes.add(serviceType.getText());
            }}
        );

        return selectedServiceTypes;
    }
    public List<String> getSelectedStatuses() {
        List<String> selectedStatuses = new ArrayList<>(0);

        statuses.getItems().forEach(status -> {
            if (status.isSelected()) {
                selectedStatuses.add(status.getText());
            }}
        );

        return selectedStatuses;
    }

}
