module com.phpswitcher.phpswitcher {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.phpswitcher.phpswitcher to javafx.fxml;
    exports com.phpswitcher.phpswitcher;
}