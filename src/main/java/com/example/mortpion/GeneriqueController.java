package com.example.mortpion;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class GeneriqueController {

    @FXML
    private VBox creditsBox;
    @FXML
    private Button returnButton;

    public void initialize() {
        animateCredits();
    }

    private void animateCredits() {
        // Calcule la hauteur après que le contenu ait été rendu
        double height = creditsBox.getBoundsInParent().getHeight();
        TranslateTransition transition = new TranslateTransition(Duration.seconds(10), creditsBox);
        transition.setFromY(1000);
        transition.setToY(-height);
        transition.setCycleCount(TranslateTransition.INDEFINITE);
        transition.play();
    }

    @FXML
    private void handleReturn() throws IOException {
        Stage stage = (Stage) returnButton.getScene().getWindow();
        MortPionApplication.navigateToScene(stage, "hello-view.fxml", "Mort Pion: MENU");
    }
}
