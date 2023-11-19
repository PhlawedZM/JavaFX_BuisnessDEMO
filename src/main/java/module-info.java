module com.zachm.buisness_demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    opens com.zachm.buisness_demo to javafx.fxml;
    opens com.zachm.buisness_demo.util to javafx.fxml;
    exports com.zachm.buisness_demo;
    exports com.zachm.buisness_demo.util;
}