package com.trustedservices.navigator.components;

import com.trustedservices.domain.TrustedList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;

import java.util.*;

public class ServiceTypeFilterPane extends FilterPane {

    private final ListView<CheckBox> serviceTypes;

    public ServiceTypeFilterPane() {
        serviceTypes = new ListView<>();
        this.setText("Service Types");
        this.setFilterView(serviceTypes);
    }

    @Override
    protected void setSelectionStatusForAll(boolean selectionStatus) {
        serviceTypes.getItems().forEach(checkBox -> {
            checkBox.setSelected(selectionStatus);
        });
    }

    @Override
    public void fillWith(TrustedList dataToShow) {
        dataToShow.getServiceTypes().forEach(serviceType -> {
            CheckBox box = new CheckBox(serviceType);
            box.selectedProperty().addListener(super.getSelectionListener());
            serviceTypes.getItems().add(box);
        });
    }

    public Set<String> getSelected() {
        Set<String> selectedServiceTypes = new HashSet<>(0);

        for (CheckBox serviceType : serviceTypes.getItems()) {
            if (serviceType.isSelected()) {
                selectedServiceTypes.add(serviceType.getText());
            }
        }

        return selectedServiceTypes;
    }

    @Override
    public Set<String> getUnselected() {
        Set<String> selectedServiceTypes = new HashSet<>(0);

        for (CheckBox serviceType : serviceTypes.getItems()) {
            if (!serviceType.isSelected()) {
                selectedServiceTypes.add(serviceType.getText());
            }
        }

        return selectedServiceTypes;
    }

    @Override
    public void disable(Collection<String> itemsToDisable) {
        serviceTypes.getItems().forEach(serviceTypeItem -> {
            boolean toDisable = itemsToDisable.contains(serviceTypeItem.getText());
            serviceTypeItem.setDisable(toDisable);
        });
    }
}
