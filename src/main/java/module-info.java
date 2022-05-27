module com.tsn.trustedservicesnavigator {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;

    opens com.tsn.trustedservicesnavigator to javafx.fxml;
    exports com.tsn.trustedservicesnavigator;
    exports com.tsn.trustedservicesnavigator.backend;
    opens com.tsn.trustedservicesnavigator.backend to javafx.fxml;
    exports com.tsn.trustedservicesnavigator.frontend.filters;
    opens com.tsn.trustedservicesnavigator.frontend.filters to javafx.fxml;
    exports com.tsn.trustedservicesnavigator.frontend;
    opens com.tsn.trustedservicesnavigator.frontend to javafx.fxml;
    exports com.tsn.trustedservicesnavigator.frontend.panes;
    opens com.tsn.trustedservicesnavigator.frontend.panes to javafx.fxml;
    exports com.tsn.trustedservicesnavigator.frontend.panes.treeitems;
    opens com.tsn.trustedservicesnavigator.frontend.panes.treeitems to javafx.fxml;
}