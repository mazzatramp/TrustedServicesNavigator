module com.tsn.trustedservicesnavigator {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;

    opens com.tsn.trustedservicesnavigator to javafx.fxml;
    exports com.tsn.trustedservicesnavigator;
}