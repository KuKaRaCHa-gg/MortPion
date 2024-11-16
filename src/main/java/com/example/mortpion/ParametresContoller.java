package com.example.mortpion;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.net.URI;
import java.io.IOException;

public class ParametresContoller {

    @FXML
    private Slider graphicsSlider;
    @FXML
    private Slider volumeSlider;
    @FXML
    private CheckBox rtxCheckBox;
    @FXML
    private CheckBox dlssCheckBox;
    @FXML
    private CheckBox rgbCheckBox;

    public void initialize() {
        graphicsSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() == 4) {
                openWebPage("https://youtu.be/xvFZjo5PgG0"); // URL de blague
            }
        });

        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() == 0) {
                showAlert("Silence", "Chut... on dort !");
            } else if (newValue.intValue() == 50) {
                showAlert("Volume modéré", "Le volume est juste parfait !");
            } else if (newValue.intValue() == 100) {
                showAlert("Volume maximum", "Attention aux tympans !");
            }
        });

        rtxCheckBox.setOnAction(e -> showAlert("RTX", "Il faut avoir la RTX Titan pour ça !"));
        dlssCheckBox.setOnAction(e -> showAlert("DLSS", "Ajout de FPS... 9999 FPS atteints !"));
        rgbCheckBox.setOnAction(e -> showAlert("RGB", "C'est trop pour vous !"));
    }

    private void openWebPage(String url) {
        try {
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                desktop.browse(new URI(url));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void returnToMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/mortpion/hello-view.fxml"));
            Parent mainMenu = loader.load();
            Scene mainMenuScene = new Scene(mainMenu);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(mainMenuScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement du menu principal : " + e.getMessage());
        }
    }

    @FXML
    private void applySettings(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/mortpion/hello-view.fxml"));
            Parent mainMenu = loader.load();
            Scene mainMenuScene = new Scene(mainMenu);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(mainMenuScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement du menu principal : " + e.getMessage());

        }
    }
}
