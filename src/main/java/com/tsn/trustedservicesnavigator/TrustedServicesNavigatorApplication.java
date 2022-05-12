package com.tsn.trustedservicesnavigator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TrustedServicesNavigatorApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TrustedServicesNavigatorApplication.class.getResource("navigation-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 800);

        stage.setTitle("Trusted Services Navigator App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}