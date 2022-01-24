package com.example.progettoispw;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class HistoryController {
    @FXML
    private Button gotohomeButton;
    @FXML
    private AnchorPane anchor;
    @FXML
    private Button clear;

    private HistoryControllerA hca;
    private ArrayList<RecipeBean> rbs;
    private ArrayList<Button> buttons;

    public HistoryController(){
        hca=new HistoryControllerA();
        buttons=new ArrayList<>();
    }

    public void gotoHome() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Second-interface.fxml")));
        Stage window=(Stage) gotohomeButton.getScene().getWindow();
        window.setScene(GeneralScene.getHome(root));
    }

    public void initialize() throws IOException, ClassNotFoundException {
        rbs=hca.getRecipes();
        buttons.clear();
        anchor.getChildren().clear();
        if (rbs != null) {
            for (int i = 0; i < rbs.size(); i++) {
                buttons.add(new Button(rbs.get(i).getName()));
                buttons.get(i).setPrefSize(646, 50);
                buttons.get(i).setLayoutY(i * 65);
                buttons.get(i).getStyleClass().add("button2");
                buttons.get(i).setFont(Font.font("Century gothic",23));


                int finalI = i;
                buttons.get(i).setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        IndexTrace.raset(finalI);
                        IndexTrace.setFive(1);
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
                if(i>6){
                    anchor.setPrefSize(370+(i-5)*50, 630);
                }
                anchor.getChildren().add(buttons.get(i));
            }
        }
    }
    @FXML
    public void clear() throws IOException {
        anchor.getChildren().clear();
        hca.delete();
    }
}
