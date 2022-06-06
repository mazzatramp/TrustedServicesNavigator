package com.trustedservices.navigator.components;

import com.trustedservices.domain.TrustedList;
import com.trustedservices.navigator.filters.ServiceTypeFilter;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;

import java.util.*;

/**
 * This class extends the FilterPane and enable the filtering of the list by ServiceTypes, which are showed on a list of checkBoxes
 * that the user can interact with
 */
public class ServiceTypeFilterPane extends FilterPane {

    private final ListView<CheckBox> serviceTypes;

    public ServiceTypeFilterPane() {
        serviceTypes = new ListView<>();
        this.setFilterView(serviceTypes);
        this.setAssociatedFilter(new ServiceTypeFilter());
    }

    @Override
    protected void setSelectedForAll(boolean select) {
        serviceTypes.getItems().forEach(checkBox -> {
            if (!checkBox.isDisabled() || !select)
                checkBox.setSelected(select);
        });
    }

    /**
     * @param dataToShow fills the panel with list of serviceTypes that the complete TrustedList builds upon creation.
     * Adds a listener on each checkBox to add a checked item to the filter whitelist
     */
    @Override
    public void fillWith(TrustedList dataToShow) {
        dataToShow.getServiceTypes().forEach(serviceType -> {
            CheckBox box = new CheckBox(serviceType);
            box.selectedProperty().addListener(super.handleFilterChange(box.getText()));
            serviceTypes.getItems().add(box);
        });
    }

    @Override
    public Set<String> getSelectedItems() {
        Set<String> selectedServiceTypes = new HashSet<>(0);

        for (CheckBox serviceType : serviceTypes.getItems())
            if (serviceType.isSelected())
                selectedServiceTypes.add(serviceType.getText());

        return selectedServiceTypes;
    }

    @Override
    public Set<String> getUnselectedItems() {
        Set<String> selectedServiceTypes = new HashSet<>(0);

        for (CheckBox serviceType : serviceTypes.getItems())
            if (!serviceType.isSelected())
                selectedServiceTypes.add(serviceType.getText());

        return selectedServiceTypes;
    }

    /**
     * @param itemsToDisable This method disables the items that would get no results if added as parameters of the search.
     *                       The parameter is filled in the
     * @see FilterPanesAccordion
     */
    @Override
    public void disable(Set<String> itemsToDisable) {
        serviceTypes.getItems().forEach(serviceTypeItem -> {
            boolean toDisable = itemsToDisable.contains(serviceTypeItem.getText());
            serviceTypeItem.setDisable(toDisable);
        });
    }
}
