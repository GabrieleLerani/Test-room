package com.example.progettoispw;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class GeneralScene {
    private static Scene sceneHome;
    private static Scene sceneHomeChef;
    private static Scene sceneAddRecipe;
    private static Scene sceneAddRecipeTemp;
    private static Scene sceneRecipe;

    private GeneralScene(){}

    public static Scene getAdd(Parent root){
        if(sceneAddRecipe==null){
            sceneAddRecipe=new Scene(root, 850, 594);
        }else{
            sceneAddRecipe.setRoot(root);
        }
        return sceneAddRecipe;
    }

    public static Scene getAddTemp(Parent root){
        if(sceneAddRecipe==null && sceneAddRecipeTemp==null){
            sceneAddRecipeTemp=new Scene(root, 850, 594);
        }else if(sceneAddRecipe!=null && sceneAddRecipeTemp==null){
            sceneAddRecipeTemp=sceneAddRecipe;
            sceneAddRecipeTemp.setRoot(root);
        }else if(sceneAddRecipe != null){
            sceneAddRecipeTemp.setRoot(root);
        }
        return sceneAddRecipeTemp;
    }

    public static void deleteAddTemp(){
        sceneAddRecipeTemp=null;
    }

    public static Scene getHome(Parent root){
        if(sceneHome==null){
            sceneHome=new Scene(root, 850, 594);
        }else{
            sceneHome.setRoot(root);
        }
        return sceneHome;
    }

    public static void refreshHome(Parent root){
        sceneHome.setRoot(root);
    }

    public static Scene getHome(){
        return sceneHome;
    }

    public static Scene getRecipe(Parent root) {
        if (sceneRecipe == null) {
            sceneRecipe = new Scene(root, 850, 594);
        } else {
            sceneRecipe.setRoot(root);
        }
        return sceneRecipe;
    }

    public static Scene getHomeChef(Parent root){
        if(sceneHomeChef==null){
            sceneHomeChef=new Scene(root, 850, 594);
        }else{
            sceneHomeChef.setRoot(root);
        }
        return sceneHomeChef;
    }
}
