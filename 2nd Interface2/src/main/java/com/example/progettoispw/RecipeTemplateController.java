package com.example.progettoispw;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class RecipeTemplateController {

    @FXML
    private Button goBackButton;
    @FXML
    private Label difficult;
    @FXML
    private Label cookingTime;
    @FXML
    private Label dishType;
    @FXML
    private VBox ingredientBox;
    @FXML
    private Text descriptionArea;
    @FXML
    private Label recipeTitle;
    @FXML
    private Button likeButton;
    @FXML
    private Button prevRec;
    @FXML
    private Button nextRec;
    @FXML
    private ImageView iw;

    private BackControllerA bca;
    private int i=0;
    private RecipeTemplateControllerA rtca;
    private RecipeBean rb;

    public RecipeTemplateController(){
        bca=new BackControllerA();
        rtca=new RecipeTemplateControllerA();
    }

    public void initialize() throws IOException, ClassNotFoundException {
        List<RecipeBean> rbs;
        if(IndexTrace.getFive()==0) {
            rb = rtca.getRecipe();
        }else if(IndexTrace.getFive()==1){
            rbs = rtca.getArray();
            rb = rbs.get(IndexTrace.raget());
        }else if(IndexTrace.getFive()==2){
            rbs=rtca.getSaving();
            rb=rbs.get(IndexTrace.preget());
        }else if(IndexTrace.getFive()==3){
            rb=rtca.getMeal(1);
            i=1;
        }else if(IndexTrace.getFive()==4){
            rb=rtca.getRecipeChef();
        }
        recipeTitle.setText(rb.getName());
        descriptionArea.setText(rb.getDescription());
        difficult.setText(rb.getCookingLevel());
        cookingTime.setText(rb.getCT());
        dishType.setText(rb.getType());
        iw.setImage(new Image(new ByteArrayInputStream(rb.getImage())));
        for(int j=0; j<rb.getIngredient().size(); j++) {
            ingredientBox.getChildren().add(new Text(rb.getIngredient().get(j).getName()+" "+rb.getIngredient().get(j).getAmount()));
        }

        if(IndexTrace.timeget()==2){
            prevRec.setOpacity(1);
            nextRec.setOpacity(1);
            prevRec.setDisable(false);
            nextRec.setDisable(false);
        }
    }

    public void goBack() throws IOException, ClassNotFoundException {
        if(IndexTrace.timeget()!=0){
            IndexTrace.timereset();
        }
        if(bca.getSpecialization().equalsIgnoreCase("User") || bca.getSpecialization().equalsIgnoreCase("Premium")) {
            Stage window = (Stage) goBackButton.getScene().getWindow();
            window.setScene(GeneralScene.getHome());
        }else{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HomeChef.fxml")));
            Stage window = (Stage) goBackButton.getScene().getWindow();
            window.setScene(new Scene(root, 850, 594));
        }
    }

    @FXML
    public void setLike() throws IOException, ClassNotFoundException {
        rtca.saveRecipe(rb);
    }

    @FXML
    public void prev() throws IOException, ClassNotFoundException {
        if(i==1){
            rb=rtca.getMeal(3);
            i=3;
        }else if(i==2){
            rb=rtca.getMeal(1);
            i=1;
        }else if(i==3) {
            rb = rtca.getMeal(2);
            i = 2;
        }
        this.refresh(rb);
    }

    @FXML
    public void next() throws IOException, ClassNotFoundException {
        if(i==1){
            rb=rtca.getMeal(2);
            i=2;
        }else if(i==2){
            rb=rtca.getMeal(3);
            i=3;
        }else if(i==3) {
            rb = rtca.getMeal(1);
            i = 1;
        }
        this.refresh(rb);
    }

    public void refresh(RecipeBean rb){
        ingredientBox.getChildren().clear();
        recipeTitle.setText(rb.getName());
        descriptionArea.setText(rb.getDescription());
        difficult.setText(rb.getCookingLevel());
        cookingTime.setText(rb.getCT());
        dishType.setText(rb.getType());
        iw.setImage(new Image(new ByteArrayInputStream(rb.getImage())));
        for(int j=0; j<rb.getIngredient().size(); j++) {
            ingredientBox.getChildren().add(new Text(rb.getIngredient().get(j).getName()+" "+rb.getIngredient().get(j).getAmount()));
        }
    }
}
