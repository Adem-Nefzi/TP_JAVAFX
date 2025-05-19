package com.example.exercicetp6;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        com.example.exercicetp6.model.Connexion.getInstance();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/exercicetp6/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 300);
        stage.setTitle("login");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}