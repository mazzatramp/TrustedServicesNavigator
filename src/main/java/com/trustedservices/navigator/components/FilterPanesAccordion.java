package com.trustedservices.navigator.components;

import com.trustedservices.domain.TrustedList;
import com.trustedservices.navigator.WindowController;
import com.trustedservices.navigator.filters.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class that contains all the filter panes. It fills them with data from the complete list, refreshes the filters and disables
 * the checkboxes if needed
 */
public class FilterPanesAccordion extends Accordion {

    @FXML private ProviderFilterPane providers;
    @FXML private StatusFilterPane statuses;
    @FXML private ServiceTypeFilterPane serviceTypes;

    private WindowController windowController;

    public FilterPanesAccordion() {
        loadFXMLResource();
    }

    private void loadFXMLResource() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("filter-panes-accordion.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            System.err.println("Error while setting the scene for FilterPane Accordion in FXMLLoader");
            throw new RuntimeException(e);
        }
    }

    public void setWindowController(WindowController windowController) {
        this.windowController = windowController;
    }

    /**
     * @param completeList the complete TrustedList built by ApiData. The method asks each filter to fill itself with the
     *                     complete list
     */
    public void fillFilterPanesWith(TrustedList completeList) {
        providers.fillWith(completeList);
        serviceTypes.fillWith(completeList);
        statuses.fillWith(completeList);
    }

    /**
     * @param toNotRefresh method that asks to refresh the items of each filter by the method disableItemsOf, but the parameter filter,
     *                     which is the last selected
     */
    public void refreshPanesExcept(FilterPane toNotRefresh) {
        if (!(toNotRefresh instanceof ProviderFilterPane))
            disableItemsOf(providers);
        if (!(toNotRefresh instanceof ServiceTypeFilterPane))
            disableItemsOf(serviceTypes);
        if (!(toNotRefresh instanceof StatusFilterPane))
            disableItemsOf(statuses);
    }

    private void disableItemsOf(FilterPane filterPane) {
        Set<String> unselectedFilterItems = filterPane.getUnselectedItems(); //We don't disable selected items
        Set<String> itemsToDisable = new HashSet<>();

        for (String unselectedFilterItem : unselectedFilterItems) {
            if (!wouldGetResults(filterPane.getAssociatedFilter(), unselectedFilterItem))
                itemsToDisable.add(unselectedFilterItem);
        }

        filterPane.disable(itemsToDisable);
    }

    private boolean wouldGetResults(Filter filter, String filterItem) {
        Set<String> savedWhitelist = filter.getWhitelist();

        filter.setWhitelist(Set.of(filterItem));

        FilterList filters = new FilterList(getAssociatedFilters());
        boolean hasResults = !filters.getFilteredListFrom(windowController.getCompleteTrustedList()).isEmpty();

        filter.setWhitelist(savedWhitelist);
        return hasResults;
    }

    public void resetFilters() {
        providers.setSelectedForAll(false);
        serviceTypes.setSelectedForAll(false);
        statuses.setSelectedForAll(false);
    }

    public List<Filter> getAssociatedFilters() {
        return List.of(
            providers.getAssociatedFilter(),
            statuses.getAssociatedFilter(),
            serviceTypes.getAssociatedFilter()
        );
    }
}
