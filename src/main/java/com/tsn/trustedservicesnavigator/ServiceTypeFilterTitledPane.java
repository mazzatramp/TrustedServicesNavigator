package com.tsn.trustedservicesnavigator;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

public class ServiceTypeFilterTitledPane extends FilterTitledPane {

    private ListView<CheckBox> serviceTypes;

    public ServiceTypeFilterTitledPane() {
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

    protected List<String> getSelected() {
        List<String> selectedServiceTypes = new ArrayList<>(0);

        for (CheckBox serviceType : serviceTypes.getItems()) {
            if (serviceType.isSelected()) {
                selectedServiceTypes.add(serviceType.getText());
            }
        }

        return selectedServiceTypes;
    }
}
