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
        String query = "SELECT * FROM user WHERE username = '" + user.getUsername() + "' AND password = '" + user.getPassword() + "'";
        rs = statement.executeQuery(query);

        if (rs.next()) {
            String firstname = rs.getString("firstname");
            String lastname = rs.getString("lastname");
            String username = rs.getString("username");
            String password = rs.getString("password");
            String email = rs.getString("email");
            String gender = rs.getString("gender");
            String role = rs.getString("role");
            boolean active = rs.getBoolean("active");

            return new User(firstname, lastname, username, email, password, gender, role, active);
        }

        return null;
    }

    // ✅ SELECT ALL USERS (with all fields)
    public List<User> selectAll() throws SQLException {
        List<User> users = new ArrayList<>();
        statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = statement.executeQuery("SELECT * FROM user");

        while (rs.next()) {
            User user = new User();
            user.setFirstname(rs.getString("firstname"));
            user.setLastname(rs.getString("lastname"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password")); // optional
            user.setGender(rs.getString("gender"));
            user.setRole(rs.getString("role"));
            user.setActive(rs.getBoolean("active"));
            users.add(user);
        }
        return users;
    }

    // ✅ CREATE NEW USER (with all fields)
    public void create(User user) throws SQLException {
        statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String firstname = user.getFirstname();
        String lastname = user.getLastname();
        String email = user.getEmail();
        String password = user.getPassword();
        String username = user.getUsername();
        String gender = user.getGender();
        String role = user.getRole();
        boolean active = user.isActive();

        String query = "INSERT INTO user (firstname, lastname, username, password, email, gender, role, active) " +
                "VALUES ('" + firstname + "', '" + lastname + "', '" + username + "', '" + password + "', '" +
                email + "', '" + gender + "', '" + role + "', " + active + ")";

        int result = statement.executeUpdate(query);
    }
}
