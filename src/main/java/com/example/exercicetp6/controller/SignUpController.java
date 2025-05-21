package com.example.exercicetp6.controller;

import com.example.exercicetp6.model.User;
import com.example.exercicetp6.model.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private ComboBox<String> roleComboBox;
    @FXML
    private RadioButton maleRadio;
    @FXML
    private RadioButton femaleRadio;
    @FXML
    private ToggleGroup genderGroup;
    @FXML
    private CheckBox activeCheckBox;


    @FXML
    public void initialize() {
        roleComboBox.getItems().addAll("admin", "user");
        activeCheckBox.setDisable(true);

        genderGroup = new ToggleGroup();
        maleRadio.setToggleGroup(genderGroup);
        femaleRadio.setToggleGroup(genderGroup);
    }

    @FXML
    private void selectRole() {
        String selectedRole = roleComboBox.getSelectionModel().getSelectedItem();
        if ("user".equals(selectedRole)) {
            activeCheckBox.setDisable(true);
            activeCheckBox.setSelected(false);
        } else {
            activeCheckBox.setDisable(false);
        }
    }
    @FXML
    public void signUp(ActionEvent event) {
        if (validateInputs()) {
            try {
                // Get gender string from selected radio button
                Toggle selectedToggle = genderGroup.getSelectedToggle();
                String gender = (selectedToggle != null) ? ((RadioButton) selectedToggle).getText() : "";

                // Get other fields
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String username = usernameField.getText();
                String email = emailField.getText();
                String password = passwordField.getText();
                String role = roleComboBox.getValue();
                boolean active = activeCheckBox.isSelected();

                // Now create User with correct parameters order:
                User user = new User(firstName, lastName, username,email ,password,gender, role, active);

                UserDAO userDAO = new UserDAO();
                userDAO.create(user);

                System.out.println("New user created:");
                System.out.println("First Name: " + user.getFirstname());
                System.out.println("Last Name: " + user.getLastname());
                System.out.println("Username: " + user.getUsername());
                System.out.println("Email: " + user.getEmail());

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/exercicetp6/users-list.fxml"));
                Parent root = loader.load();

                UserListController controller = loader.getController();
                controller.setUser(user);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Liste des utilisateurs");
                stage.show();

                ((Stage) (((Node) event.getSource()).getScene().getWindow())).close();

            } catch (IOException | SQLException e) {
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
        String selectedRole = roleComboBox.getSelectionModel().getSelectedItem();
        boolean isActive = activeCheckBox.isSelected();
        Toggle selectedToggle = genderGroup.getSelectedToggle();
        String gender = (selectedToggle != null) ? ((RadioButton) selectedToggle).getText() : "";



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