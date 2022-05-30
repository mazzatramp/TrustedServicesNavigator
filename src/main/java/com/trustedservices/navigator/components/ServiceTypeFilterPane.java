package com.trustedservices.navigator.components;

import com.trustedservices.domain.TrustedList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

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

    public List<String> getSelected() {
        List<String> selectedServiceTypes = new ArrayList<>(0);

        for (CheckBox serviceType : serviceTypes.getItems()) {
            if (serviceType.isSelected()) {
                selectedServiceTypes.add(serviceType.getText());
            }
        }

        return selectedServiceTypes;
    }

    @Override
    public List<String> getUnselected() {
        List<String> selectedServiceTypes = new ArrayList<>(0);

        for (CheckBox serviceType : serviceTypes.getItems()) {
            if (!serviceType.isSelected()) {
                selectedServiceTypes.add(serviceType.getText());
            }
        }

        return selectedServiceTypes;
    }

    @Override
    public void disable(List<String> itemsToDisable) {
        serviceTypes.getItems().forEach(serviceTypeItem -> {
            boolean toDisable = itemsToDisable.contains(serviceTypeItem.getText());
            serviceTypeItem.setDisable(toDisable);
        });
    }
}
