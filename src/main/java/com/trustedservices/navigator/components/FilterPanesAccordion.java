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

    /**
     * @param filterPane for the parameter filterPane, builds a list of parameter items that would add no new results to the search,
     *                   by using the method wouldGetNoResults
     */
    private void disableItemsOf(FilterPane filterPane) {
        Set<String> unselectedFilterItems = filterPane.getUnselectedItems(); //We don't disable selected items
        Set<String> itemsToDisable = new HashSet<>();

        for (String unselectedFilterItem : unselectedFilterItems) {
            if (!wouldGetResults(filterPane.getAssociatedFilter(), unselectedFilterItem))
                itemsToDisable.add(unselectedFilterItem);
        }

        filterPane.disable(itemsToDisable);
    }

    /**
     * @param filter for the filter parameter
     * @param filterItem checks if this filter parameter item would show results upon filtering, which is done with method getFilteredListFrom
     * The method saves the WhiteList for this filter, resets it with only the parameter filter item (the whiteLists of other filters won't be affected and
     * those filters will work as usual),then filters the list for each filter and asks if the new list is empty.
     * At last, resets the filter whiteList as it was.
     * @return a boolean that is true if the filtered list has at least an item
     */
    private boolean wouldGetResults(Filter filter, String filterItem) {
        Set<String> savedWhitelist = filter.getWhitelist();
        Set<String> singleItem = new HashSet<>();
        singleItem.add(filterItem);
        filter.setWhitelist(singleItem);

        FilterList filters = new FilterList(getAssociatedFilters());
        boolean hasResults = !filters.getFilteredListFrom(windowController.getCompleteTrustedList()).isEmpty();

        filter.setWhitelist(savedWhitelist);
        return hasResults;
    }

    public void resetFilters() {
        providers.selectAllFilterItems(false);
        serviceTypes.selectAllFilterItems(false);
        statuses.selectAllFilterItems(false);
    }

    public List<Filter> getAssociatedFilters() {
        List<Filter> filters = new java.util.ArrayList<>();
        filters.add(providers.getAssociatedFilter());
        filters.add(statuses.getAssociatedFilter());
        filters.add(serviceTypes.getAssociatedFilter());
        return filters;
    }
}
