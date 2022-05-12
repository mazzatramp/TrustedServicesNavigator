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
        try {
            trustedList.fillCountriesData();
        } catch (Exception e) {
            System.err.println("can't fill countries");
        }

        servicesTreeView.setRoot(new TreeItem<>("EU"));
        servicesTreeView.setShowRoot(false);

        for (Country country : trustedList.getCountries()) {
            servicesTreeView.getRoot().getChildren().add(new TreeItem<>(country.getName()));
        }

    }


}