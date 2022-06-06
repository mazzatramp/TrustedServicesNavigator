package com.trustedservices.navigator.components;

import com.trustedservices.domain.*;
import com.trustedservices.navigator.filters.ProviderFilter;
import javafx.scene.control.*;

import java.util.*;

/**
 * This class fills the ProviderFilterPane and inherits and implements methods from FilterPane. This filter Pane has a treeView
 * much like the Display pane, that displays the providers for each country, letting the user select each item by implementing
 * a checkBoxTreeItem
 */
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

    /**
     * @param selectionStatus implementation of the same abstract method fo the class filter pane, for each country, changes the
     *                        selection status to the according parameter.
     */
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

    /**
     * The method browses the Tree of countries and Providers and disables all providers belonging to the list
     * @param itemsToDisable . The method double-checks the identity of the providers, by checking also the name of the associated
     * country. The variable allProviderDisable becomes false when an element does not get disable, if all elements get disable
     * the associated country gets disabled too.
     */
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

    /**
     * Fills the Country and Provider CheckBoxTreeItem with
     * @param dataToShow
     * It delegates the creation of the CountryCheckBox to the method createCountryCheckBox.
     */
    @Override
    public void fillWith(TrustedList dataToShow) {
        for (Country country : dataToShow.getCountries()) {
            FilterTreeItem<String> countryCheckBox = createCountryCheckBox(country);
            filterTreeView.getRoot().getChildren().add(countryCheckBox);
        }
    }

    /**
     * @param country given the parameter country, creates a FilterTreeItem. Then it browses the list of providers associated
     * to the country and creates them with the method createProviderCheckBox, and add them to the node.
     * @return a new countryFilterItem with its children
     */
    private FilterTreeItem<String> createCountryCheckBox(Country country) {
        FilterTreeItem<String> countryFilterItem = new FilterTreeItem<>(country.getName());
        for (Provider provider : country.getProviders()) {
            FilterTreeItem<String> providerFilterItem = createProviderCheckBox(provider);
            countryFilterItem.getChildren().add(providerFilterItem);
        }
        return countryFilterItem;
    }

    /**
     * @param provider given a provider argument, creates a new FilterTreeItem and adds a new listener to it.
     * @return a providerFilterItem
     */
    private FilterTreeItem<String> createProviderCheckBox(Provider provider) {
        FilterTreeItem<String> providerFilterItem = new FilterTreeItem<>(provider.getName());
        providerFilterItem.selectedProperty().addListener(
                handleFilterChange(provider.getCountry().getName() + "/" + provider.getName())
        );
        return providerFilterItem;
    }
}
