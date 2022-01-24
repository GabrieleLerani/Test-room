package com.example.progettoispw;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegisterPageController implements Initializable {
    @FXML
    private Button registerButton;

    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField usernameField;

    @FXML
    private Label emailinv;
    @FXML
    private Label userinv;

    @FXML private Button goBack;

    @FXML
    private ChoiceBox<String> cl;

    @FXML
    private ChoiceBox<String> spec;

    private final String[] clStr = {"low","medium","hard"};
    private static Logger logger=Logger.getLogger(RegisterPageController.class.getName());
    private LogBean a;
    private RegisterControllerA reg;

    public RegisterPageController() {
        a=new LogBean();
        reg=new RegisterControllerA();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cl.getItems().addAll(clStr);
        spec.getItems().add("chef");
        spec.getItems().add("user");

    }

    @FXML
    public void gotoHome() throws IOException {
        if(!emailField.getText().contains("@") || !emailField.getText().contains(".")){
            logger.log(Level.INFO, "Inserire una mail valida");
            emailinv.setOpacity(1);
            emailField.setText("");
            return;
        }
        a.setSpec(spec.getSelectionModel().getSelectedItem());
        a.setEmail(emailField.getText());
        a.setUser(usernameField.getText());
        a.setPass(passwordField.getText());
        a.setCL(cl.getSelectionModel().getSelectedItem());
        try {
            reg.register(a);
            reg.initFile(a);
        }catch(Exception e){
            e.printStackTrace();
            userinv.setOpacity(1);
            usernameField.setText("");
            return;
        }
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Home.fxml")));
        Stage window=(Stage) registerButton.getScene().getWindow();
        window.setScene(new Scene(root, 850, 594));
    }

    @FXML
    public void goBackToLogin() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("enter.fxml")));
        Stage window=(Stage) goBack.getScene().getWindow();
        window.setScene(new Scene(root, 850, 594));
    }

}
