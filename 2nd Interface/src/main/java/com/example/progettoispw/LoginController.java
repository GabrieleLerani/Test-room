package com.example.progettoispw;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginController {

    @FXML
    private TextField username;
    @FXML
    private TextField password;

    @FXML
    private Button registerlink;
    @FXML
    private Button login = new Button();
    @FXML
    private Button forgotPasswordButton=new Button();
    @FXML
    private Button googleLoginButton= new Button();
    @FXML
    private CheckBox rememberMeCheckBox= new CheckBox();
    @FXML
    private Label wrong;
    @FXML
    private Label emptyUsername;
    @FXML
    private Label emptyPassword;


    private final LoginControllerA app;


    public LoginController(){
        app=new LoginControllerA();
    }

    @FXML
    public void gotoForgotPassword() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ForgotPassword.fxml")));
        Stage window=(Stage) forgotPasswordButton.getScene().getWindow();
        window.setScene(new Scene(root, 850, 594));

    }

    @FXML
    private Button developAccess;

    @FXML
    public void develop() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Home.fxml")));
        Stage window=(Stage) developAccess.getScene().getWindow();
        window.setScene(GeneralScene.getHome(root));
    }

    @FXML
    public void checkUsernameAndPassword() throws IOException, ClassNotFoundException {
        String str1 = username.getText();
        String str2 = password.getText();

        if(str1.isEmpty() && str2.isEmpty()){
            emptyUsername.setOpacity(1);
            emptyPassword.setOpacity(1);
        }else if(str1.isEmpty()){
            emptyUsername.setOpacity(1);
        }else if(str2.isEmpty()) {
            emptyPassword.setOpacity(1);
        }else{
            int s;
            LogBean temp=new LogBean(str1, str2);
            if((s=app.checkUserAndPass(temp))==1) {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Home.fxml")));
                Stage window = (Stage) login.getScene().getWindow();
                window.setScene(GeneralScene.getHome(root));
            }else if(s==2){
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HomeChef.fxml")));
                Stage window = (Stage) login.getScene().getWindow();
                window.setScene(GeneralScene.getHomeChef(root));
            }else{
                wrong.setOpacity(1);
                username.setText("");
                password.setText("");
            }
            if(rememberMeCheckBox.isSelected()){
                app.select();
            }
        }
    }

    @FXML
    public void gotoRegisterNow() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("RegisterPage.fxml")));
        Stage window=(Stage) registerlink.getScene().getWindow();
        window.setScene(new Scene(root, 850, 594));
    }


}