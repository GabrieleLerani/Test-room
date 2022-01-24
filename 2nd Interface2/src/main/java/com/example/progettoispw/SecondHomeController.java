package com.example.progettoispw;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class SecondHomeController implements Initializable {

    @FXML
    private VBox recipeBox;
    @FXML
    private VBox savedBox;

    @FXML
    private Button breakfastButton;

    @FXML
    private Button lunchButton;

    @FXML
    private Button dinnerButton;

    @FXML
    private Button newPlanButton;

    @FXML
    private Button go;

    @FXML
    private TextField searchField;

    @FXML
    private Label labelerr;

    @FXML
    private Label nameUser;

    @FXML
    private ChoiceBox<String> filterRecipeBox;

    @FXML
    private ChoiceBox<String> dayBox;

    @FXML
    private ChoiceBox<String> homeActionBox;



    private final String[] filter = {"Recipe","Ingredient","Time","Dish type"};
    private final String[] cookinglevel = {"Beginner","Intermediate","Advanced"};
    private final String[] days = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
    private final String[] action = {"Shopping list","Food preferences","Supermarket","History"};


    private final FileInterDAO filedao;

    private final SavedControllerA sca;
    private final SearchRecipeA sra;
    private final ArrayList<Button> buttons=new ArrayList<>();
    private ArrayList<AnchorPane> aps;
    private LogBean login;
    private ArrayList<RecipeBean> recipes;
    private WeeklyPlanControllerA wpca;

    private CookingLevelControllerA clca;
    private AccountSettingsControllerA asca;



    public SecondHomeController() throws IOException, ClassNotFoundException {
        sra=new SearchRecipeA();
        sca=new SavedControllerA();
        wpca=new WeeklyPlanControllerA();
        aps=new ArrayList<>();

        filedao=FileInterDAO.getInstance(); //Andrebbe istanziato in un controller applicativo e poi restituito al controller grafico
        asca=new AccountSettingsControllerA();
        clca=new CookingLevelControllerA();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeActionBox.getItems().addAll(action);
        filterRecipeBox.getItems().addAll(filter);
        cookinglevelBox.getItems().addAll(cookinglevel);
        dayBox.getItems().addAll(days);

        //INITIALIZE ACCOUNT

        try {
            login=Convert.ConvertEntityToBean(filedao.ReadLog());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        usernameField.setText(login.getUser()+" / "+login.getSpec());
        passwordField.setText(login.getPass());
        emailchange.setText(login.getEmail());

        nameUser.setText("Hi "+login.getUser());

        //INITIALIZE COOKING LEVEL

        String cl = login.getCL();
        switch (cl.toLowerCase()) {
            case "beginner" -> clText.setText("Beginner");
            case "intermediate" -> clText.setText("Intermediate");
            case "advanced" -> clText.setText("Advanced");
        }

        //INITIALIZE SAVED

        this.refreshSaved();
    }

    // CONTROLLER HOME PAGE
    //CONTROLLER HOME PAGE

    public void handleButton() throws IOException {
        String selectedItem = homeActionBox.getSelectionModel().getSelectedItem();
        switch (selectedItem){
            case "History" -> {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("History.fxml")));
                Stage window=(Stage) go.getScene().getWindow();
                window.setScene(GeneralScene.getAdd(root));
            }
            case "Supermarket" -> {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Maps.fxml")));
                Stage window=(Stage) go.getScene().getWindow();
                window.setScene(new Scene(root, 850, 594));
            }
            case "Shopping list" -> {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ShoppingList2.fxml")));
                Stage window=(Stage) go.getScene().getWindow();
                window.setScene(new Scene(root, 850, 594));
            }
            case "Food preferences" -> {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FoodPreferences2.fxml")));
                Stage window=(Stage) go.getScene().getWindow();
                window.setScene(new Scene(root, 850, 594));
            }

            default -> throw new IllegalStateException("Unexpected value: " + selectedItem);
        }
    }



    //CONTROLLER SEARCH

    @FXML
    private Pane paneHome;

    @FXML
    public void addDynamicElement() throws Exception {
        RecipeBean rb;
        ArrayList<Button> bt = new ArrayList<>();
        ArrayList<Label> lb = new ArrayList<>();
        ArrayList<ImageView> iw = new ArrayList<>();
        ArrayList<RecipeBean> rbs = new ArrayList<>();

        String selectedFilter = filterRecipeBox.getSelectionModel().getSelectedItem();

        recipeBox.getChildren().clear();
        try {
            switch (selectedFilter) {
                case "Recipe" -> {
                    String rec = searchField.getText();
                    rb = new RecipeBean(rec);
                    rbs = sra.searchRecipe(rb);
                }
                case "Time" -> {
                    String tm = searchField.getText();
                    rbs = sra.searchRecipeTime(tm);
                }
                case "Ingredient" -> {
                    String ingr = searchField.getText();
                    rbs = sra.searchRecipeIngr(ingr);
                }
                case "Dish type" -> {
                    String type = searchField.getText();
                    rbs = sra.searchRecipeType(type);
                }
            }
            aps = new ArrayList<>();
            for (int i = 0; i < rbs.size(); i++) {
                bt.add(new Button(rbs.get(i).getName()));
                bt.get(i).setMaxSize(150, 30);
                bt.get(i).getStyleClass().add("button1");
                bt.get(i).setFont(Font.font("Century Gothic", 14));
                bt.get(i).setCursor(Cursor.HAND);

                int finalI = i;
                bt.get(i).setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        IndexTrace.tempset(finalI);
                        IndexTrace.setFive(0);
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Recipetemplate.fxml")));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Stage window=(Stage) paneHome.getScene().getWindow();
                        window.setScene(GeneralScene.getRecipe(root));
                    }
                });

                lb.add(new Label("Chef: "+rbs.get(i).getChef()));
                lb.get(i).setMaxSize(100, 20);
                iw.add(new ImageView());
                iw.get(i).setImage(new Image(new ByteArrayInputStream(rbs.get(i).getImage())));
                iw.get(i).setFitHeight(75);
                iw.get(i).setFitWidth(100);
                aps.add(new AnchorPane());
                if((i%2)==0) {
                    aps.get(i).setStyle("-fx-background-color: #FFFFFF");
                }else{
                    aps.get(i).setStyle("-fx-background-color: #09911a");
                }
                aps.get(i).getChildren().addAll(bt.get(i), lb.get(i), iw.get(i));
                aps.get(i).setMaxSize(564,75);
                aps.get(i).setLeftAnchor(bt.get(i), 10.0);
                aps.get(i).setTopAnchor(bt.get(i), 10.0);
                aps.get(i).setLeftAnchor(lb.get(i), 15.0);
                aps.get(i).setTopAnchor(lb.get(i), 45.0);
                aps.get(i).setRightAnchor(iw.get(i), 10.0);
                aps.get(i).setTopAnchor(iw.get(i), 0.0);
                recipeBox.getChildren().add(aps.get(i));
            }
        }catch(MyException e){
            labelerr.setText("Ricetta o immagine relativa non trovata");
        }
    }

    //CONTROLLER ACCOUNT

    @FXML
    private Button confirm1;
    @FXML
    private Button confirm2;
    @FXML
    private Button logoutButton;


    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private TextField emailchange;
    @FXML
    private Label error;

    @FXML
    public void changeU(){
        usernameLabel.setOpacity(1);
        usernameField.setEditable(true);
        usernameField.setText("");
    }

    @FXML
    public void changePass(){
        passwordLabel.setOpacity(1);
        passwordField.setEditable(true);
        passwordField.setText("");
    }

    @FXML
    public void appear_confirm_U_button(){
        System.out.println("click");
        confirm1.setDisable(false);
    }

    @FXML
    public void appear_confirm_Pass_button(){
        System.out.println("click");
        confirm2.setDisable(false);
    }

    @FXML
    public void setConfirm1() throws IOException, ClassNotFoundException {
        String username=usernameField.getText();
        asca.confirmUser(username);
        login=Convert.ConvertEntityToBean(filedao.ReadLog());
        usernameField.setText(login.getUser() + " / " + login.getSpec());
    }

    @FXML
    public void setConfirm2() throws Exception {
        String pass=passwordField.getText();
        if(asca.confirmPass(pass)==1){
            error.setText("Password non adeguata");
        }else {
            login = Convert.ConvertEntityToBean(filedao.ReadLog());
            passwordField.setText(login.getPass());
        }
    }
    @FXML
    public void logout() throws IOException, ClassNotFoundException {
        asca.deselect();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
        Stage window = (Stage) logoutButton.getScene().getWindow();
        window.setScene(new Scene(root, 850, 594));
    }

    //CONTROLLER COOKING LEVEL

    @FXML private Text clText;
    @FXML private ChoiceBox<String> cookinglevelBox;

    @FXML
    public void confirmCL() throws IOException, ClassNotFoundException {
        String level = cookinglevelBox.getSelectionModel().getSelectedItem();
        //setta il cooking level dell'utente
        clca.setCL(level);
        clText.setText(level);
    }

    @FXML
    public void refreshSaved(){
        try {
            recipes = sca.saved();
            aps.clear();
            buttons.clear();
            savedBox.getChildren().clear();
            if (recipes != null && recipes.size() > 0) {
                for (int i = 0; i < recipes.size(); i++) {
                    buttons.add(new Button(recipes.get(i).getName()));
                    buttons.get(i).setPrefSize(556, 50);
                    buttons.get(i).setFont(Font.font("Centhury Gothic", 25));
                    buttons.get(i).getStyleClass().add("button2");

                    int finalI = i;
                    buttons.get(i).setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            IndexTrace.preset(finalI);
                            IndexTrace.setFive(2);
                            Parent root = null;
                            try {
                                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Recipetemplate.fxml")));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Stage window=(Stage) paneHome.getScene().getWindow();
                            window.setScene(GeneralScene.getRecipe(root));
                        }
                    });

                    aps.add(new AnchorPane());
                    aps.get(i).getChildren().addAll(buttons.get(i));
                    aps.get(i).setLeftAnchor(buttons.get(i), 10.0);
                    aps.get(i).setTopAnchor(buttons.get(i), 10.0);

                    savedBox.getChildren().add(buttons.get(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //CONTROLLER WEEKLY PLAN
    @FXML
    private AnchorPane paneCalc;
    @FXML
    private AnchorPane panePlan;
    @FXML
    private AnchorPane panePremiumUser;
    @FXML
    private Button premiumButton;




    public void initialize() throws IOException, ClassNotFoundException {
        if(wpca.getPremiumUser() && !wpca.getFiles()){
            enablePane(paneCalc,true);
            enablePane(panePlan,false);
            enablePane(panePremiumUser,false);
        }
        else if(wpca.getPremiumUser() && wpca.getFiles()){
            enablePane(paneCalc,false);
            enablePane(panePlan,true);
            enablePane(panePremiumUser,false);
        }
        else{
            enablePane(paneCalc,false);
            enablePane(panePlan,false);
            enablePane(panePremiumUser,true);
        }
    }

    @FXML
    public void handlePlan(ActionEvent e) throws IOException {

        IndexTrace.setFive(3);
        Object source=e.getSource();
        String day = dayBox.getSelectionModel().getSelectedItem();

        switch(day){
            case "Monday" ->{
                IndexTrace.dayset(1);
                if(source.equals(breakfastButton)){
                    IndexTrace.tempset(1);
                }
                if(source.equals(lunchButton)){
                    IndexTrace.tempset(2);
                }

                if(source.equals(dinnerButton)){
                    IndexTrace.tempset(3);
                }

            }
            case "Tuesday" ->{
                IndexTrace.dayset(2);
                if(source.equals(breakfastButton)){
                    IndexTrace.tempset(1);
                }
                if(source.equals(lunchButton)){
                    IndexTrace.tempset(2);
                }
                if(source.equals(dinnerButton)){
                    IndexTrace.tempset(3);
                }
            }
            case "Wednesday" ->{
                IndexTrace.dayset(3);
                if(source.equals(breakfastButton)){
                    IndexTrace.tempset(1);
                }
                if(source.equals(lunchButton)){
                    IndexTrace.tempset(2);
                }

                if(source.equals(dinnerButton)){
                    IndexTrace.tempset(3);
                }
            }
            case "Thursday" ->{
                IndexTrace.dayset(4);
                if(source.equals(breakfastButton)){
                    IndexTrace.tempset(1);
                }
                if(source.equals(lunchButton)){
                    IndexTrace.tempset(2);
                }

                if(source.equals(dinnerButton)){
                    IndexTrace.tempset(3);
                }
            }
            case "Friday" ->{
                IndexTrace.dayset(5);
                if(source.equals(breakfastButton)){
                    IndexTrace.tempset(1);
                }
                if(source.equals(lunchButton)){
                    IndexTrace.tempset(2);
                }
                if(source.equals(dinnerButton)){
                    IndexTrace.tempset(3);
                }
            }
            case "Saturday" ->{
                IndexTrace.dayset(6);
                if(source.equals(breakfastButton)){
                    IndexTrace.tempset(1);
                }
                if(source.equals(lunchButton)){
                    IndexTrace.tempset(2);
                }

                if(source.equals(dinnerButton)){
                    IndexTrace.tempset(3);
                }
            }
            case "Sunday" ->{
                IndexTrace.dayset(7);
                if(source.equals(breakfastButton)){
                    IndexTrace.tempset(1);
                }
                if(source.equals(lunchButton)){
                    IndexTrace.tempset(2);
                }
                if(source.equals(dinnerButton)){
                    IndexTrace.tempset(3);
                }
            }
        }

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Recipetemplate.fxml")));
        Stage window=(Stage) premiumButton.getScene().getWindow();
        window.setScene(GeneralScene.getRecipe(root));
    }

    @FXML
    public void gotoPremiumPayment() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Payment.fxml")));
        Stage window=(Stage) premiumButton.getScene().getWindow();
        window.setScene(new Scene(root, 850, 594));
    }

    @FXML
    private Parent general;

    @FXML
    public void calculate(){
        if(wpca.calc()) {
            enablePane(panePremiumUser, false);
            enablePane(paneCalc, false);
            enablePane(panePlan, true);

            general=paneHome.getParent();
            GeneralScene.refreshHome(general);

        }

    }



    // UTILITY
    private void enablePane(AnchorPane pane,boolean able) {
        if (!able) {
            pane.toBack();
            pane.setOpacity(0);
            pane.setDisable(true);
        }
        else{
            pane.toFront();
            pane.setOpacity(1);
            pane.setDisable(false);
        }
    }

}


