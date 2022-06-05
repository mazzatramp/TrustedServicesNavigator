package com.trustedservices.navigator.components;

import com.trustedservices.navigator.WindowController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;

public class InfoPane extends AnchorPane {

    @FXML private Label information;
    @FXML private Hyperlink closeButton;

    public InfoPane() {
        loadFXMLResource();
        closeButton.setOnAction(actionEvent -> setVisible(false));
    }

    private void loadFXMLResource() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("info-pane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setInfo(String information) {
        this.information.setText(information);
    }
}
