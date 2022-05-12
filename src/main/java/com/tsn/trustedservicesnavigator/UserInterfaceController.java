package com.tsn.trustedservicesnavigator;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class UserInterfaceController {
    @FXML
    private TreeView<String> servicesTreeView;

    @FXML
    public void initialize() {
        TrustedList trustedList = TrustedList.getInstance();

        servicesTreeView.setRoot(new TreeItem<>("European Union"));

        for (Country country : trustedList.getCountries()) {
            TreeItem<String> countryItem = new TreeItem<>(country.getName());
            for (Provider provider : country.getProviders()) {
                TreeItem<String> providerItem = new TreeItem<>(provider.getName());
                for (Service service : provider.getServices()) {
                    providerItem.getChildren().add(new TreeItem<>(service.getName()));
                }
                countryItem.getChildren().add(providerItem);
            }
            servicesTreeView.getRoot().getChildren().add(countryItem);
        }
    }


}