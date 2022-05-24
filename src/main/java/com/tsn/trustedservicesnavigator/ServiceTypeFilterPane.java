package com.tsn.trustedservicesnavigator;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

public class ServiceTypeFilterPane extends FilterPane {

    private ListView<CheckBox> serviceTypes;

    public ServiceTypeFilterPane() {
        this.setText("Service Types");
        serviceTypes = new ListView<>();
        setFilterView(serviceTypes);
    }

    @Override
    protected void setSelectionStatusForAll(boolean selectionStatus) {
        serviceTypes.getItems().forEach(checkBox -> {
            checkBox.setSelected(selectionStatus);
        });
    }

    protected void fill(TrustedList dataToShow) {
        dataToShow.getServiceTypes().forEach(serviceType -> {
            serviceTypes.getItems().add(new CheckBox(serviceType));
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
}