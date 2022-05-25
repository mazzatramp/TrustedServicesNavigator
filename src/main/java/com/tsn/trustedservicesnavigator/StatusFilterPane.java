package com.tsn.trustedservicesnavigator;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

public class StatusFilterPane extends FilterPane {

    private ListView<CheckBox> statuses;

    public StatusFilterPane() {
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
            if (!status.isDisabled() && status.isSelected()) {
                selectedStatuses.add(status.getText());
            }
        }

        return selectedStatuses;
    }

    @Override
    public List<String> getUnselected() {

        List<String> selectedStatuses = new ArrayList<>(0);

        for (CheckBox status : statuses.getItems()) {
            if (!status.isSelected()) {
                selectedStatuses.add(status.getText());
            }
        }

        return selectedStatuses;
    }

    @Override
    public void refreshDisableProperty(List<String> itemsToDisable) {
        statuses.getItems().forEach(statusItem -> {
            boolean toDisable = itemsToDisable.contains(statusItem.getText());
            statusItem.setDisable(toDisable);
        });
    }
}
