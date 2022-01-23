package com.example.progettoispw;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterPageController {
    @FXML
    private Button registerButton;
    @FXML
    private TextField specField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField cookingLevelField;
    @FXML
    private Label email_inv;
    @FXML
    private Label user_inv;

    @FXML private Button goBack;

    private LogBean a;
    private RegisterControllerA reg;

    public RegisterPageController() {
        a=new LogBean();
        reg=new RegisterControllerA();
    }

    @FXML
    public void gotoHome() throws IOException {
        if(!emailField.getText().contains("@") || !emailField.getText().contains(".")){
            System.out.println("Inserire una mail valida");
            email_inv.setOpacity(1);
            emailField.setText("");
            return;
        }
        a.setSpec(specField.getText());
        a.setEmail(emailField.getText());
        a.setUser(usernameField.getText());
        a.setPass(passwordField.getText());
        a.setCL(cookingLevelField.getText());
        try {
            reg.register(a);
            reg.initFile(a);
        }catch(Exception e){
            e.printStackTrace();
            user_inv.setOpacity(1);
            usernameField.setText("");
            return;
        }
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Stage window=(Stage) registerButton.getScene().getWindow();
        window.setScene(new Scene(root, 850, 594));
    }

    @FXML
    public void goBackToLogin() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Stage window=(Stage) goBack.getScene().getWindow();
        window.setScene(new Scene(root, 850, 594));
    }

}
