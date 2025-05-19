package com.example.exercicetp6.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    Connection connect = Connexion.getInstance();
    Statement statement;
    ResultSet rs;

    public User find(User user) throws SQLException {
        statement = connect.createStatement();
        rs = statement.executeQuery("SELECT * FROM user WHERE username = '" + user.getUsername() + "'" + " AND password = '" + user.getPassword() + "'");
        if (rs.next()) {
            String username = rs.getString("username");
            String password = rs.getString("password");
            String firstname = rs.getString("firstname");
            String lastname = rs.getString("lastname");
            String email = rs.getString("email");

            if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                return new User(firstname, lastname, username, password, email);
            }
        }
        return null;
    }

    //AFFICHAGE DES UTILISATEURS
    public List<User> selectAll() throws SQLException {
        List<User> users = new ArrayList<>();
        statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = statement.executeQuery("SELECT * FROM user");

        while (rs.next()) {
            // Create a NEW User object for each row
            User user = new User();
            user.setFirstname(rs.getString("firstname"));
            user.setLastname(rs.getString("lastname"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            users.add(user); // Add the new object to the list
        }

        return users;
    }


    //CREATE USERS:
    public void create(User user) throws SQLException {
        statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        String firstname = user.getFirstname();
        String lastname = user.getLastname();
        String email = user.getEmail();
        String password = user.getPassword();
        String username = user.getUsername();
        String query = "INSERT INTO user (firstname, lastname, username, password, email) VALUES ('"+firstname+"','"+lastname+"','"+username+"','"+password+"','"+email+"')";
        int result = statement.executeUpdate(query);
    }
}