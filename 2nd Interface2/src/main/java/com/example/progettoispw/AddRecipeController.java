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
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    @FXML private AnchorPane anch;
    @FXML private ScrollPane upper;
    @FXML private AnchorPane anchor;
    @FXML private RadioButton r1;
    @FXML private RadioButton r2;
    @FXML private RadioButton r3;
    @FXML private RadioButton r4;
    @FXML private RadioButton r5;
    @FXML private RadioButton r6;
    @FXML private Label selectedButtonLabel1;
    @FXML private Label error;
    @FXML private ProgressIndicator prog;

    private String sel="selected";
    private String no="No allergies";
    private AddRecipeControllerA arca;
    private String cookingLevel;
    private ArrayList<Text> ingredient;
    private ArrayList<TextField> name;
    private ArrayList<TextField> amount;
    private int i;
    private ArrayList<IngredientBean> ingredients;
    private File file;
    private static Logger logger=Logger.getLogger(AddRecipeController.class.getName());
    private final String[] allergies ={"Dried fruit","Fish","Eggs","Milk","Meat",no};

    public AddRecipeController() throws IOException, ClassNotFoundException {
        arca=new AddRecipeControllerA();
        ingredient=new ArrayList<>();
        name=new ArrayList<>();
        amount=new ArrayList<>();
        i=IndexTrace.get();
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
        logger.log(Level.INFO, dtype.getText() + " "+ sel);
    }

    @FXML
    public void handleDifficult(ActionEvent actionEvent){
        selectedButton.setText(((RadioButton)actionEvent.getSource()).getText());
        logger.log(Level.INFO, selectedButton.getText() + " "+sel);
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
            logger.log(Level.INFO, selectedButtonLabel1.getText()+" "+sel);
        }
        else {
            logger.log(Level.INFO, "Deselected");
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
        String aP;
        try {
            String title = insertTitle.getText();
            String description = addDescription.getText();

            if (!selectedButtonLabel1.getText().equals("No particular preferences")) {
                aP = selectedButtonLabel1.getText();
            } else {
                aP = "None";
            }
            String cT = cookingTime.getText();

            final RadioButton[] allRB = {r1, r2, r3, r4, r5, r6};
            ArrayList<String> listOfAllergies = new ArrayList<>();
            for (int j = 0; j < 6; j++) {
                if (allRB[j].isSelected()) {
                    listOfAllergies.add(allergies[j]);
                    if (allRB[j].getText().equals(no)) {
                        listOfAllergies.clear();
                        listOfAllergies.add(no);
                    }
                }
            }

            IngredientBean ing = new IngredientBean(ingredientName.getText(), ingredientAmount.getText());
            ingredients.add(ing);
            for (int j = 1; j < IndexTrace.get() + 1; j++) {
                ingredients.add(arca.createBean(name.get(j - 1).getText(), amount.get(j - 1).getText()));
            }
            String type = dtype.getText();

            if (title.equals("") || type.equals("") || cookingLevel.equals("") || aP.equals("") || cT.equals("") || listOfAllergies.isEmpty() || file == null || ingredients.isEmpty()) {
                MyException e = new MyException("Field empty");
                throw new ExceptionAdd("Fields empty", e.getCause());
            }

            int cookT = convertIntParameter(cT);

            RecipeBean rb = new RecipeBean(title);
            rb.setType(type);
            rb.setCookingLevel(cookingLevel);
            rb.setDescription(description);
            rb.setAP(aP);
            rb.setCT(cT);
            rb.addAll(listOfAllergies);
            rb.setImage(Files.readAllBytes(file.toPath()));
            for (int j = 0; j < IndexTrace.get() + 1; j++) {
                rb.addIngredient(Convert.convertBeanToEntity(ingredients.get(j)));
            }

            arca.addRecipe(rb);
            if (file != null) {
                arca.sendIm(rb.getName());
            }

            upper.setOpacity(0);
            upper.setDisable(true);
            anchor.setOpacity(1);
            anchor.setDisable(false);

            logger.log(Level.INFO, title + '\n' + type + '\n' + description + '\n' + aP + '\n' + cookT + '\n' + ingredientName + '\n' + ingredientAmount.getText());
        }catch(ExceptionAdd e){
            error.setText("Fields empty");
            error.setOpacity(1);
        } catch (MyException e) {
            error.setText("Insert a integer into CT");
            error.setOpacity(1);
        }
    }

    @FXML
    public void handleAddIngredient() {
        String cent="Century Gothic";
        i=IndexTrace.get();

        Text ingr=new Text();
        ingr.setText("Ingredient:");
        ingr.setFont(Font.font(cent, 14));
        ingredient.add(i, ingr);

        TextField n=new TextField();
        n.setPromptText("name");
        n.setPrefSize(124, 27.33333332);
        n.setFont(Font.font(cent, 14));
        name.add(i, n);

        TextField am=new TextField();
        am.setPromptText("amount");
        am.setPrefSize(124, 27.33333332);
        am.setFont(Font.font(cent, 14));
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

        anch.getChildren().remove(upper);
        upper.setContent(original);
        anch.getChildren().add(upper);
        IndexTrace.add();
        Stage window = (Stage) insertTitle.getScene().getWindow();
        window.setScene(GeneralScene.getAddTemp(anch));
    }



    public int convertIntParameter(String str) throws MyException {
        int value = 0;
        try{
            value = Integer.parseInt(str);
        }
        catch (NumberFormatException ex){
            ex.printStackTrace();
            throw new MyException("Parametro non valido");
        }
        return value;
    }
}