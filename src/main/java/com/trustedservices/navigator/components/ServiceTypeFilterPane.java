package com.trustedservices.navigator.components;

import com.trustedservices.domain.TrustedList;
import com.trustedservices.navigator.filters.ServiceTypeFilter;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;

import java.util.*;

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

    @Override
    public void disable(Set<String> itemsToDisable) {
        serviceTypes.getItems().forEach(serviceTypeItem -> {
            boolean toDisable = itemsToDisable.contains(serviceTypeItem.getText());
            serviceTypeItem.setDisable(toDisable);
        });
    }
}
