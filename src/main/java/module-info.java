module com.phpswitcher.phpswitcher {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.phpswitcher.phpswitcher to javafx.fxml;
    exports com.phpswitcher.phpswitcher;
}