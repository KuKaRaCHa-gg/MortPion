package com.example.mortpion;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MortPionApplication extends Application {

    // Méthode pour naviguer entre les scènes de l'application
    public static void navigateToScene(Stage stage, String fxmlFile, String title) throws IOException {
        // Charge le fichier FXML spécifié
        FXMLLoader loader = new FXMLLoader(MortPionApplication.class.getResource(fxmlFile));
        Parent root = loader.load();
        // Crée une nouvelle scène avec le contenu chargé
        Scene scene = new Scene(root);
        // Configure la fenêtre (stage) avec la nouvelle scène et le titre
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        double width = 500.0;
        double height = 700.0;

        stage.setMinWidth(width);
        stage.setMinHeight(height);
        stage.setMaxWidth(width);
        stage.setMaxHeight(height);
        stage.setResizable(false); // Empêche le redimensionnement

        stage.setTitle("Mort Pion: MENU PRINCIPAL");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/mortpion/Icon.png")));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
