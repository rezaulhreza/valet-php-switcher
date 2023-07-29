package com.phpswitcher.phpswitcher;

import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PhpSwitcherApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("php_switcher.fxml"));
        primaryStage.setTitle("PHP Version Switcher for valet");
        primaryStage.setScene(new Scene(root));

        // Set the size of the window
        primaryStage.setWidth(500);
        primaryStage.setHeight(140);

        // Disable maximizing
        primaryStage.setResizable(false);

        // Set an icon for the application
        primaryStage.getIcons().add(new Image("file:src/main/resources/images/icon.png"));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
