module com.tsn.trustedservicesnavigator {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires org.apache.commons.io;

    exports com.trustedservices.domain;
    opens com.trustedservices.domain to javafx.fxml;
    exports com.trustedservices.navigator.filters;
    opens com.trustedservices.navigator.filters to javafx.fxml;
    exports com.trustedservices.navigator;
    opens com.trustedservices.navigator to javafx.fxml;
    exports com.trustedservices.navigator.components;
    opens com.trustedservices.navigator.components to javafx.fxml;
    exports com.trustedservices.navigator.web;
    opens com.trustedservices.navigator.web to javafx.fxml;
}
