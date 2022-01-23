package com.example.progettoispw;


import com.example.progettoispw.RecipeModel.Ingredient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ShoppingList2Controller implements Initializable {
    @FXML
    private Button gotohomeButton;

    @FXML
    private TextField nameandamountField;

    @FXML
    private Button saveListButton;

    @FXML
    private TableView<Ingredient> listTable;

    @FXML
    private TableColumn<Ingredient,String> nameCol;

    @FXML
    private  TableColumn<Ingredient,String> amountCol;

    ObservableList<Ingredient> observableList = FXCollections.observableArrayList();
    private BackControllerA bca;

    public ShoppingList2Controller(){
        bca=new BackControllerA();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        listTable.setItems(observableList);
    }

    @FXML
    public void gotoHome() throws IOException, ClassNotFoundException {
        if (bca.getSpecialization().equalsIgnoreCase("User") || bca.getSpecialization().equalsIgnoreCase("Premium")) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Second-interface.fxml")));
            Stage window = (Stage) gotohomeButton.getScene().getWindow();
            window.setScene(GeneralScene.getHome(root));
        }else{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HomeChef.fxml")));
            Stage window = (Stage) gotohomeButton.getScene().getWindow();
            window.setScene(new Scene(root, 850, 594));
        }
    }

    @FXML
    public void addIngredient(){
        String str = nameandamountField.getText();
        String[] result = str.split("\\s");
        String ingName = result[0];
        String ingAm = result[1];

        if(ingName.isEmpty() && ingAm.isEmpty() || !ingAm.matches("[0-9]+") || !ingName.matches("[a-zA-Z]+")) {
            nameandamountField.getStyleClass().add("textInvalid");
        }
        else{
            listTable.getItems().add(new Ingredient(ingName,ingAm));
            nameandamountField.setText("");
            nameandamountField.getStyleClass().removeAll("textInvalid");
        }
    }

    @FXML
    public void removeIngredient(){
        ObservableList<Ingredient> singleIngredient;
        observableList = listTable.getItems();
        singleIngredient = listTable.getSelectionModel().getSelectedItems();
        singleIngredient.forEach(observableList::remove);

    }

    public void handleSave(){
        Stage secondaryStage = new Stage();
        FileChooser fc = new FileChooser();
        fc.setTitle("Save shopping list");
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        if(observableList.isEmpty()){
            secondaryStage.initOwner(this.saveListButton.getScene().getWindow());
            Alert emptyTableAlert = new Alert(Alert.AlertType.ERROR, "EMPTY TABLE",ButtonType.OK);
            emptyTableAlert.setContentText("You have nothing to save");
            emptyTableAlert.initModality(Modality.APPLICATION_MODAL);
            emptyTableAlert.initOwner(this.saveListButton.getScene().getWindow());
            emptyTableAlert.showAndWait();
            if(emptyTableAlert.getResult() == ButtonType.OK){
                emptyTableAlert.close();
            }
        }
        else{
            File file = fc.showSaveDialog(secondaryStage);
            if(file != null){
                saveFile(observableList,file);
            }
        }
    }

    public void saveFile(ObservableList<Ingredient> observableList,File file){
        try {
            BufferedWriter outWriter = new BufferedWriter(new FileWriter(file));
            for(Ingredient i : observableList){
                outWriter.write(String.valueOf(i));
                System.out.println(i);
                outWriter.newLine();
            }
            System.out.println(observableList);
            outWriter.close();
        }
        catch (IOException e) {
            Alert ioAlert = new Alert(Alert.AlertType.ERROR,"OPS!",ButtonType.OK);
            ioAlert.setContentText("Sorry. An error has occurred");
            ioAlert.showAndWait();
            if(ioAlert.getResult() == ButtonType.OK){
                ioAlert.close();
            }
        }
    }

}
