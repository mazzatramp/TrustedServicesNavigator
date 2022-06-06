package com.trustedservices.navigator.components;

import com.trustedservices.domain.TrustedList;
import com.trustedservices.navigator.filters.StatusFilter;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;

import java.util.*;

/**
 * This class extends the FilterPane and enable the filtering of the list by Statuses, which are showed on a list of checkBoxes
 * that the user can interact with
 */
public class StatusFilterPane extends FilterPane {

    private final ListView<CheckBox> statuses;

    public StatusFilterPane() {
        statuses = new ListView<>();
        setFilterView(statuses);
        this.setAssociatedFilter(new StatusFilter());
    }

    @Override
    protected void setAllCheckBoxStatus(boolean select) {
        statuses.getItems().forEach(checkBox -> {
            if (!checkBox.isDisabled() || !select)
                checkBox.setSelected(select);
        });
    }

    /**
     * @param dataToShow fills the panel with list of statuses that the complete TrustedList builds upon creation.
     * Adds a listener on each checkBox to add a checked item to the filter whitelist
     */
    @Override
    public void fillWith(TrustedList dataToShow) {
        dataToShow.getStatuses().forEach(status -> {
            CheckBox box = new CheckBox(status);
            box.selectedProperty().addListener(super.handleFilterChange(box.getText()));
            statuses.getItems().add(box);
        });
    }

    @Override
    public Set<String> getSelectedItems() {
        Set<String> selectedStatuses = new HashSet<>();

        for (CheckBox status : statuses.getItems())
            if (!status.isDisabled() && status.isSelected())
                selectedStatuses.add(status.getText());

        return selectedStatuses;
    }

    @Override
    public Set<String> getUnselectedItems() {
        Set<String> selectedStatuses = new HashSet<>(0);

        for (CheckBox status : statuses.getItems())
            if (!status.isSelected())
                selectedStatuses.add(status.getText());

        return selectedStatuses;
    }

    /**
     * @param itemsToDisable This method disables the items that would get no results if added as parameters of the search.
     *                       The parameter is filled in the
     * @see FilterPanesAccordion
     */
    @Override
    public void disable(Collection<String> itemsToDisable) {
        statuses.getItems().forEach(statusItem -> {
            boolean toDisable = itemsToDisable.contains(statusItem.getText());
            statusItem.setDisable(toDisable);
        });
    }
}
