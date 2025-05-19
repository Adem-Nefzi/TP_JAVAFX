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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class SignUpController {
    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    public void signUp(ActionEvent event) {
        // Validate input fields
        if (validateInputs()) {
            try {
                // Create user object
                User user = new User(
                        firstNameField.getText(),
                        lastNameField.getText(),
                        usernameField.getText(),
                        emailField.getText(),
                        passwordField.getText()
                );

                UserDAO userDAO = new UserDAO();
                try {
                    userDAO.create(user);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                // Display user information in console
                System.out.println("New user created:");
                System.out.println("First Name: " + user.getFirstname());
                System.out.println("Last Name: " + user.getLastname());
                System.out.println("Username: " + user.getUsername());
                System.out.println("Email: " + user.getEmail());

                // Load the users list window
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/exercicetp6/users-list.fxml"));
                Parent root = loader.load();

                // Get controller and pass the user object
                UserListController controller = loader.getController();
                controller.setUser(user);

                // Display the new scene
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Liste des utilisateurs");
                stage.show();

                // Close the current window
                ((Stage)(((Node)event.getSource()).getScene().getWindow())).close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validateInputs() {
        // Check if fields are empty
        if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() ||
                usernameField.getText().isEmpty() || emailField.getText().isEmpty() ||
                passwordField.getText().isEmpty() || confirmPasswordField.getText().isEmpty()) {

            showAlert(Alert.AlertType.ERROR, "Error", "Empty Fields",
                    "Please fill in all fields.");
            return false;
        }

        // Check if passwords match
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            showAlert(Alert.AlertType.ERROR, "Error", "Password Mismatch",
                    "Password and confirmation do not match.");
            return false;
        }

        // Check email format (basic validation)
        if (!emailField.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid Email",
                    "Please enter a valid email address.");
            return false;
        }

        return true;
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}