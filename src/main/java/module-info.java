module com.tsn.trustedservicesnavigator {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;

    opens com.tsn.trustedservicesnavigator to javafx.fxml;
    exports com.tsn.trustedservicesnavigator;
}