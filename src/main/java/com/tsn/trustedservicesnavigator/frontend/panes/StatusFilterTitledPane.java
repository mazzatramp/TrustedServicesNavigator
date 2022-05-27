package com.tsn.trustedservicesnavigator.frontend.panes;

import com.tsn.trustedservicesnavigator.backend.TrustedList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

public class StatusFilterTitledPane extends FilterTitledPane {

    private ListView<CheckBox> statuses;

    public StatusFilterTitledPane() {
        this.setText("Statuses");
        statuses = new ListView<>();
        setFilterView(statuses);
    }

    @Override
    protected void setSelectionStatusForAll(boolean selectionStatus) {
        statuses.getItems().forEach(checkBox -> {
            checkBox.setSelected(selectionStatus);
        });
    }

    public void fill(TrustedList dataToShow) {
        dataToShow.getStatuses().stream().sorted().forEach(
                status -> statuses.getItems().add(new CheckBox(status))
        );
    }

    public List<String> getSelected() {
        List<String> selectedStatuses = new ArrayList<>(0);

        for (CheckBox status : statuses.getItems()) {
            if (status.isSelected()) {
                selectedStatuses.add(status.getText());
            }
        }

        return selectedStatuses;
    }
}
