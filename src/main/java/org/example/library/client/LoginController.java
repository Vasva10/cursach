package org.example.library.client;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    private ComboBox<UserRole> roleComboBox; // Добавили ComboBox для роли

    private String currentLogin;
    private String currentPassword;

    @FXML
    public void initialize() {
        // Инициализируем ComboBox двумя ролями
        roleComboBox.getItems().addAll(UserRole.READER, UserRole.LIBRARIAN);
        // По умолчанию можно поставить READER
        roleComboBox.getSelectionModel().select(UserRole.READER);
    }

    @FXML
    public void onSignIn() {
        String login = loginField.getText().trim();
        String password = passwordField.getText();

        User user = UserStorage.findByLogin(login);
        if (user == null) {
            messageLabel.setText("User not found. Please register or try another login.");
            return;
        }

        if (!user.getPasswordHash().equals(password)) {
            messageLabel.setText("Invalid password.");
            return;
        }

        // Успешный вход
        currentLogin = login;
        currentPassword = password;
        messageLabel.setText("Login successful!");
        openMainView();
    }

    @FXML
    public void onRegister() {
        String login = loginField.getText().trim();
        String password = passwordField.getText();
        UserRole selectedRole = roleComboBox.getValue();

        if (login.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Login and password cannot be empty.");
            return;
        }

        User existing = UserStorage.findByLogin(login);
        if (existing != null) {
            messageLabel.setText("User already exists. Choose another login.");
            return;
        }

        // Проверка роли при регистрации
        boolean librarianExists = UserStorage.hasLibrarian();

        if (librarianExists && selectedRole == UserRole.LIBRARIAN) {
            messageLabel.setText("A LIBRARIAN already exists. You cannot register as LIBRARIAN.");
            return;
        }

        // Если первый LIBRARIAN не существует, можно стать LIBRARIAN или READER,
        // Если LIBRARIAN существует, а роль выбран READER, тоже ок.
        User newUser = new User(login, password, "New User", selectedRole);
        UserStorage.addUser(newUser);

        messageLabel.setText("User registered successfully as " + selectedRole + "! You can now sign in.");
    }

    private void openMainView() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/MainView.fxml"));
            javafx.scene.Parent root = loader.load();
            MainController mainController = loader.getController();
            mainController.initData(currentLogin, currentPassword);

            javafx.stage.Stage stage = (javafx.stage.Stage) messageLabel.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root, 800, 600));
            stage.setTitle("Library Management");
            stage.show();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            messageLabel.setText("Error loading main view.");
        }
    }
}
