package com.example.mortpion;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class GameController {
    @FXML private GridPane grilleDeJeu;
    @FXML private Label indicateurTour;
    @FXML private Label scoreLabelJoueur1, scoreLabelJoueur2;
    @FXML private Rectangle lumiereIndicateurTour;

    private boolean estTourJoueur1 = true;
    private Button[][] boutons = new Button[3][3];
    private int nombreDeCoups = 0;
    private int scoreJoueur1 = 0;
    private int scoreJoueur2 = 0;
    private String nomJoueur1;
    private String nomJoueur2;
    private String symboleJoueur1;
    private String symboleJoueur2;
    private Color couleurJoueur1;
    private Color couleurJoueur2;
    private String joueurCommencant;
    private String modeDeJeu;

    public void initialiserJeu(String nomJoueur1, String nomJoueur2, String symboleJoueur1, String symboleJoueur2,
                               Color couleurJoueur1, Color couleurJoueur2, String joueurCommencant, String modeDeJeu) {
        this.nomJoueur1 = nomJoueur1.isEmpty() ? "J1" : nomJoueur1;
        this.nomJoueur2 = nomJoueur2.isEmpty() ? "J2" : nomJoueur2;
        this.symboleJoueur1 = symboleJoueur1.isEmpty() ? "X" : symboleJoueur1;
        this.symboleJoueur2 = symboleJoueur2.isEmpty() ? "O" : symboleJoueur2;
        this.couleurJoueur1 = couleurJoueur1;
        this.couleurJoueur2 = couleurJoueur2;
        this.joueurCommencant = joueurCommencant;
        this.modeDeJeu = modeDeJeu;

        setupGrilleDeJeu();
        mettreAJourIndicateurTour();
        mettreAJourScores();

        if (joueurCommencant.equals("Joueur 2") && modeDeJeu.contains("Bot")) {
            estTourJoueur1 = false;
            gererTourBot();
        }
    }

    private void setupGrilleDeJeu() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button bouton = new Button();
                bouton.setPrefSize(100, 100);
                bouton.setFont(new Font("Arial", 24)); // Police adaptée pour afficher des emojis ou texte
                bouton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-border-color: white;");
                int finalI = i, finalJ = j;
                bouton.setOnAction(e -> gererActionBouton(finalI, finalJ));
                grilleDeJeu.add(bouton, j, i);
                boutons[i][j] = bouton;
            }
        }
    }

    private void gererActionBouton(int row, int col) {
        Button bouton = boutons[row][col];
        if (bouton.getGraphic() == null && bouton.getText().isEmpty()) {
            String contenu = estTourJoueur1 ? symboleJoueur1 : symboleJoueur2;
            Color couleur = estTourJoueur1 ? couleurJoueur1 : couleurJoueur2;

            if (contenu.endsWith(".png") || contenu.endsWith(".jpg")) {
                Image image = new Image(new File(contenu).toURI().toString());
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(80);
                imageView.setFitWidth(80);
                bouton.setGraphic(imageView);
            } else {
                bouton.setTextFill(couleur);
                bouton.setText(contenu);
            }

            nombreDeCoups++;
            if (verifierVictoire()) {
                incrementerScore();
                surlignerLigneGagnante();
                indicateurTour.setText((estTourJoueur1 ? nomJoueur1 : nomJoueur2) + " gagne !");
                desactiverBoutons();
            } else if (nombreDeCoups == 9) {
                indicateurTour.setText("Match nul !");
                desactiverBoutons();
            } else {
                estTourJoueur1 = !estTourJoueur1;
                mettreAJourIndicateurTour();
                if (!estTourJoueur1 && (modeDeJeu.equals("Joueur vs Bot Facile") || modeDeJeu.equals("Joueur vs Bot Difficile"))) {
                    gererTourBot();
                }
            }
        }
    }

    private void gererTourBot() {
        if (modeDeJeu.equals("Joueur vs Bot Facile")) {
            botFacile();
        } else if (modeDeJeu.equals("Joueur vs Bot Difficile")) {
            botDifficile();
        }
    }

    private void botFacile() {
        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (!boutons[row][col].getText().isEmpty());
        gererActionBouton(row, col);
    }

    private void botDifficile() {
        int[] bestMove = minimax(0, true);
        gererActionBouton(bestMove[1], bestMove[2]);
    }

    private int[] minimax(int depth, boolean isMaximizingPlayer) {
        String currentPlayerSymbol = isMaximizingPlayer ? symboleJoueur2 : symboleJoueur1;
        int score = evaluate();

        if (score == 10) {
            return new int[] {score - depth, -1, -1};
        }
        if (score == -10) {
            return new int[] {score + depth, -1, -1};
        }
        if (isBoardFull()) {
            return new int[] {0, -1, -1};
        }

        int bestScore = isMaximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int[] bestMove = new int[] {-1, -1, -1};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (boutons[i][j].getText().isEmpty()) {
                    boutons[i][j].setText(currentPlayerSymbol);
                    int currentScore = minimax(depth + 1, !isMaximizingPlayer)[0];
                    boutons[i][j].setText("");

                    if (isMaximizingPlayer) {
                        if (currentScore > bestScore) {
                            bestScore = currentScore;
                            bestMove = new int[] {bestScore, i, j};
                        }
                    } else {
                        if (currentScore < bestScore) {
                            bestScore = currentScore;
                            bestMove = new int[] {bestScore, i, j};
                        }
                    }
                }
            }
        }
        return bestMove;
    }

    private int evaluate() {
        for (int i = 0; i < 3; i++) {
            if (boutons[i][0].getText().equals(boutons[i][1].getText()) && boutons[i][1].getText().equals(boutons[i][2].getText())) {
                if (boutons[i][0].getText().equals(symboleJoueur2)) return 10;
                if (boutons[i][0].getText().equals(symboleJoueur1)) return -10;
            }
            if (boutons[0][i].getText().equals(boutons[1][i].getText()) && boutons[1][i].getText().equals(boutons[2][i].getText())) {
                if (boutons[0][i].getText().equals(symboleJoueur2)) return 10;
                if (boutons[0][i].getText().equals(symboleJoueur1)) return -10;
            }
        }

        if (boutons[0][0].getText().equals(boutons[1][1].getText()) && boutons[1][1].getText().equals(boutons[2][2].getText())) {
            if (boutons[0][0].getText().equals(symboleJoueur2)) return 10;
            if (boutons[0][0].getText().equals(symboleJoueur1)) return -10;
        }

        if (boutons[0][2].getText().equals(boutons[1][1].getText()) && boutons[1][1].getText().equals(boutons[2][0].getText())) {
            if (boutons[0][2].getText().equals(symboleJoueur2)) return 10;
            if (boutons[0][2].getText().equals(symboleJoueur1)) return -10;
        }

        return 0;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (boutons[i][j].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean verifierVictoire() {
        for (int i = 0; i < 3; i++) {
            if (verifierLigne(boutons[i][0], boutons[i][1], boutons[i][2]) || verifierLigne(boutons[0][i], boutons[1][i], boutons[2][i])) {
                return true;
            }
        }
        return verifierLigne(boutons[0][0], boutons[1][1], boutons[2][2]) || verifierLigne(boutons[0][2], boutons[1][1], boutons[2][0]);
    }

    private boolean verifierLigne(Button b1, Button b2, Button b3) {
        if (b1.getGraphic() != null && b2.getGraphic() != null && b3.getGraphic() != null) {
            ImageView iv1 = (ImageView) b1.getGraphic();
            ImageView iv2 = (ImageView) b2.getGraphic();
            ImageView iv3 = (ImageView) b3.getGraphic();
            return iv1.getImage().getUrl().equals(iv2.getImage().getUrl()) &&
                    iv2.getImage().getUrl().equals(iv3.getImage().getUrl());
        } else {
            return b1.getText().equals(b2.getText()) && b2.getText().equals(b3.getText()) && !b1.getText().isEmpty();
        }
    }

    private void desactiverBoutons() {
        for (Button[] ligneBoutons : boutons) {
            for (Button bouton : ligneBoutons) {
                bouton.setDisable(true);
            }
        }
    }

    private void incrementerScore() {
        if (estTourJoueur1) {
            scoreJoueur1++;
        } else {
            scoreJoueur2++;
        }
        mettreAJourScores();
    }

    private void mettreAJourScores() {
        scoreLabelJoueur1.setText(nomJoueur1 + ": " + scoreJoueur1);
        scoreLabelJoueur2.setText(nomJoueur2 + ": " + scoreJoueur2);
    }

    private void mettreAJourIndicateurTour() {
        indicateurTour.setText("Tour : " + (estTourJoueur1 ? nomJoueur1 : nomJoueur2));
        lumiereIndicateurTour.setFill(estTourJoueur1 ? couleurJoueur1 : couleurJoueur2);
        indicateurTour.setTextFill(estTourJoueur1 ? couleurJoueur1 : couleurJoueur2);
    }

    private void surlignerLigneGagnante() {
        for (int i = 0; i < 3; i++) {
            if (verifierLigne(boutons[i][0], boutons[i][1], boutons[i][2])) {
                for (int j = 0; j < 3; j++) {
                    boutons[i][j].setStyle("-fx-background-color: yellow;");
                }
                return;
            }
            if (verifierLigne(boutons[0][i], boutons[1][i], boutons[2][i])) {
                for (int j = 0; j < 3; j++) {
                    boutons[j][i].setStyle("-fx-background-color: yellow;");
                }
                return;
            }
        }
        if (verifierLigne(boutons[0][0], boutons[1][1], boutons[2][2])) {
            boutons[0][0].setStyle("-fx-background-color: yellow;");
            boutons[1][1].setStyle("-fx-background-color: yellow;");
            boutons[2][2].setStyle("-fx-background-color: yellow;");
        } else if (verifierLigne(boutons[0][2], boutons[1][1], boutons[2][0])) {
            boutons[0][2].setStyle("-fx-background-color: yellow;");
            boutons[1][1].setStyle("-fx-background-color: yellow;");
            boutons[2][0].setStyle("-fx-background-color: yellow;");
        }
    }

    @FXML
    private void gererRejouer(ActionEvent event) {
        for (Button[] ligneBoutons : boutons) {
            for (Button bouton : ligneBoutons) {
                bouton.setText("");
                bouton.setGraphic(null);
                bouton.setDisable(false);
                bouton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-border-color: white;");
            }
        }
        estTourJoueur1 = joueurCommencant.equals("Joueur 1") || (joueurCommencant.equals("Aléatoire") && Math.random() < 0.5);
        nombreDeCoups = 0;
        mettreAJourIndicateurTour();
    }

    @FXML
    private void gererQuitter(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void gererReset(ActionEvent event) {
        scoreJoueur1 = 0;
        scoreJoueur2 = 0;
        mettreAJourScores();
        gererRejouer(event);
    }

    @FXML
    private void retournerAuxParametres(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/mortpion/Classique-Parametres.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
