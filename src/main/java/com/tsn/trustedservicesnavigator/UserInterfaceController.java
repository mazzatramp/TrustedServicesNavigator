package com.tsn.trustedservicesnavigator;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class UserInterfaceController {
    @FXML
    private TreeView<String> servicesTreeView;
    @FXML
    private ProgressBar servicesDownloadProgressBar;

    @FXML
    public void initialize() {
        servicesTreeView.setRoot(new TreeItem<>("Root"));
        fillServicesTreeView();
    }

    private void fillServicesTreeView() {
        TrustedList trustedList = TrustedList.getInstance();

        Task<Integer> fillTreeViewTask = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                trustedList.fillWithApiData();

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


                return 1;
            }
        };

        servicesDownloadProgressBar.visibleProperty().bind(fillTreeViewTask.runningProperty());
        servicesDownloadProgressBar.progressProperty().bind(fillTreeViewTask.progressProperty());

        Thread th = new Thread(fillTreeViewTask);
        th.setDaemon(true);
        th.start();
        servicesTreeView.refresh();
    }


}