package com.trustedservices.navigator.components;

import com.trustedservices.domain.*;
import com.trustedservices.navigator.filters.ProviderFilter;
import javafx.scene.control.*;

import java.util.*;

public class ProviderFilterPane extends FilterPane {

    private final TreeView<String> filterTreeView;

    public ProviderFilterPane() {
        filterTreeView = new TreeView<>();
        filterTreeView.setRoot(new FilterTreeItem<>());
        filterTreeView.setShowRoot(false);
        filterTreeView.setCellFactory(FilterTreeCell.forTreeView());

        setFilterView(filterTreeView);
        this.setAssociatedFilter(new ProviderFilter());
    }

    @Override
    public void setAllCheckBoxStatus(boolean selectionStatus) {
        for (TreeItem<String> countryTreeItem : filterTreeView.getRoot().getChildren()) {
            for (TreeItem<String> provider : countryTreeItem.getChildren()) {
                ((FilterTreeItem<String>) provider).setSelected(selectionStatus);
            }
        }
    }

    @Override
    public Set<String> getSelectedItems() {
        Set<String> selectedProviders = new HashSet<>();

        for (TreeItem<String> countryTreeItem : filterTreeView.getRoot().getChildren()) {
            for (TreeItem<String> providerTreeItem : countryTreeItem.getChildren()) {
                FilterTreeItem<String> providerFilterItem = (FilterTreeItem<String>) providerTreeItem;
                if (providerFilterItem.isSelected())
                    selectedProviders.add(countryTreeItem.getValue() + "/" + providerTreeItem.getValue());
            }
        }

        System.out.println(selectedProviders);
        return selectedProviders;
    }

    @Override
    public Set<String> getUnselectedItems() {
        Set<String> selectedProviders = new HashSet<>();

        for (TreeItem<String> countryTreeItem : filterTreeView.getRoot().getChildren()) {
            for (TreeItem<String> providerTreeItem : countryTreeItem.getChildren()) {
                FilterTreeItem<String> providerFilterItem = (FilterTreeItem<String>) providerTreeItem;
                if (!providerFilterItem.isSelected())
                    selectedProviders.add(countryTreeItem.getValue() + "/" + providerTreeItem.getValue());
            }
        }

        return selectedProviders;
    }

    @Override
    public void disable(Collection<String> itemsToDisable) {
        for (TreeItem<String> countryTreeItem : filterTreeView.getRoot().getChildren()) {
            FilterTreeItem<String> countryFilterItem = (FilterTreeItem<String>) countryTreeItem;
            boolean allProvidersDisabled = true;

            for (TreeItem<String> providerTreeItem : countryFilterItem.getChildren()) {
                FilterTreeItem providerFilterItem = (FilterTreeItem) providerTreeItem;

                boolean disable = itemsToDisable.contains(countryFilterItem.getValue() + "/" + providerFilterItem.getValue());
                providerFilterItem.setDisabled(disable);

                if (!disable) allProvidersDisabled = false;
            }

            countryFilterItem.setDisabled(allProvidersDisabled);
        }
    }

    @Override
    public void fillWith(TrustedList dataToShow) {
        for (Country country : dataToShow.getCountries()) {
            FilterTreeItem<String> countryCheckBox = createCountryCheckBox(country);
            filterTreeView.getRoot().getChildren().add(countryCheckBox);
        }
    }

    private FilterTreeItem<String> createCountryCheckBox(Country country) {
        FilterTreeItem<String> countryFilterItem = new FilterTreeItem<>(country.getName());
        for (Provider provider : country.getProviders()) {
            FilterTreeItem<String> providerFilterItem = createProviderCheckBox(provider);
            countryFilterItem.getChildren().add(providerFilterItem);
        }
        return countryFilterItem;
    }

    private FilterTreeItem<String> createProviderCheckBox(Provider provider) {
        FilterTreeItem<String> providerFilterItem = new FilterTreeItem<>(provider.getName());
        providerFilterItem.selectedProperty().addListener(
                handleFilterChange(provider.getCountry().getName() + "/" + provider.getName())
        );
        return providerFilterItem;
    }
}
