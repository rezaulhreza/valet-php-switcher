package com.phpswitcher.phpswitcher;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.regex.Matcher;
import javafx.application.Platform;


public class PhpSwitcherController {

    @FXML
    private TextField phpPathField;

    @FXML
    private Button browseButton;


    @FXML
    public void initialize() {
        browseButton.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(browseButton.getScene().getWindow());

            if (selectedDirectory != null) {
                phpPathField.setText(selectedDirectory.getAbsolutePath());
            }
        });
    }

    @FXML
    public void exitApplication() {
        Platform.exit();
    }

    @FXML
    public void showAboutDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("PHP Version Switcher for valet");
        alert.setContentText(
                "This application allows you to switch between different versions of PHP for Valet.\n\n" +
                        "Instructions for use:\n" +
                        "1. Click the 'Browse' button and navigate to the directory containing the desired PHP version. Please select the directory itself, not the 'php-cgi.exe' file. For example, if your PHP version is located at 'C:/php-8.2.8/php-cgi.exe', you should select the 'php-8.2.8' directory.\n" +
                        "2. Click the 'Switch PHP Version for valet' button. The application will update the PHP version for Valet and restart the Valet services. You may see several pop-up windows from Valet during this process - please click 'Yes' to all of them.\n" +
                        "3. Once you see the 'Valet restarted successfully' message, your PHP version has been successfully switched. You can now visit your sites with the new PHP version.\n\n" +
                        "Enjoy using PHP Version Switcher for Valet!"
        );
        alert.showAndWait();
    }

    @FXML
    public void switchPhpVersion() {
        String phpCgiPath = phpPathField.getText().replace("\\", "/");
        File file = new File(phpCgiPath);
        if (!file.exists()) {
            showAlert(Alert.AlertType.ERROR, "PHP version is not installed in " + phpCgiPath);
            return;
        }

        String xmlFile = System.getProperty("user.home") + "/.config/valet/Services/phpcgiservice.xml";
        Path path = Paths.get(xmlFile);
        try (Stream<String> lines = Files.lines(path)) {
            String content = lines
                    .map(line -> line.replaceAll("<executable>[^<]*</executable>", Matcher.quoteReplacement("<executable>" + phpCgiPath + "/php-cgi.exe</executable>")))
                    .reduce((a, b) -> a + "\n" + b)
                    .get();
            Files.write(path, content.getBytes());
            showAlert(Alert.AlertType.INFORMATION, "Switching to PHP for valet to: " + phpCgiPath);

            // Restart Valet
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "valet restart");
            Process process = processBuilder.start();
            process.waitFor();
            showAlert(Alert.AlertType.INFORMATION, "Valet restarted successfully");
        } catch (IOException | InterruptedException e) {
            showAlert(Alert.AlertType.ERROR, "Error switching to PHP for valet at " + phpCgiPath);
        }
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
