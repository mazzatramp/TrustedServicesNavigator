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
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        NavigationMediator navigationMediator = new NavigationMediator();
        linkNavigatorAndController(fxmlLoader.getController(), navigationMediator);

        stage.setTitle("Trusted Services Navigator App");
        stage.setScene(scene);
        stage.show();
    }

    private void linkNavigatorAndController(UserInterfaceController userInterfaceController, NavigationMediator navigationMediator) {
        navigationMediator.setUserInterfaceController(userInterfaceController);
        userInterfaceController.setNavigationMediator(navigationMediator);
    }

    public static void main(String[] args) {
        launch();
    }
}