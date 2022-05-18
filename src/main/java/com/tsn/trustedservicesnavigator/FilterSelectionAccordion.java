package com.tsn.trustedservicesnavigator;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilterSelectionAccordion extends Accordion {

    @FXML
    private TreeView<String> countriesAndProviders;
    @FXML
    private TreeView<String> serviceTypes;
    @FXML
    private TreeView<String> statuses;

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

    public List<String> getSelectedCountries() {
        ObservableList<TreeItem<String>> countries = countriesAndProviders.getRoot().getChildren();
        List<String> selectedCountries = new ArrayList<>(0);

        for (TreeItem<String> countryTreeItem : countries) {
            CheckBoxTreeItem c = (CheckBoxTreeItem) countryTreeItem;
            String countryCheckBox = (String) c.getValue();
            if (c.isSelected()) {
                selectedCountries.add(countryCheckBox);
            }
        }
        return selectedCountries;
    }
}
