package com.tsn.trustedservicesnavigator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class UserInterfaceController {
    @FXML
    private TreeView<String> servicesTreeView;

    @FXML
    public void initialize() {
        TrustedList trustedList = TrustedList.getInstance();

        trustedList.getCountries().add(new Country("Austria", "AT"));
        trustedList.getCountries().add(new Country("Italy", "IT"));
        trustedList.getCountries().add(new Country("Germany", "DE"));
        trustedList.getCountries().add(new Country("France", "FR"));

        servicesTreeView.setRoot(new TreeItem<>("EU"));
        servicesTreeView.setShowRoot(false);

        for (Country country : trustedList.getCountries()) {
            servicesTreeView.getRoot().getChildren().add(new TreeItem<>(country.getName()));
        }

    }
}