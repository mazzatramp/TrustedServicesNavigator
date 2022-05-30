package com.trustedservices.navigator;

import com.trustedservices.navigator.filters.FilterController;
import com.trustedservices.navigator.components.FilterSelectionAccordion;
import com.trustedservices.navigator.web.TrustedListApiBuilder;
import com.trustedservices.navigator.web.TrustedListBuilder;
import com.trustedservices.web.*;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TrustedServicesNavigatorApplication extends Application {
    private final static String TITLE = "Trusted Services Navigator App";

    private NavigationMediator navigationMediator;
    private UserInterfaceController userInterfaceController;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TrustedServicesNavigatorApplication.class.getResource("navigation-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        navigationMediator = new NavigationMediator();
        userInterfaceController = fxmlLoader.getController();
        linkNavigatorAndController();
        setupFilterPanes();
        downloadApiTrustedList();

        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }

    private void setupFilterPanes() {
        FilterSelectionAccordion filterSelectionAccordion = userInterfaceController.getFilterAccordion();
        FilterController filterController = navigationMediator.getFilterController();
        filterSelectionAccordion.setNavigationMediator(navigationMediator);
        filterSelectionAccordion.linkFilterPanesWithAssociatedFilters(filterController);
    }

    private void linkNavigatorAndController() {
        navigationMediator.setUserInterfaceController(userInterfaceController);
        userInterfaceController.setNavigationMediator(navigationMediator);
    }

    private void downloadApiTrustedList() {
        Task<Void> downloadingApiData = downloadAndDisplayApiData();

        userInterfaceController.bindProgressBarWith(downloadingApiData);

        Thread th = new Thread(downloadingApiData);
        th.start();
    }

    private Task<Void> downloadAndDisplayApiData() {
        return new Task<>() {
            @Override
            protected Void call() {
            TrustedListBuilder apiBuilder = new TrustedListApiBuilder();
            navigationMediator.buildCompleteList(apiBuilder);
            userInterfaceController.fillFiltersAndDisplay();
            return null;
            }
        };
    }

    public static void main(String[] args) {
        launch();
    }
}