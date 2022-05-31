package com.trustedservices.navigator.components;

import com.trustedservices.domain.Country;
import com.trustedservices.domain.Provider;
import com.trustedservices.domain.TrustedList;
import com.trustedservices.navigator.filters.ProviderFilter;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;

import java.util.*;

public class ProviderFilterPane extends FilterPane {

    private final TreeView<String> providers;

    public ProviderFilterPane() {
        this.setText("Country and Providers");

        providers = new TreeView<>();
        providers.setRoot(new TreeItem<>());
        providers.setShowRoot(false);
        providers.setCellFactory(CheckBoxTreeCell.forTreeView());

        setFilterView(providers);
        this.setAssociatedFilter(new ProviderFilter());
    }

    @Override
    public void setSelectionStatusForAll(boolean selectionStatus) {
        providers.getRoot().getChildren().forEach(country-> {
            CheckBoxTreeItem<String> checkBoxCountry = (CheckBoxTreeItem<String>) country;
            checkBoxCountry.getChildren().forEach(provider -> {
                ((CheckBoxTreeItem<String>) provider).setSelected(selectionStatus);
            });

        });
    }

    public Set<String> getSelected() {
    public Set<String> getSelectedItems() {
        Set<String> selectedProviders = new HashSet<>();
        providers.getRoot().getChildren().forEach(countryTreeItem -> {
            CheckBoxTreeItem<String> countryCheckBox = (CheckBoxTreeItem<String>) countryTreeItem;
            if (!countryCheckBox.isIndependent()) {
                countryCheckBox.getChildren().forEach(providerTreeItem -> {
                    CheckBoxTreeItem<String> providerCheckBox = (CheckBoxTreeItem<String>) providerTreeItem;
                    if (providerCheckBox.isSelected())
                        selectedProviders.add(providerCheckBox.getValue());
                });
            }
        });
        return selectedProviders;
    }

    @Override
    public Set<String> getUnselectedItems() {
        Set<String> unselected = new HashSet<>();
        providers.getRoot().getChildren().forEach(countryTreeItem -> {
            CheckBoxTreeItem<String> countryCheckBox = (CheckBoxTreeItem<String>) countryTreeItem;
            if (!countryCheckBox.isIndependent()) {
                countryCheckBox.getChildren().forEach(providerTreeItem -> {
                    CheckBoxTreeItem<String> providerCheckBox = (CheckBoxTreeItem<String>) providerTreeItem;
                    if (!providerCheckBox.isSelected())
                        unselected.add(providerCheckBox.getValue());
                });
            }
        });
        return unselected;
    }

    @Override
    public void disable(Collection<String> toDisable) {
        //
    }

    @Override
    public void fillWith(TrustedList dataToShow) {
        for (Country country : dataToShow.getCountries()) {
            CheckBoxTreeItem<String> countryCheckBox = createCountryCheckBox(country);
            providers.getRoot().getChildren().add(countryCheckBox);
        }
    }

    private CheckBoxTreeItem<String> createCountryCheckBox(Country country) {
        CheckBoxTreeItem<String> countryTreeItem = new CheckBoxTreeItem<>(country.getName());
        for (Provider provider : country.getProviders()) {
            CheckBoxTreeItem<String> providerCheckBox = createProviderCheckBox(provider);
            countryTreeItem.getChildren().add(providerCheckBox);
        }
        return countryTreeItem;
    }

    private CheckBoxTreeItem<String> createProviderCheckBox(Provider provider) {
        CheckBoxTreeItem<String> providerTreeItem = new CheckBoxTreeItem<>(provider.getName());
        providerTreeItem.selectedProperty().addListener(super.getSelectionListener());
        return providerTreeItem;
    }

}
