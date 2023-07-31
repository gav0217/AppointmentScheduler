package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DialogController {

@FXML
private Label messageLabel;

@FXML
private Button okButton;

private Stage dialogStage;

@FXML
private void closeDialog() {
    dialogStage.close();
}

}

