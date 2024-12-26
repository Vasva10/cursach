package org.example.library.client;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ReturnBookController {
    @FXML
    private TextField issueIdField;

    private MainController mainController;

    public void init(MainController controller) {
        this.mainController = controller;
    }

    @FXML
    public void onReturn() {
        Long issueId = Long.valueOf(issueIdField.getText());

        IssueStorage.returnBook(issueId);

        mainController.onViewAllIssues();

        Stage stage = (Stage) issueIdField.getScene().getWindow();
        stage.close();
    }
}
