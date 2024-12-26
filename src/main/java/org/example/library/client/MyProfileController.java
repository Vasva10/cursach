package org.example.library.client;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MyProfileController implements InitializableWithMainController {
    @FXML
    private TableView<Object> favoritesTable;
    @FXML
    private ListView<String> notesList;
    @FXML
    private TextField bookIdField;
    @FXML
    private TextField noteField;

    private MainController mainController;
    private LibraryService service = LibraryService.getInstance();
    private User currentUser;
    private ReaderProfile profile;

    @Override
    public void init(MainController mainController) {
        this.mainController = mainController;
        this.currentUser = UserStorage.findByLogin(mainController.getCurrentLogin());
        this.profile = service.getReaderProfile(this.currentUser.getLogin());

        setupFavoritesTable();
        updateUI();
    }

    private void setupFavoritesTable() {
        favoritesTable.getColumns().clear();
        TableColumn<Object,String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(cellData -> {
            Long bookId = (Long) cellData.getValue();
            Book b = BookStorage.findById(bookId);
            return new javafx.beans.property.SimpleStringProperty(
                    b != null ? b.getTitle() : "Unknown"
            );
        });
        favoritesTable.getColumns().add(titleCol);
    }

    private void updateUI() {
        favoritesTable.getItems().setAll(profile.getFavoriteBookIds());
        notesList.getItems().setAll(profile.getNotes());
    }

    @FXML
    public void onAddFavoriteBook() {
        String text = bookIdField.getText().trim();
        if (text.isEmpty()) {
            mainController.showError("Book ID is empty.");
            return;
        }
        try {
            Long bookId = Long.valueOf(text);
            service.addBookToFavorites(currentUser.getLogin(), bookId);
            updateUI();
        } catch (NumberFormatException e) {
            mainController.showError("Invalid book ID.");
        }
    }

    @FXML
    public void onAddNote() {
        String note = noteField.getText().trim();
        if (note.isEmpty()) {
            mainController.showError("Note is empty.");
            return;
        }
        service.addNoteToProfile(currentUser.getLogin(), note);
        updateUI();
    }
}
