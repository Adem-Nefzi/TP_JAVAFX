package com.example.exercicetp6.controller;


import com.example.exercicetp6.model.User;
import com.example.exercicetp6.model.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    public void login(ActionEvent actionEvent) {
        loginButton.setStyle("-fx-background-color: #2E8B57; -fx-text-fill: white;");

        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Champs requis", "Veuillez remplir tous les champs.", Alert.AlertType.WARNING);
            return;
        }

        try {
            User currentUser = new User(username, password);
            UserDAO userDAO = new UserDAO();
            User foundUser = userDAO.find(currentUser);

            if (foundUser == null) {
                showAlert("Erreur", "Vérifiez vos informations !", Alert.AlertType.INFORMATION);
                return;
            }

            // Check if user is admin
            if (!"admin".equals(foundUser.getRole())) {
                showAlert("Accès refusé", "Seuls les administrateurs peuvent se connecter", Alert.AlertType.WARNING);
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/exercicetp6/users-list.fxml"));
            Parent scene = loader.load();

            // Pass the logged-in user to the controller
            UserListController controller = loader.getController();
            controller.setCurrentUser(foundUser);

            Stage stage = new Stage();
            stage.setTitle("Liste des utilisateurs");
            stage.setScene(new Scene(scene));
            stage.show();

            // Close login window
            ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();

        } catch (SQLException e) {
            showAlert("Erreur SQL", "Une erreur s'est produite lors de l'accès à la base de données.", Alert.AlertType.ERROR);
            e.printStackTrace();
        } catch (IOException e) {
            showAlert("Erreur d'affichage", "Impossible de charger l'interface utilisateur.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    public void initialize() {
        usernameField.setOnMouseExited(event -> {
            usernameField.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial'; -fx-pref-width: 200px; -fx-padding: 6;");
        });
    }


}