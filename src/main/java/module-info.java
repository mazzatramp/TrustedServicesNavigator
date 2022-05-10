module com.tsn.trustedservicesnavigator {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.tsn.trustedservicesnavigator to javafx.fxml;
    exports com.tsn.trustedservicesnavigator;
}