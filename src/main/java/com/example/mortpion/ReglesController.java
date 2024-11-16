package com.example.mortpion;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class ReglesController {

    @FXML
    private void handleBackToMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/mortpion/hello-view.fxml"));
            Parent mainMenu = loader.load();


            Scene mainMenuScene = new Scene(mainMenu, 503, 700);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Centre la fenêtre sur l'écran
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            window.setX((screenBounds.getWidth() - 503) / 2);
            window.setY((screenBounds.getHeight() - 700) / 2);

            window.setScene(mainMenuScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement du menu principal : " + e.getMessage());
        }
    }


}
