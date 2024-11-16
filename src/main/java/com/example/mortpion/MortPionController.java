package com.example.mortpion;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MortPionController {

    @FXML
    private void handleStartButtonClick(ActionEvent event) {
        try {

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            MortPionApplication.navigateToScene(stage, "Choix-Jeu.fxml", "Mort Pion: Choix du Jeu");
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void handleSettings(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MortPionApplication.navigateToScene(stage, "settings-view.fxml", "Mort Pion: PARAMÈTRES");
    }


    @FXML
    private Label mortPionLabel;
    @FXML
    private Button button1, button2, button3, button4, button5, button6, button7, button8, button9, startButton;

    @FXML
    protected void onHelloButtonClick() {
        mortPionLabel.setText("Welcome to MORT PION!");
    }


    @FXML
    protected void onRulesButtonClick(ActionEvent event) {
        try {
            // Charger la vue des règles
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/mortpion/Regles.fxml"));
            Parent rulesView = loader.load();
            Scene scene = new Scene(rulesView);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleSettingsButtonClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Parametres.fxml"));
            Parent settingsRoot = loader.load();

            Scene settingsScene = new Scene(settingsRoot);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(settingsScene);
            window.setTitle("Mort Pion: PARAMÈTRES");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleGenerique(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MortPionApplication.navigateToScene(stage, "Generique.fxml", "Mort Pion: GÉNÉRIQUE");
    }
}
