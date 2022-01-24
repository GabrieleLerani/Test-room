package com.example.progettoispw;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyListController {
    @FXML
    private Button goBackButton;
    @FXML
    private VBox anchor;

    private ArrayList<AnchorPane> aps;
    private MyListControllerA mlca;
    private List<Button> buttons;

    public MyListController(){
        mlca=new MyListControllerA();
        buttons=new ArrayList<>();
        aps=new ArrayList<>();
    }

    public void goBack() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HomeChef.fxml")));
        Stage window=(Stage) goBackButton.getScene().getWindow();
        window.setScene(new Scene(root, 850, 594));
    }

    public void initialize() throws IOException, ClassNotFoundException {
        List<RecipeBean> rbs=mlca.getRecipesChef();
        aps.clear();
        buttons.clear();
        anchor.getChildren().clear();
        anchor.setSpacing(20);
        if(rbs!=null && rbs.size()>0){
            for(int i = 0; i< rbs.size(); i++) {
                buttons.add(new Button(rbs.get(i).getName()));
                buttons.get(i).setPrefSize(300, 50);
                buttons.get(i).setFont(Font.font("Centhury Gothic", 25));
                buttons.get(i).getStyleClass().add("button2");
                buttons.get(i).setCursor(Cursor.HAND);


                int finalI = i;
                buttons.get(i).setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        IndexTrace.chefset(finalI);
                        IndexTrace.setFive(4);
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Recipetemplate.fxml")));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Stage window=(Stage) anchor.getScene().getWindow();
                        window.setScene(GeneralScene.getRecipe(root));
                    }
                });

                aps.add(new AnchorPane());
                aps.get(i).getChildren().addAll(buttons.get(i));
                aps.get(i).setLeftAnchor(buttons.get(i), 10.0);
                aps.get(i).setTopAnchor(buttons.get(i), 10.0);

                anchor.getChildren().add(buttons.get(i));
            }
        }
    }
}
