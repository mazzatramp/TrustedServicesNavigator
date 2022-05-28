module com.tsn.trustedservicesnavigator {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;

    opens com.trustedservicesnavigator to javafx.fxml;
    exports com.trustedservicesnavigator;
    exports com.trustedservicesnavigator.domain;
    opens com.trustedservicesnavigator.domain to javafx.fxml;
    exports com.trustedservicesnavigator.frontend.filters;
    opens com.trustedservicesnavigator.frontend.filters to javafx.fxml;
    exports com.trustedservicesnavigator.frontend;
    opens com.trustedservicesnavigator.frontend to javafx.fxml;
    exports com.trustedservicesnavigator.frontend.panes;
    opens com.trustedservicesnavigator.frontend.panes to javafx.fxml;
    exports com.trustedservicesnavigator.frontend.panes.treeitems;
    opens com.trustedservicesnavigator.frontend.panes.treeitems to javafx.fxml;
}