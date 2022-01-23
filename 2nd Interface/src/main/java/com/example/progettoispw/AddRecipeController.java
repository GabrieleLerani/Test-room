package com.example.progettoispw;

import com.example.progettoispw.RecipeModel.Ingredient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class AddRecipeController {
    @FXML private TextArea insertTitle;
    @FXML private TextArea addDescription;
    @FXML private TextArea cookingTime;

    @FXML Label dtype;

    @FXML private MenuButton dtypeMenu;
    @FXML private MenuItem m1;
    @FXML private MenuItem m2;
    @FXML private MenuItem m3;

    @FXML private Button addIngredient;

    @FXML private TextField ingredientName;
    @FXML private TextField ingredientAmount;

    @FXML Label selectedButton;

    @FXML private Button uploadImage;

    @FXML private Button goBackButton;
    @FXML private Button confirmRecipeButton;

    @FXML private AnchorPane root;
    @FXML private ScrollPane upper;
    @FXML private AnchorPane anchor;
    @FXML private RadioButton rb1;
    @FXML private RadioButton rb2;
    @FXML private RadioButton rb3;
    @FXML private RadioButton rb4;
    @FXML private RadioButton rb5;
    @FXML private RadioButton rb6;
    @FXML private RadioButton fd1;
    @FXML private RadioButton fd2;
    @FXML private RadioButton fd3;
    @FXML private Label selectedButtonLabel1;
    @FXML private Label selectedButtonLabel2;
    @FXML private Label error;
    @FXML private ProgressIndicator prog;

    private AddRecipeControllerA arca;
    private String cookingLevel;
    private ArrayList<Text> ingredient;
    private ArrayList<TextField> name;
    private ArrayList<TextField> amount;
    private int i;
    private Stage window;
    private Button Add;
    private ArrayList<IngredientBean> ingredients;
    private File file;
    private final String[] allergies ={"Dried fruit","Fish","Eggs","Milk","Meat","No allergies"};
    private String aP;

    public AddRecipeController() throws IOException, ClassNotFoundException {
        arca=new AddRecipeControllerA();
        ingredient=new ArrayList<>();
        name=new ArrayList<>();
        amount=new ArrayList<>();
        i=IndexTrace.get();
        Add=new Button();
        ingredients=new ArrayList<>();
        IndexTrace.reset();
    }

    public void initialize(){
        upper.setOpacity(1);
        upper.setDisable(false);
        anchor.setOpacity(0);
        anchor.setDisable(true);
        error.setOpacity(0);
    }

    @FXML
    public void handleDishType(ActionEvent actionEvent){
        dtype.setText(((MenuItem)actionEvent.getSource()).getText());
        dtypeMenu.setText(dtype.getText());
        System.out.println(dtype.getText() + " selected");
    }

    @FXML
    public void handleDifficult(ActionEvent actionEvent){
        selectedButton.setText(((RadioButton)actionEvent.getSource()).getText());
        System.out.println(selectedButton.getText() + " selected");
        if(selectedButton.getText().equals("B")){
            cookingLevel="Beginner";
        }else if(selectedButton.getText().equals("I")){
            cookingLevel="Intermediate";
        }else if(selectedButton.getText().equals("A")){
            cookingLevel="Advanced";
        }
    }

    @FXML
    public void goBack() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HomeChef.fxml")));
        Stage window = (Stage) goBackButton.getScene().getWindow();
        window.setScene(new Scene(root, 850, 594));
        GeneralScene.deleteAddTemp();
    }

    @FXML
    public void handleAlimentarPref(ActionEvent actionEvent){
        RadioButton rb = (RadioButton) actionEvent.getSource();
        if(rb.isSelected()){
            selectedButtonLabel1.setText(((RadioButton)actionEvent.getSource()).getText());
            System.out.println(selectedButtonLabel1.getText()+" selected");
        }
        else {
            System.out.println("Deselected");
        }
    }

    @FXML
    public void handleUploadImage(){
        prog.setProgress(0.5);
        if((file=arca.saveIm())==null){
            error.setText("Image not added");
            prog.setProgress(0);
        }else {
            prog.setProgress(1);
        }
    }

    @FXML
    public void commitRecipe() throws IOException, ClassNotFoundException {
        try {
            String title = insertTitle.getText();
            String description = addDescription.getText();

            if (!selectedButtonLabel1.getText().equals("No particular preferences")) {
                aP = selectedButtonLabel1.getText();
            } else {
                aP = "None";
            }
            String cT = cookingTime.getText();

            final RadioButton[] allRB = {rb1, rb2, rb3, rb4, rb5, rb6};
            String preferences = selectedButtonLabel1.getText();
            ArrayList<String> listOfAllergies = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                if (allRB[i].isSelected()) {
                    listOfAllergies.add(allergies[i]);
                    if (allRB[i].getText().equals("No allergies")) {
                        listOfAllergies.clear();
                        listOfAllergies.add("No allergies");
                    }
                }
            }

            IngredientBean ing = new IngredientBean(ingredientName.getText(), ingredientAmount.getText());
            ingredients.add(ing);
            for (int i = 1; i < IndexTrace.get() + 1; i++) {
                ingredients.add(arca.createBean(name.get(i - 1).getText(), amount.get(i - 1).getText()));
            }
            String type = dtype.getText();

            if (title.equals("") || type.equals("") || cookingLevel.equals("") || aP.equals("") || cT.equals("") || listOfAllergies.isEmpty() || file == null || ingredients.isEmpty()) {
                MyException e = new MyException("Field empty");
                ExceptionAdd ex = new ExceptionAdd("Fields empty", e.getCause());
                throw ex;
            }

            int cookingTime = convertIntParameter(cT);

            RecipeBean rb = new RecipeBean(title);
            rb.setType(type);
            rb.setCookingLevel(cookingLevel);
            rb.setDescription(description);
            rb.setAP(aP);
            rb.setCT(cT);
            rb.addAll(listOfAllergies);
            for (int i = 0; i < IndexTrace.get() + 1; i++) {
                rb.addIngredient(Convert.ConvertBeanToEntity(ingredients.get(i)));
            }

            arca.addRecipe(rb);
            if (file != null) {
                arca.sendIm(rb.getName());
            }

            upper.setOpacity(0);
            upper.setDisable(true);
            anchor.setOpacity(1);
            anchor.setDisable(false);

            System.out.println(title + '\n' + type + '\n' + description + '\n' + aP + '\n' + cookingTime + '\n' + ingredientName + '\n' + ingredientAmount.getText());
        }catch(ExceptionAdd e){
            error.setText("Fields empty");
            error.setOpacity(1);
        } catch (MyException e) {
            error.setText("Insert a integer into CT");
            error.setOpacity(1);
        }
    }

    @FXML
    public void handleAddIngredient() throws IOException {
        System.out.println("Clicked");
        i=IndexTrace.get();

        Text ingr=new Text();
        ingr.setText("Ingredient:");
        ingr.setFont(Font.font("Century Gothic", 14));
        ingredient.add(i, ingr);

        TextField n=new TextField();
        n.setPromptText("name");
        n.setPrefSize(124, 27.33333332);
        n.setFont(Font.font("Century Gothic", 14));
        name.add(i, n);

        TextField am=new TextField();
        am.setPromptText("amount");
        am.setPrefSize(124, 27.33333332);
        am.setFont(Font.font("Century Gothic", 14));
        amount.add(i, am);

        ingredient.get(i).setLayoutX(101);
        ingredient.get(i).setLayoutY(464+i*35);

        name.get(i).setLayoutX(184);
        name.get(i).setLayoutY(445+i*35);

        amount.get(i).setLayoutX(318);
        amount.get(i).setLayoutY(445+i*35);

        confirmRecipeButton.setLayoutX(218);
        confirmRecipeButton.setLayoutY(482+i*35);

        Pane original= (Pane) insertTitle.getParent().getParent();
        original.getChildren().add(ingredient.get(i));
        original.getChildren().add(name.get(i));
        original.getChildren().add(amount.get(i));
        original.getChildren().remove(confirmRecipeButton);
        original.getChildren().add(confirmRecipeButton);
        original.setMinHeight(original.getPrefHeight()+i*35);

        root.getChildren().remove(upper);
        upper.setContent(original);
        root.getChildren().add(upper);
        IndexTrace.add();
        window = (Stage) insertTitle.getScene().getWindow();
        window.setScene(GeneralScene.getAddTemp(root));
    }


    //TODO nel controller applicativo
    public int convertIntParameter(String str) throws MyException {
        int value = 0;
        try{
            value = Integer.parseInt(str);
        }
        catch (NumberFormatException ex){
            ex.printStackTrace();
            MyException e=new MyException("Parametro non valido");
            throw e;
        }
        return value;
    }

    @FXML
    public void goBackHome() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HomeChef.fxml")));
        Stage window = (Stage) goBackButton.getScene().getWindow();
        window.setScene(new Scene(root, 850, 594));
    }
}