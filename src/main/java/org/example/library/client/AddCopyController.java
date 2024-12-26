package org.example.library.client;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddCopyController {
    @FXML
    private TextField bookIdField;

    @FXML
    private TextField invNumberField;

    private MainController mainController;

    public void init(MainController controller) {
        this.mainController = controller;
    }

    @FXML
    public void onAdd() {
        Long bookId = Long.valueOf(bookIdField.getText());
        String invNumber = invNumberField.getText();

        CopyStorage.addCopy(bookId, invNumber);

        mainController.onViewAllCopies();

        Stage stage = (Stage) bookIdField.getScene().getWindow();
        stage.close();
    }
}
