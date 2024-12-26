package org.example.library.client;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class IssueBookController {
    @FXML
    private TextField copyIdField;
    @FXML
    private TextField userLoginField;

    private MainController mainController;

    public void init(MainController controller) {
        this.mainController = controller;
    }

    @FXML
    public void onIssue() {
        Long copyId = Long.valueOf(copyIdField.getText());
        String userLogin = userLoginField.getText();

        IssueStorage.issueBook(copyId, userLogin);

        mainController.onViewAllIssues();

        Stage stage = (Stage) copyIdField.getScene().getWindow();
        stage.close();
    }
}
