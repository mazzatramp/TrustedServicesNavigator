package com.trustedservices.navigator.components;

import com.trustedservices.domain.Country;
import com.trustedservices.domain.Provider;
import com.trustedservices.domain.TrustedList;
import com.trustedservices.navigator.filters.ProviderFilter;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProviderFilterPane extends FilterPane {

    private final TreeView<String> providers;

    public ProviderFilterPane() {
        this.setText("Country and Providers");

        providers = new TreeView<>();
        providers.setRoot(new FilterTreeItem<>());
        providers.setShowRoot(false);
        providers.setCellFactory(FilterTreeCell.forTreeView());

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

    public Set<String> getSelectedItems() {
        Set<String> selectedProviders = new HashSet<>();
        providers.getRoot().getChildren().forEach(countryTreeItem -> {
            CheckBoxTreeItem<String> countryCheckBox = (CheckBoxTreeItem<String>) countryTreeItem;
            if (!countryCheckBox.isIndependent()) {
                countryCheckBox.getChildren().forEach(providerTreeItem -> {
                    CheckBoxTreeItem<String> providerCheckBox = (CheckBoxTreeItem<String>) providerTreeItem;
                    if (providerCheckBox.isSelected())
                        selectedProviders.add(countryCheckBox.getValue() +"/"+ providerCheckBox.getValue());
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
    public void disable(Collection<String> itemsToDisable) {
        for (TreeItem<String> countryTreeItem : providers.getRoot().getChildren()) {
            FilterTreeItem countryFilterItem = (FilterTreeItem) countryTreeItem;
            boolean allVisitedProvidersDisabled = true;

            for (TreeItem<String> providerTreeItem : countryTreeItem.getChildren()) {
                FilterTreeItem providerFilterItem = (FilterTreeItem) providerTreeItem;

                boolean disable = itemsToDisable.contains(providerTreeItem.getValue());
                providerFilterItem.setDisabled(disable);
                allVisitedProvidersDisabled &= disable;
            }
            countryFilterItem.setDisabled(allVisitedProvidersDisabled);
        }
    }

    @Override
    public void fillWith(TrustedList dataToShow) {
        for (Country country : dataToShow.getCountries()) {
            FilterTreeItem<String> countryCheckBox = createCountryCheckBox(country);
            providers.getRoot().getChildren().add(countryCheckBox);
        }
    }

    private FilterTreeItem<String> createCountryCheckBox(Country country) {
        FilterTreeItem<String> countryTreeItem = new FilterTreeItem<>(country.getName());
        for (Provider provider : country.getProviders()) {
            FilterTreeItem<String> providerCheckBox = createProviderCheckBox(provider);
            countryTreeItem.getChildren().add(providerCheckBox);
        }
        return countryTreeItem;
    }

    private FilterTreeItem<String> createProviderCheckBox(Provider provider) {
        FilterTreeItem<String> providerTreeItem = new FilterTreeItem<>(provider.getName());
        providerTreeItem.selectedProperty().addListener(super.getSelectionListener());
        return providerTreeItem;
    }

}
