package org.example.library.client;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddBookController implements InitializableWithMainController {
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField yearField;
    @FXML
    private TextField isbnField;
    @FXML
    private TextField genreField;

    private MainController mainController;

    @Override
    public void init(MainController controller) {
        this.mainController = controller;
    }

    @FXML
    public void onAdd() {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String isbn = isbnField.getText().trim();
        String genre = genreField.getText().trim();

        int year;
        try {
            year = Integer.parseInt(yearField.getText().trim());
        } catch (NumberFormatException e) {
            mainController.showError("Year must be a number.");
            return;
        }

        if (title.isEmpty() || author.isEmpty() || isbn.isEmpty() || genre.isEmpty()) {
            mainController.showError("All fields are required.");
            return;
        }

        // Теперь вызываем метод сервиса
        LibraryService.getInstance().addBook(title, author, year, isbn, genre);

        mainController.onViewAllBooks();
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }
}
