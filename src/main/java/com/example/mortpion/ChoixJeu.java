package com.example.mortpion;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class ChoixJeu {

    @FXML
    private Button classicButton;

    @FXML
    private Button workInProgressButton;

    @FXML
    private Button leaderBoardButton;


    public void initialize() {
        // Initialisation si nécessaire
    }

    /**
     * Gère l'action du bouton pour démarrer le jeu classique.
     * @param event L'événement de clic sur le bouton.
     */
    @FXML
    private void handleClassicButtonAction(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/mortpion/Classique-Parametres.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Paramètres du Jeu Classique");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue des paramètres du jeu classique : " + e.getMessage());
        }
    }


    @FXML
    private void handleWorkInProgressButtonAction() {
        System.out.println("Option de travail en cours sélectionnée.");

    }


    @FXML
    private void handleLeaderBoardButtonAction() {
        System.out.println("Tableau des scores sélectionné.");
        // Implémenter une fonctionnalité si nécessaire
    }
}
