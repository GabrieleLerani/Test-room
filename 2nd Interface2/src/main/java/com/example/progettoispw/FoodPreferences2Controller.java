package com.example.progettoispw;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class FoodPreferences2Controller {
    @FXML
    private Button goBackButton;
    @FXML
    private RadioButton rb1;
    @FXML
    private RadioButton rb2;
    @FXML
    private RadioButton rb3;
    @FXML
    private RadioButton rb4;
    @FXML
    private RadioButton rb5;
    @FXML
    private RadioButton rb6;
    @FXML
    private Text fpText;
    @FXML
    private ChoiceBox<String> foodPrefBox;

    private final String[] pref = {"Vegetarian","Vegan","I have no particular preferences"};
    private final String[] allergies ={"Dried fruit","Fish","Eggs","Milk","Meat","No allergies"};
    private final AlimentarPreferencesControllerA apca;
    private final BackControllerA bca;

    public FoodPreferences2Controller() throws IOException, ClassNotFoundException {
        apca = new AlimentarPreferencesControllerA();
        bca = new BackControllerA();
    }


    public void initialize(){

        foodPrefBox.getItems().addAll(pref);

        String pref=apca.getPref();
        switch (pref) {
            case "Vegetarian" -> fpText.setText("Vegetarian");
            case "Vegan" -> fpText.setText("Vegan");
            case "None" -> fpText.setText("No particular preferences");
        }

        ArrayList<String> all=apca.getAll();
        for (String s : all) {
            switch (s) {
                case "Dried fruit" -> rb1.setSelected(true);
                case "Fish" -> rb2.setSelected(true);
                case "Eggs" -> rb3.setSelected(true);
                case "Milk" -> rb4.setSelected(true);
                case "Meat" -> rb5.setSelected(true);
                case "No Allergies" -> rb6.setSelected(true);
            }
        }
    }

    @FXML
    public void goBack() throws IOException, ClassNotFoundException {
        if (bca.getSpecialization().equalsIgnoreCase("User") || bca.getSpecialization().equalsIgnoreCase("Premium")) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Second-interface.fxml")));
            Stage window = (Stage) goBackButton.getScene().getWindow();
            window.setScene(GeneralScene.getHome(root));
        }else{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HomeChef.fxml")));
            Stage window = (Stage) goBackButton.getScene().getWindow();
            window.setScene(new Scene(root, 850, 594));
        }
    }


    @FXML
    public void clear(){
        apca.clear();
        rb1.setSelected(false);
        rb2.setSelected(false);
        rb3.setSelected(false);
        rb4.setSelected(false);
        rb5.setSelected(false);
        rb6.setSelected(false);
    }

    @FXML
    public void confirm() throws IOException {
        final RadioButton[] allRB = {rb1,rb2,rb3,rb4,rb5,rb6};
        String preferences = foodPrefBox.getSelectionModel().getSelectedItem();
        ArrayList<String> listOfAllergies = new ArrayList<>() ;
        for(int i=0;i<6;i++){
            if(allRB[i].isSelected()){
                listOfAllergies.add(allergies[i]);
                if(allRB[i].getText().equals("No allergies")){
                    listOfAllergies.clear();
                    listOfAllergies.add("No allergies");
                }
            }
        }
        apca.setPref(preferences, listOfAllergies);
        System.out.println(preferences+"\n"+listOfAllergies);
    }

}
