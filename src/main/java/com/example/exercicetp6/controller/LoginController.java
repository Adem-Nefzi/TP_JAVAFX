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
        // Change button color on click
        loginButton.setStyle("-fx-background-color: #2E8B57; -fx-text-fill: white;");

        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs requis");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }

        try {
            User currentUser = new User(username, password);
            UserDAO userDAO = new UserDAO();

            if (userDAO.find(currentUser) == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Vérifiez vos informations !");
                alert.showAndWait();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/exercicetp6/users-list.fxml"));
                Parent scene = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Liste des utilisateurs");
                stage.setScene(new Scene(scene));
                stage.show();

                // Close login window
                Stage loginStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                loginStage.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur SQL");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur s'est produite lors de l'accès à la base de données.");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur d'affichage");
            alert.setHeaderText(null);
            alert.setContentText("Impossible de charger l'interface utilisateur.");
            alert.showAndWait();
        }
    }

    @FXML
    public void initialize() {
        usernameField.setOnMouseExited(event -> {
            usernameField.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial'; -fx-pref-width: 200px; -fx-padding: 6;");
        });
    }
}