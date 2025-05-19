package com.example.exercicetp6.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Connexion {
    private String url = "jdbc:mysql://localhost:3306/students";
    private String user = "root";
    private String passwd = "";
    private static Connection connect;
    private Connexion(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connection Established successfully");
            connect = DriverManager.getConnection(url, user, passwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getInstance(){
        if(connect == null){
            new Connexion();
        }
        return connect;
    }
}
