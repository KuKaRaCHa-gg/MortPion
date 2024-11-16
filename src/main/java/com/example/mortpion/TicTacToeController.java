package com.example.mortpion;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class TicTacToeController {
    @FXML
    private GridPane gameGrid; // Ensure this ID matches the GridPane in your FXML
    private boolean isXTurn = true;
    private Button[][] buttons = new Button[3][3]; // Array to store buttons for easy access

    @FXML
    public void initialize() {
        int row, col;
        // Initialize buttons array and set action handlers
        for (Node node : gameGrid.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                row = GridPane.getRowIndex(node);
                col = GridPane.getColumnIndex(node);
                buttons[row][col] = button;
                int finalRow = row;
                int finalCol = col;
                button.setOnAction(e -> handleButtonClick(button, finalRow, finalCol));
            }
        }
    }

    private void handleButtonClick(Button button, int row, int col) {
        if (!button.getText().isEmpty()) {
            return; // Do nothing if the cell is already occupied
        }

        button.setText(isXTurn ? "X" : "O");
        button.setStyle("-fx-text-fill: " + (isXTurn ? "red" : "blue") + ";");
        isXTurn = !isXTurn; // Switch turn
        checkForWinner();
    }

    private void checkForWinner() {
        // Check rows, columns, and diagonals for a win
        if (checkForWin()) {
            endGame();
        }
    }

    private boolean checkForWin() {
        for (int i = 0; i < 3; i++) {
            // Check rows and columns
            if (checkRowCol(buttons[i][0].getText(), buttons[i][1].getText(), buttons[i][2].getText()) ||
                    checkRowCol(buttons[0][i].getText(), buttons[1][i].getText(), buttons[2][i].getText())) {
                return true;
            }
        }

        // Check diagonals
        return (checkRowCol(buttons[0][0].getText(), buttons[1][1].getText(), buttons[2][2].getText()) ||
                checkRowCol(buttons[0][2].getText(), buttons[1][1].getText(), buttons[2][0].getText()));
    }

    private boolean checkRowCol(String c1, String c2, String c3) {
        return !c1.isEmpty() && c1.equals(c2) && c2.equals(c3);
    }

    private void endGame() {
        for (Button[] row : buttons) {
            for (Button button : row) {
                button.setDisable(true);
            }
        }

    }

    @FXML
    private void resetGame() {
        for (Button[] row : buttons) {
            for (Button button : row) {
                button.setText("");
                button.setDisable(false);
                button.setStyle("-fx-text-fill: black;");
            }
        }
        isXTurn = true; // Reset to X's turn
    }
}
