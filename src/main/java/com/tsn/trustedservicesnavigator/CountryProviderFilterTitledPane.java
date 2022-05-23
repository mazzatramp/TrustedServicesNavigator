package com.tsn.trustedservicesnavigator;

import javafx.collections.ObservableList;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountryProviderFilterTitledPane extends FilterTitledPane {

    TreeView<String> countriesAndProviders;

    public CountryProviderFilterTitledPane() {
        this.setText("Country and Providers");

        countriesAndProviders = new TreeView<>();
        countriesAndProviders.setRoot(new TreeItem<>("Countries Root"));
        countriesAndProviders.setShowRoot(false);
        countriesAndProviders.setCellFactory(CheckBoxTreeCell.forTreeView());

        setFilterView(countriesAndProviders);
    }

    @Override
    protected void setSelectionStatusForAll(boolean selectionStatus) {
        countriesAndProviders.getRoot().getChildren().forEach(country-> {
            CheckBoxTreeItem<String> checkBoxCountry = (CheckBoxTreeItem<String>) country;
            checkBoxCountry.getChildren().forEach(provider -> {
                ((CheckBoxTreeItem<String>) provider).setSelected(selectionStatus);
            });

        });
    }

    public Map<String, List<String>> getSelected() {
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

    public void fill(TrustedList dataToShow) {
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
}
