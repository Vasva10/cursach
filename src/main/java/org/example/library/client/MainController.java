package org.example.library.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MainController {
    @FXML
    private Menu profilesMenu;
    @FXML
    private TableView<Object> tableView;

    @FXML
    private MenuBar menuBar;

    private String currentLogin;
    private String currentPassword;

    private final LibraryService service = LibraryService.getInstance();



    // Метод для настройки колонок под книги
    private void setupBookColumns() {
        tableView.getColumns().clear();

        TableColumn<Object, Number> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> {
            Book b = (Book) cellData.getValue();
            return new javafx.beans.property.SimpleIntegerProperty(b.getId().intValue());
        });

        TableColumn<Object, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(cellData -> {
            Book b = (Book) cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(b.getTitle());
        });

        TableColumn<Object, String> authorCol = new TableColumn<>("Author");
        authorCol.setCellValueFactory(cellData -> {
            Book b = (Book) cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(b.getAuthor());
        });

        TableColumn<Object, Number> yearCol = new TableColumn<>("Year");
        yearCol.setCellValueFactory(cellData -> {
            Book b = (Book) cellData.getValue();
            return new javafx.beans.property.SimpleIntegerProperty(b.getPublicationYear());
        });

        TableColumn<Object, String> isbnCol = new TableColumn<>("ISBN");
        isbnCol.setCellValueFactory(cellData -> {
            Book b = (Book) cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(b.getIsbn());
        });

        TableColumn<Object, String> genreCol = new TableColumn<>("Genre");
        genreCol.setCellValueFactory(cellData -> {
            Book b = (Book) cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(b.getGenre());
        });

        tableView.getColumns().addAll(idCol, titleCol, authorCol, yearCol, isbnCol, genreCol);
    }

    // Настройка колонок под Copies
    private void setupCopyColumns() {
        tableView.getColumns().clear();

        TableColumn<Object, Number> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> {
            Copy c = (Copy) cellData.getValue();
            return new javafx.beans.property.SimpleIntegerProperty(c.getId().intValue());
        });

        TableColumn<Object, Number> bookIdCol = new TableColumn<>("Book ID");
        bookIdCol.setCellValueFactory(cellData -> {
            Copy c = (Copy) cellData.getValue();
            return new javafx.beans.property.SimpleIntegerProperty(c.getBook().getId().intValue());
        });

        TableColumn<Object, String> invCol = new TableColumn<>("Inventory Number");
        invCol.setCellValueFactory(cellData -> {
            Copy c = (Copy) cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(c.getInventoryNumber());
        });

        TableColumn<Object, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cellData -> {
            Copy c = (Copy) cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(c.getStatus());
        });

        tableView.getColumns().addAll(idCol, bookIdCol, invCol, statusCol);
    }

    // Настройка колонок под Issues
    private void setupIssueColumns() {
        tableView.getColumns().clear();

        TableColumn<Object, Number> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> {
            Issue i = (Issue) cellData.getValue();
            return new javafx.beans.property.SimpleIntegerProperty(i.getId().intValue());
        });

        TableColumn<Object, Number> copyIdCol = new TableColumn<>("Copy ID");
        copyIdCol.setCellValueFactory(cellData -> {
            Issue i = (Issue) cellData.getValue();
            return new javafx.beans.property.SimpleIntegerProperty(i.getCopy().getId().intValue());
        });

        TableColumn<Object, String> userLoginCol = new TableColumn<>("User Login");
        userLoginCol.setCellValueFactory(cellData -> {
            Issue i = (Issue) cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(i.getUser().getLogin());
        });

        TableColumn<Object, String> issueDateCol = new TableColumn<>("Issue Date");
        issueDateCol.setCellValueFactory(cellData -> {
            Issue i = (Issue) cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(
                    i.getIssueDate() != null ? i.getIssueDate().toString() : ""
            );
        });

        TableColumn<Object, String> returnDateCol = new TableColumn<>("Return Date");
        returnDateCol.setCellValueFactory(cellData -> {
            Issue i = (Issue) cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(
                    i.getReturnDate() != null ? i.getReturnDate().toString() : ""
            );
        });

        TableColumn<Object, String> returnedCol = new TableColumn<>("Is Returned?");
        returnedCol.setCellValueFactory(cellData -> {
            Issue i = (Issue) cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(
                    i.getIsReturned() ? "Yes" : "No"
            );
        });

        tableView.getColumns().addAll(idCol, copyIdCol, userLoginCol, issueDateCol, returnDateCol, returnedCol);
    }

    @FXML
    public void onViewAllBooks() {
        List<Book> books = service.getAllBooks();
        setupBookColumns();
        tableView.getItems().setAll(books);
    }

    @FXML
    public void onAddBook() {
        openDialog("/AddBook.fxml");
    }

    @FXML
    public void onViewAllCopies() {
        List<Copy> copies = service.getAllCopies();
        setupCopyColumns();
        tableView.getItems().setAll(copies);
    }

    @FXML
    public void onAddCopy() {
        openDialog("/AddCopy.fxml");
    }

    @FXML
    public void onViewAllIssues() {
        List<Issue> issues = service.getAllIssues();
        setupIssueColumns();
        tableView.getItems().setAll(issues);
    }

    @FXML
    public void onIssueBook() {
        openDialog("/IssueBook.fxml");
    }

    @FXML
    public void onReturnBook() {
        openDialog("/ReturnBook.fxml");
    }

    @FXML
    public void onPopularBooks() {
        showInfo("Not implemented in in-memory mode.");
    }

    @FXML
    public void onBackToRegistration() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) this.menuBar.getScene().getWindow();
            stage.setScene(new Scene(root, 400, 300));
            stage.setTitle("Library Login/Registration");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error returning to registration screen: " + e.getMessage());
        }
    }

    private void openDialog(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            Object controller = loader.getController();
            if (controller instanceof InitializableWithMainController) {
                ((InitializableWithMainController) controller).init(this);
            }
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error opening dialog: " + e.getMessage());
        }
    }

    public void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg);
        alert.showAndWait();
    }

    public void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg);
        alert.showAndWait();
    }
    public void initData(String login, String password) {
        this.currentLogin = login;
        this.currentPassword = password;

        User u = UserStorage.findByLogin(login);
        if (u != null && u.getRole() == UserRole.READER) {
            // Пользователь - читатель
            // Оставляем My Profile, убираем View Reader Profiles
            profilesMenu.getItems().get(0).setVisible(true); // My Profile (первый пункт)
            profilesMenu.getItems().get(1).setVisible(false); // View Reader Profiles
        } else if (u != null && u.getRole() == UserRole.LIBRARIAN) {
            // Пользователь - библиотекарь
            profilesMenu.getItems().get(0).setVisible(false); // My Profile скрываем
            profilesMenu.getItems().get(1).setVisible(true);  // View Reader Profiles показываем
        }
    }
    public String getCurrentLogin() {
        return currentLogin;
    }
    public String getCurrentPassword() {
        return currentPassword;
    }
    // Методы:
    @FXML
    public void onMyProfile() {
        openDialog("/MyProfile.fxml");
    }

    @FXML
    public void onViewReaderProfiles() {
        openDialog("/ViewReaderProfiles.fxml");
    }
}
