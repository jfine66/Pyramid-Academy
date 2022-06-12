package four;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ConnectFour extends JFrame {
    JPanel center = new JPanel();
    JPanel reset = new JPanel();

    ArrayList<String> letters = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G"));
    int currentTurn = 0;
    ArrayList<CellButton> buttons = new ArrayList<>();
    ArrayList<CellButton> columnA = new ArrayList<>();
    ArrayList<CellButton> columnB = new ArrayList<>();
    ArrayList<CellButton> columnC = new ArrayList<>();
    ArrayList<CellButton> columnD = new ArrayList<>();
    ArrayList<CellButton> columnE = new ArrayList<>();
    ArrayList<CellButton> columnF = new ArrayList<>();
    ArrayList<CellButton> columnG = new ArrayList<>();
    JButton ButtonReset = new JButton("Reset");

    public ConnectFour() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setVisible(true);
        setLayout(null);
        setTitle("Connect Four");
        ButtonReset.setName("ButtonReset");
        ButtonReset.addActionListener(e -> resetGame());
        center.setLayout(new GridLayout(6,7,0,0));
        setButtons();
        getCols();
        reset.add(ButtonReset);
        Container frame = this.getContentPane();
        frame.setLayout(new BorderLayout());
        frame.add(center, BorderLayout.CENTER);
        frame.add(reset, BorderLayout.SOUTH);
    }

    public void setButtons() {
        for (int i = 6; i > 0; i--) {
            for (String letter : letters) {
                CellButton btn = new CellButton(" ", "Button" + letter + i);
                btn.addActionListener(e -> actionListener(btn));

                btn.setBackground(Color.GRAY);

                buttons.add(btn);
                center.add(btn);
            }
        }
    }


    public void getCols() {
        for (CellButton btn : buttons) {
            String name = btn.getName();

            if (name.startsWith("ButtonA")) {
                columnA.add(btn);
            } else if (name.startsWith("ButtonB")) {
                columnB.add(btn);
            } else if (name.startsWith("ButtonC")) {
                columnC.add(btn);
            } else if (name.startsWith("ButtonD")) {
                columnD.add(btn);
            } else if (name.startsWith("ButtonE")) {
                columnE.add(btn);
            } else if (name.startsWith("ButtonF")) {
                columnF.add(btn);
            } else if (name.startsWith("ButtonG")) {
                columnG.add(btn);
            }
        }

        Collections.reverse(columnA);
        Collections.reverse(columnB);
        Collections.reverse(columnC);
        Collections.reverse(columnD);
        Collections.reverse(columnE);
        Collections.reverse(columnF);
        Collections.reverse(columnG);
    }

    public void fillFromBottom(ArrayList<CellButton> list, int currentTurn) {
        for (CellButton cellButton : list) {
            if (cellButton.getText().equals(" ")) {
                if (currentTurn % 2 == 0) {
                    cellButton.setText("X");
                } else {
                    cellButton.setText("O");
                }
                break;
            }
        }
    }

    public void resetGame() {
        removeListeners();
        currentTurn = 0;


        for (CellButton button: buttons) {
            button.setBackground(Color.GRAY);
            button.setText(" ");
            button.addActionListener(e -> actionListener(button));
        }

    }

    private void actionListener(CellButton button) {
        String name = button.getName();

        if (name.startsWith("ButtonA")) {
            fillFromBottom(columnA, currentTurn);
            checkColsForWin(columnA);
        } else if (name.startsWith("ButtonB")) {
            fillFromBottom(columnB, currentTurn);
            checkColsForWin(columnB);
        } else if (name.startsWith("ButtonC")) {
            fillFromBottom(columnC, currentTurn);
            checkColsForWin(columnC);
        } else if (name.startsWith("ButtonD")) {
            fillFromBottom(columnD, currentTurn);
            checkColsForWin(columnD);
        } else if (name.startsWith("ButtonE")) {
            fillFromBottom(columnE, currentTurn);
            checkColsForWin(columnE);
        } else if (name.startsWith("ButtonF")) {
            fillFromBottom(columnF, currentTurn);
            checkColsForWin(columnF);
        } else if (name.startsWith("ButtonG")) {
            fillFromBottom(columnG, currentTurn);
            checkColsForWin(columnG);
        }

        checkRowForWin();
        checkForToRightDieWin();
        checkForToLeftDieWin();
        currentTurn++;

    }

    public void checkColsForWin(ArrayList<CellButton> column) {
        for (int i = 0; i < 3; i++) {
            rowColWin(column, i);
        }
    }

    public void checkRowForWin() {
        for (int i = 0; i < buttons.size() - 3; i++) {
            if (i == 4 || i == 11 || i == 18 || i == 19 || i == 25 || i == 32) {
                i += 2;
            }
            rowColWin(buttons, i);
        }
    }

    public void rowColWin(ArrayList<CellButton> buttons, int i) {
        if (buttons.get(i).getText().equals("X") && buttons.get(i + 1).getText().equals("X")
                && buttons.get(i + 2).getText().equals("X") && buttons.get(i + 3).getText().equals("X")) {
            buttons.get(i).setBackground(Color.CYAN);
            buttons.get(i + 1).setBackground(Color.CYAN);
            buttons.get(i + 2).setBackground(Color.CYAN);
            buttons.get(i + 3).setBackground(Color.CYAN);
            removeListeners();

        } else if (buttons.get(i).getText().equals("O") && buttons.get(i + 1).getText().equals("O")
                && buttons.get(i + 2).getText().equals("O") && buttons.get(i + 3).getText().equals("O")) {
            buttons.get(i).setBackground(Color.CYAN);
            buttons.get(i + 1).setBackground(Color.CYAN);
            buttons.get(i + 2).setBackground(Color.CYAN);
            buttons.get(i + 3).setBackground(Color.CYAN);
            removeListeners();
        }
    }

    public void checkForToRightDieWin() {
        for (int i = 0; i < 21; i++) {
            if (buttons.get(i).getText().equals("X") && buttons.get(i + 8).getText().equals("X")
                    && buttons.get(i + 16).getText().equals("X") && buttons.get(i + 24).getText().equals("X")) {
                buttons.get(i).setBackground(Color.RED);
                buttons.get(i + 8).setBackground(Color.RED);
                buttons.get(i + 16).setBackground(Color.RED);
                buttons.get(i + 24).setBackground(Color.RED);
                removeListeners();
            } else if (buttons.get(i).getText().equals("O") && buttons.get(i + 8).getText().equals("O")
                    && buttons.get(i + 16).getText().equals("O") && buttons.get(i + 24).getText().equals("O")) {
                buttons.get(i).setBackground(Color.RED);
                buttons.get(i + 8).setBackground(Color.RED);
                buttons.get(i + 16).setBackground(Color.RED);
                buttons.get(i + 24).setBackground(Color.RED);
                removeListeners();
            }

            if (i == 3 || i == 10 || i == 17) {
                i += 3;
            }
        }
    }

    public void checkForToLeftDieWin() {
        for (int i = 20; i > 3; i--) {
            if (buttons.get(i).getText().equals("X") && buttons.get(i + 6).getText().equals("X")
            && buttons.get(i + 12).getText().equals("X") && buttons.get(i + 18).getText().equals("X")) {
                buttons.get(i).setBackground(Color.YELLOW);
                buttons.get(i + 6).setBackground(Color.YELLOW);
                buttons.get(i + 12).setBackground(Color.YELLOW);
                buttons.get(i + 18).setBackground(Color.YELLOW);
                removeListeners();
            } else if (buttons.get(i).getText().equals("O") && buttons.get(i + 6).getText().equals("O")
                    && buttons.get(i + 12).getText().equals("O") && buttons.get(i + 18).getText().equals("O")) {
                buttons.get(i).setBackground(Color.YELLOW);
                buttons.get(i + 6).setBackground(Color.YELLOW);
                buttons.get(i + 12).setBackground(Color.YELLOW);
                buttons.get(i + 18).setBackground(Color.YELLOW);
                removeListeners();
            }

            if (i == 10 || i == 17) {
                i -= 3;
            }
        }
    }

    public void removeListeners() {
        for (CellButton btn : buttons) {
            for (ActionListener al : btn.getActionListeners()) {
                btn.removeActionListener(al);
            }
        }
    }

}