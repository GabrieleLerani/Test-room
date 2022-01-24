package com.example.progettoispw;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class StarterController {
    @FXML
    private Button enter;

    private int rem;
    private LogBean login;
    private String spec;
    private StarterControllerA sca;

    public StarterController(){
        sca=new StarterControllerA();
    }

    @FXML
    public void Enter() throws IOException {
        try {
            login=sca.getSpec();
            if (login != null) {
                rem = login.getCheck();
                spec=login.getSpec();
                if (rem != 0 && (spec.equalsIgnoreCase("User") || spec.equalsIgnoreCase("Premium"))) {
                    Parent root = FXMLLoader.load(Objects.requireNonNull(StarterController.class.getResource("Second-interface.fxml")));
                    Stage window = (Stage) enter.getScene().getWindow();
                    window.setScene(GeneralScene.getHome(root));
                    return;
                }else if(rem != 0 && spec.equalsIgnoreCase("Chef")){
                    Parent root = FXMLLoader.load(Objects.requireNonNull(StarterController.class.getResource("HomeChef.fxml")));
                    Stage window = (Stage) enter.getScene().getWindow();
                    window.setScene(new Scene(root, 850, 594));
                    return;
                }
            }
            Parent root = FXMLLoader.load(Objects.requireNonNull(StarterController.class.getResource("login.fxml")));
            Stage window = (Stage) enter.getScene().getWindow();
            window.setScene(new Scene(root, 850, 594));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
