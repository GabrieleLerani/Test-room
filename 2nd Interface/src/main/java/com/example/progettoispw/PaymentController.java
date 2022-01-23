package com.example.progettoispw;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Random;

public class PaymentController {

    @FXML
    private AnchorPane successPane;

    @FXML
    private AnchorPane paymentPane;

    @FXML
    private AnchorPane errorePane;


    @FXML
    private Button goBackButton;

    @FXML
    private Button confirmButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField numberCardField;

    @FXML
    private TextField CVV;

    @FXML
    private DatePicker data;

    private final PaymentControllerA pca;

    public PaymentController() throws IOException, ClassNotFoundException {
        pca = new PaymentControllerA();
    }

    @FXML
    public void goBack() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Second-interface.fxml")));
        Stage window=(Stage) goBackButton.getScene().getWindow();
        window.setScene(new Scene(root, 850, 594));
    }

    @FXML
    public void handleData() throws IOException {
        String name = nameField.getText();
        String number = numberCardField.getText();
        LocalDate localDate = data.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        String data = localDate.format(formatter);
        String cvv = CVV.getText();

        Random rand = new Random();
        int upperbound = 10;
        int int_random = rand.nextInt(upperbound);
        if(int_random%2 == 0){
            successPane.setOpacity(1);
            successPane.setDisable(false);
            paymentPane.setOpacity(0);
            paymentPane.setDisable(false);
            pca.setPremiumUser();

        }else{
            errorePane.setOpacity(1);
            errorePane.setDisable(false);

            paymentPane.setOpacity(0);
            paymentPane.setDisable(false);
        }


    }







}
