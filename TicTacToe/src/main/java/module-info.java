module fine.jonathon.tictactoe.tictactoe {
    requires javafx.controls;
    requires javafx.fxml;


    opens fine.jonathon.tictactoe.tictactoe to javafx.fxml;
    exports fine.jonathon.tictactoe.tictactoe;
}