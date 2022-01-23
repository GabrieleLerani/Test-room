package com.example.progettoispw;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;


public class ForgotPasswordController {
    @FXML
    private Button back;
    @FXML
    private TextField mail;
    @FXML
    private Button send;
    @FXML
    private Button submit;
    @FXML
    private TextField OTP;
    @FXML
    private Label lab;

    private String email;
    private String password;
    private ForgotPasswordControllerA fpca;

    public ForgotPasswordController(){
        fpca=new ForgotPasswordControllerA();
    }

    @FXML
    public void goBack() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
        Stage window=(Stage) back.getScene().getWindow();
        window.setScene(new Scene(root, 850, 594));
    }

    @FXML
    public void sendEmail() {
        try {
            email = mail.getText();
            fpca.checkMail(email);
            fpca.sendMail(email);
            OTP.setOpacity(1);
            OTP.setText("");
            send.setOpacity(0);
            submit.setOpacity(1);
        } catch (ExceptionPass e) {
            lab.setText("Invalid mail");
        }
    }

    @FXML
    public void Submit(){
        try {
            if (!(password=fpca.checkOTP(OTP.getText())).equalsIgnoreCase("")){
                lab.setText("Password: " + password);
            } else {
                lab.setText("Incorrect OTP");
            }
        }catch (ExceptionPass e) {
            lab.setText("Inexistent password");
        }
    }
}
