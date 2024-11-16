package com.example.mortpion;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClassiqueParametresController {
    @FXML private TextField nomJoueur1, nomJoueur2, symboleJoueur1, symboleJoueur2;
    @FXML private ColorPicker couleurJoueur1, couleurJoueur2;
    @FXML private ComboBox<String> typeDeContenuJoueur1, typeDeContenuJoueur2, joueurCommencant, modeDeJeu;
    @FXML private Button boutonSelectionnerImage1, boutonSelectionnerImage2;

    public void initialize() {
        setupTypeDeContenuBoxes();
        setupModeDeJeuBox();
        joueurCommencant.getItems().addAll("Joueur 1", "Joueur 2", "Aléatoire");
        joueurCommencant.getSelectionModel().selectFirst();
        nomJoueur1.setText("J1");
        nomJoueur2.setText("J2");
    }

    private void setupTypeDeContenuBoxes() {
        typeDeContenuJoueur1.getItems().addAll("Texte", "Emoji", "Image");
        typeDeContenuJoueur1.getSelectionModel().selectFirst();
        typeDeContenuJoueur2.getItems().addAll("Texte", "Emoji", "Image");
        typeDeContenuJoueur2.getSelectionModel().selectFirst();
    }

    private void setupModeDeJeuBox() {
        modeDeJeu.getItems().addAll("Joueur vs Joueur", "Joueur vs Bot Facile", "Joueur vs Bot Difficile");
        modeDeJeu.getSelectionModel().selectFirst();
        modeDeJeu.setOnAction(this::gererChangementModeDeJeu);
    }

    @FXML
    private void gererChangementModeDeJeu(ActionEvent event) {
        boolean estJeuBot = modeDeJeu.getValue().contains("Bot");
        nomJoueur2.setDisable(estJeuBot);
        symboleJoueur2.setDisable(estJeuBot);
        boutonSelectionnerImage2.setDisable(estJeuBot);
    }

    @FXML
    private void gererChangementTypeDeContenuJoueur1(ActionEvent event) {
        mettreAJourVisibiliteChampSaisie(typeDeContenuJoueur1, symboleJoueur1, boutonSelectionnerImage1);
    }

    @FXML
    private void gererChangementTypeDeContenuJoueur2(ActionEvent event) {
        mettreAJourVisibiliteChampSaisie(typeDeContenuJoueur2, symboleJoueur2, boutonSelectionnerImage2);
    }

    private void mettreAJourVisibiliteChampSaisie(ComboBox<String> comboBox, TextField champSaisie, Button bouton) {
        String typeDeContenu = comboBox.getValue();
        if (typeDeContenu.equals("Image")) {
            champSaisie.setPromptText("Entrez le chemin de l'image");
            bouton.setVisible(true);
        } else if (typeDeContenu.equals("Emoji")) {
            champSaisie.setPromptText("Entrez un emoji");
            bouton.setVisible(false);
        } else {
            champSaisie.setPromptText("Entrez un symbole");
            bouton.setVisible(false);
        }
    }

    @FXML
    private void gererSelectionImageJoueur1(ActionEvent event) {
        selectionnerImage(symboleJoueur1);
    }

    @FXML
    private void gererSelectionImageJoueur2(ActionEvent event) {
        selectionnerImage(symboleJoueur2);
    }

    private void selectionnerImage(TextField champSaisie) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner une Image");
        File fichier = fileChooser.showOpenDialog(null);
        if (fichier != null) {
            champSaisie.setText(fichier.getPath());
        }
    }

    @FXML
    private void demarrerJeu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/mortpion/GameView.fxml"));
        Parent root = loader.load();

        GameController gameController = loader.getController();
        gameController.initialiserJeu(nomJoueur1.getText(), nomJoueur2.getText(), symboleJoueur1.getText(), symboleJoueur2.getText(),
                couleurJoueur1.getValue(), couleurJoueur2.getValue(), joueurCommencant.getValue(), modeDeJeu.getValue());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void gererAfficherRegles(ActionEvent event) {
        Alert alerte = new Alert(Alert.AlertType.INFORMATION);
        alerte.setTitle("Règles du Jeu");
        alerte.setHeaderText(null);
        alerte.setContentText("Les règles du jeu sont simples :\n\n" +
                "1. Les joueurs placent à tour de rôle leur symbole (texte, emoji ou image) sur la grille.\n" +
                "2. Le premier joueur à aligner trois de ses symboles horizontalement, verticalement ou en diagonale gagne.\n" +
                "3. Si la grille est remplie sans qu'aucun joueur n'aligne trois symboles, la partie est déclarée nulle.");
        alerte.showAndWait();
    }

    @FXML
    private void gererQuitter(ActionEvent event) {
        System.exit(0);
    }
}
