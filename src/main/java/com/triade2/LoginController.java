package com.triade2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private TextField nomeUsuarioTF;
    @FXML
    private PasswordField senhaPF;

    @FXML
    protected void onLoginClicado() {
        String nomeUsuario = nomeUsuarioTF.getText();
        String senha = senhaPF.getText();

        if (nomeUsuario.equals("admin") && senha.equals("123")) {
            try {
                Stage stage = (Stage) nomeUsuarioTF.getScene().getWindow();
                TriadeApplication.mostrarInicio(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Usuário ou senha inválidos!");
            alert.showAndWait();
            System.out.println("Login falhou!");
        }
    }
}