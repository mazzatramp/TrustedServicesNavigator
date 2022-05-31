package com.trustedservices.navigator;

import com.trustedservices.navigator.components.FilterSelectionAccordion;
import com.trustedservices.navigator.web.TrustedListApiBuilder;
import com.trustedservices.navigator.web.TrustedListBuilder;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TrustedServicesNavigatorApplication extends Application {
    private final static String TITLE = "Trusted Services Navigator App";

    private NavigationController navigationController;
    private WindowController windowController;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TrustedServicesNavigatorApplication.class.getResource("main-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        navigationController = new NavigationController();
        filterController = new FilterController();
        windowController = fxmlLoader.getController();

        startApiDataDownload();

        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }

    private void setupFilterPanes() {
        FilterSelectionAccordion filterSelectionAccordion = windowController.getFilterAccordion();
        filterSelectionAccordion.setNavigationMediator(navigationController);
        filterSelectionAccordion.linkFilterPanesWithAssociatedFilters(filterController);
    }
    private void startApiDataDownload() {
        Task<Void> downloadAndDisplay = getDownloadAndDisplayDataTask();
        windowController.bindProgressBarWith(downloadAndDisplay);
        Thread th = new Thread(downloadAndDisplay);
        th.start();
    }

    private Task<Void> getDownloadAndDisplayDataTask() {
        return new Task<>() {
            @Override
            protected Void call() {
                TrustedListBuilder apiBuilder = new TrustedListApiBuilder();
                navigationController.buildCompleteList(apiBuilder);
                windowController.fillDisplayAndFiltersViews();
                return null;
            }
        };
    }

    public static void main(String[] args) {
        launch();
    }
}