package com.tsn.trustedservicesnavigator.frontend.panes;

import com.tsn.trustedservicesnavigator.frontend.UserInterfaceController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class InfoPane extends AnchorPane {
    @FXML
    private Label text;
    @FXML
    private Hyperlink close;

    private UserInterfaceController userInterfaceController;

    public InfoPane() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("info-pane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        close.setOnAction(actionEvent -> userInterfaceController.setInfoPaneVisible(false));
    }

    public void setInfo(String providerInfo, String serviceInfo) {
        StringBuilder builder = new StringBuilder();
        if (providerInfo != null) {
            builder.append("Provider\n");
            builder.append(providerInfo);
        }

        if (providerInfo != null && serviceInfo != null)
            builder.append("\n———————\n");

        if (serviceInfo != null) {
            builder.append("Service\n");
            builder.append(serviceInfo);
        }

        text.setText(builder.toString());
    }

    public void setUserInterfaceController(UserInterfaceController userInterfaceController) {
        this.userInterfaceController = userInterfaceController;
    }
}
