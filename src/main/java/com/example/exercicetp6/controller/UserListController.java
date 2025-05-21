package com.example.exercicetp6.controller;

import com.example.exercicetp6.model.User;
import com.example.exercicetp6.model.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserListController implements Initializable {
    private User currentUser;

    @FXML
    private Button addUserButton;
    @FXML
    private ListView<User> userListView;

    @FXML
    private TableView<User> userTableView;

    @FXML
    private TableColumn<User, String> firstNameColumn;

    @FXML
    private TableColumn<User, String> lastNameColumn;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableColumn<User, String> genderColumn;

    @FXML
    private TableColumn<User, String> roleColumn;

    @FXML
    private TableColumn<User, Boolean> activeColumn;

    // Create an observable list for the users

    public void setCurrentUser(User user) {
        this.currentUser = user;
        updateAddButtonState();
    }

    private void updateAddButtonState() {
        if (currentUser != null && "admin".equals(currentUser.getRole())) {
            addUserButton.setDisable(!currentUser.isActive());
        } else {
            addUserButton.setDisable(true);
        }
    }
    private ObservableList<User> userList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        activeColumn.setCellValueFactory(new PropertyValueFactory<>("active"));

        updateAddButtonState();

        UserDAO userDAO = new UserDAO();
        try {
            userList = FXCollections.observableArrayList(userDAO.selectAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        userListView.setItems(userList);
        userTableView.setItems(userList);
    }


    @FXML
    private void addUser(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/exercicetp6/signup.fxml"));
        Parent root = loader.load();

        // Display the sign-up scene
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("S'inscrire");
        stage.show();

        // Close the current window
        ((Stage)(((Node)event.getSource()).getScene().getWindow())).close();
    }

    // Method to add a user from another window
    public void setUser(User user) {
        // Don't add directly to userList
        // Instead refresh the entire list from database
        try {
            userList.setAll(new UserDAO().selectAll()); // This replaces all items
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}