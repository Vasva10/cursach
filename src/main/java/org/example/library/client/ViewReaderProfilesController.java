package org.example.library.client;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Map;

public class ViewReaderProfilesController implements InitializableWithMainController {
    @FXML
    private ListView<String> readersList;

    @FXML
    private Label selectedUserLabel;

    @FXML
    private TableView<Object> favoritesTable;

    @FXML
    private ListView<String> notesList;

    private MainController mainController;
    private LibraryService service = LibraryService.getInstance();

    @Override
    public void init(MainController mainController) {
        this.mainController = mainController;
        updateReadersList();
        setupFavoritesTable();
    }

    private void updateReadersList() {
        readersList.getItems().clear();
        Map<String, ReaderProfile> all = service.getAllReaderProfiles();
        // Выведем логины читателей
        for (Map.Entry<String, ReaderProfile> e : all.entrySet()) {
            readersList.getItems().add(e.getKey());
        }
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

    @FXML
    public void onViewProfile() {
        String selectedLogin = readersList.getSelectionModel().getSelectedItem();
        if (selectedLogin == null) {
            mainController.showError("No reader selected.");
            return;
        }
        ReaderProfile p = service.getReaderProfile(selectedLogin);
        if (p == null) {
            mainController.showError("Profile not found.");
            return;
        }
        selectedUserLabel.setText("Selected Profile: " + selectedLogin);
        favoritesTable.getItems().setAll(p.getFavoriteBookIds());
        notesList.getItems().setAll(p.getNotes());
    }
}
