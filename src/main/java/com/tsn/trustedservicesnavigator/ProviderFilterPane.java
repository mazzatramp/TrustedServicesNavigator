package com.tsn.trustedservicesnavigator;

import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;

import java.util.ArrayList;
import java.util.List;

public class ProviderFilterPane extends FilterPane {

    private TreeView<String> providers;

    public ProviderFilterPane() {
        this.setText("Country and Providers");

        providers = new TreeView<>();
        providers.setRoot(new TreeItem<>("Countries Root"));
        providers.setShowRoot(false);
        providers.setCellFactory(CheckBoxTreeCell.forTreeView());

        setFilterView(providers);
    }

    @Override
    protected void setSelectionStatusForAll(boolean selectionStatus) {
        providers.getRoot().getChildren().forEach(country-> {
            CheckBoxTreeItem<String> checkBoxCountry = (CheckBoxTreeItem<String>) country;
            checkBoxCountry.getChildren().forEach(provider -> {
                ((CheckBoxTreeItem<String>) provider).setSelected(selectionStatus);
            });

        });
    }

    public List<String> getSelected() {
        List<String> selectedProviders = new ArrayList<>();
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
    public void fill(TrustedList dataToShow) {
        CheckBoxTreeItem<String> countryTreeItem, providerTreeItem;

        for (Country country : dataToShow.getCountries()) {
            countryTreeItem = new CheckBoxTreeItem<>(country.getName());
            for (Provider provider : country.getProviders()) {
                providerTreeItem = new CheckBoxTreeItem<>(provider.getName());
                countryTreeItem.getChildren().add(providerTreeItem);
            }
            providers.getRoot().getChildren().add(countryTreeItem);
        }

        providers.refresh();
    }

    
}
