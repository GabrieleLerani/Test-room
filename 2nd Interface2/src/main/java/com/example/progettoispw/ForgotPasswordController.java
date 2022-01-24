package com.example.progettoispw;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


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
    private TextField otp;
    @FXML
    private Label lab;

    private ForgotPasswordControllerA fpca;

    public ForgotPasswordController(){
        fpca=new ForgotPasswordControllerA();
    }

    @FXML
    public void goBack() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("enter.fxml")));
        Stage window=(Stage) back.getScene().getWindow();
        window.setScene(new Scene(root, 850, 594));
    }

    @FXML
    public void sendEmail() {
        try {
            String email = mail.getText();
            fpca.checkMail(email);
            fpca.sendMail(email);
            otp.setOpacity(1);
            otp.setText("");
            send.setOpacity(0);
            submit.setOpacity(1);
        } catch (ExceptionPass e) {
            lab.setOpacity(1);
            mail.getStyleClass().add("textInvalid");
        }
    }

    @FXML
    public void submit(){
        try {
            String password=fpca.checkOTP(otp.getText());
            if (!(password.equalsIgnoreCase(""))){
                lab.setText("Password: " + password);
            } else {
                lab.setText("Incorrect OTP");
            }
        }catch (ExceptionPass e) {
            lab.setText("Inexistent password");
        }
    }
}
