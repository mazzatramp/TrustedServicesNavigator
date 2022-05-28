package com.trustedservicesnavigator.frontend.panes;

import com.trustedservicesnavigator.frontend.UserInterfaceController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.List;

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

    public void setInfo(List<String> info) {
        StringBuilder printingInformation = new StringBuilder();
        for (String string : info) {
            printingInformation.append(string);
            printingInformation.append("\n");
        }
        text.setText(printingInformation.toString());
    }

    public void setUserInterfaceController(UserInterfaceController userInterfaceController) {
        this.userInterfaceController = userInterfaceController;
    }
}
