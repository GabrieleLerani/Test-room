package com.example.progettoispw;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MapsController {
    @FXML
    private Button gobackButton;

    private BackControllerA bca;

    public MapsController(){
        bca=new BackControllerA();
    }

    @FXML
    public void gotoHome() throws IOException, ClassNotFoundException {
        if(bca.getSpecialization().equalsIgnoreCase("User") || bca.getSpecialization().equalsIgnoreCase("Premium")) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Second-interface.fxml")));
            Stage window = (Stage) gobackButton.getScene().getWindow();
            window.setScene(GeneralScene.getHome(root));
        }else{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HomeChef.fxml")));
            Stage window = (Stage) gobackButton.getScene().getWindow();
            window.setScene(GeneralScene.getHomeChef(root));
        }
    }
}
