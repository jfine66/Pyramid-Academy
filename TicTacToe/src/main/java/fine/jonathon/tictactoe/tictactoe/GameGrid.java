package fine.jonathon.tictactoe.tictactoe;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;

public class GameGrid {
    private final GridPane grid;
    private final Button[][] btns;
    private final Label display;
    private int currentTurn;
    private boolean gameWon;

    public GameGrid(GridPane gridPane, Label label) {
        this.grid = gridPane;
        this.display = label;
        this.btns = new Button[3][3];
        this.currentTurn = 0;
        this.gameWon = false;
        setGridSize();
        setButtons();
        addListeners();
    }

    private void setGridSize() {
        this.grid.setMinWidth(300);
        this.grid.setMinHeight(300);
    }

    private void addListeners() {
        for (Button[] btn : btns) {
            for (Button button : btn) {
                button.setOnAction((event) -> {
                    if (this.currentTurn % 2 == 0) {
                        button.setText("X");
                        this.display.setText("Turn: O");
                    } else {
                        button.setText("O");
                        this.display.setText("Turn: X");
                    }

                    button.setOnAction(null);
                    checkDieWin();
                    checkColWin();
                    checkRowWin();
                    checkForDraw();
                    this.currentTurn++;
                });

            }
        }
    }

    // Used the first time to app is launched to create buttons
    private void setButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button btn = new Button(" ");
                btn.setMinWidth(100);
                btn.setMinHeight(100);
                btns[i][j] = btn;
                GridPane.setConstraints(btn,i ,j);
                this.grid.getChildren().add(btn);
            }
        }
    }

    // Checks each row for a win
    private void checkRowWin() {
        for (Button[] btn : btns) {
            if (btn[0].getText().equals("X") && btn[1].getText().equals("X") && btn[2].getText().equals("X")) {
                this.display.setText("X WINS!!!!!");
                gameWon = true;
                clearListeners();
            } else if (btn[0].getText().equals("O") && btn[1].getText().equals("O") && btn[2].getText().equals("O")) {
                this.display.setText("O WINS!!!!");
                gameWon = true;
                clearListeners();
            }
        }
    }

    // Checks each column for a win
    private void checkColWin() {
        int xCount = 0;
        int oCount = 0;

        for (int i = 0; i < btns.length; i++) {
            for (int j = 0; j < btns[i].length; j++) {
                if (btns[j][i].getText().equals("X")) {
                    xCount++;
                } else if (btns[j][i].getText().equals("O")) {
                    oCount++;
                }
            }

            if (xCount == 3) {
                this.display.setText("X WINS");
                gameWon = true;
                clearListeners();
                break;
            } else if (oCount == 3) {
                this.display.setText("O WINS");
                gameWon = true;
                clearListeners();
                break;
            }

            oCount = 0;
            xCount = 0;
        }
    }

    //Check diagonals, since there are only two I did it manually but for a larger grid it can be done with a loop
    // The first if-else has return so that if either options is true it doesn't bother checking the second if-else
    private void checkDieWin() {
        if (btns[0][0].getText().equals("X") && btns[1][1].getText().equals("X") && btns[2][2].getText().equals("X")) {
            this.display.setText("X WINS!!!!!");
            gameWon = true;
            clearListeners();
            return;
        } else if (btns[0][0].getText().equals("O") && btns[1][1].getText().equals("O") && btns[2][2].getText().equals("O")) {
            this.display.setText("O WINS!!!!");
            gameWon = true;
            clearListeners();
            return;
        }

        if (btns[0][2].getText().equals("X") && btns[1][1].getText().equals("X") && btns[2][0].getText().equals("X")) {
            this.display.setText("X WINS!!!!!");
            gameWon = true;
            clearListeners();
        } else if (btns[0][2].getText().equals("O") && btns[1][1].getText().equals("O") && btns[2][0].getText().equals("O")) {
            this.display.setText("O WINS!!!!");
            gameWon = true;
            clearListeners();
        }
    }

    // Checks for draw
    private void checkForDraw() {
        if (gameWon) {
            return;
        }

        for (Button[] btn : btns) {
            for (Button button : btn) {
                if (button.getText().equals(" ")) {
                    return;
                }
            }
        }

        this.display.setText("DRAW");
    }

    // Sets the button text back to nothing
    private void clearButtons() {
        for (Button[] btn : btns) {
            for (Button button : btn) {
                button.setText(" ");
            }
        }
    }

    // removes all listeners so that you can not click on the buttons after game is over
    private void clearListeners() {
        for (Button[] btn : btns) {
            for (Button button : btn) {
                button.setOnAction((event) -> button.setOnAction(null));
            }
        }
    }

    // Method used to reset game, used in StartApplication
    public void playAgain() {
        clearButtons();
        addListeners();
        gameWon = false;
        this.currentTurn = 0;
        this.display.setText("Turn: X");
    }

}
