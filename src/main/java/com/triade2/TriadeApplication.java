package com.triade2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TriadeApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TriadeApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("TRÍADE");
        stage.setScene(scene);
        stage.show();
    }

    public static void mostrarInicio(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TriadeApplication.class.getResource("inicio-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Cadastro de Clientes");
        stage.setScene(scene);
    }

    public static void main(String[] args) {
        launch();
    }
}