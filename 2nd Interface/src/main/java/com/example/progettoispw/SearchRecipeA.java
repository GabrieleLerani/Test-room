package com.example.progettoispw;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SearchRecipeA {
    private String recipe;
    private SearchDAO dao;
    private ArrayList<Recipe> recipes;
    private ArrayList<RecipeBean> rbs;
    private Login login;
    private FileInterDAO filedao;

    public SearchRecipeA() throws IOException, ClassNotFoundException {
        dao=SearchDAO.getInstance();
        filedao=FileInterDAO.getInstance();
        login=filedao.ReadLog();
    }

    public ArrayList<RecipeBean> searchRecipe(RecipeBean rb) throws Exception {
        rbs=new ArrayList<>();
        SearchRecipeA sra=new SearchRecipeA();
        recipe=rb.getName();
        recipes=dao.searchRec(recipe, login.getCL(), login.getAP(), login.getUser());

        rbs=sra.checkAlle(recipes);
        return rbs;
    }

    public ArrayList<RecipeBean> searchRecipeTime(String time) throws Exception {
        rbs=new ArrayList<>();
        SearchRecipeA sra=new SearchRecipeA();
        recipes=dao.searchRecipe(time, login.getCL(), login.getAP(), login.getUser());

        rbs=sra.checkAlle(recipes);
        return rbs;
    }

    public ArrayList<RecipeBean> searchRecipeIngr(String ingr) throws Exception {
        rbs=new ArrayList<>();
        SearchRecipeA sra=new SearchRecipeA();
        recipes=dao.searchRecipeIngr(ingr, login.getCL(), login.getAP(), login.getUser());

        rbs=sra.checkAlle(recipes);
        return rbs;
    }

    public ArrayList<RecipeBean> searchRecipeType(String type) throws Exception {
        rbs=new ArrayList<>();
        SearchRecipeA sra=new SearchRecipeA();
        recipes=dao.searchRecipeType(type, login.getCL(), login.getAP(), login.getUser());

        rbs=sra.checkAlle(recipes);
        return rbs;
    }

    public ArrayList<RecipeBean> checkAlle(ArrayList<Recipe> recipe){
        ArrayList<RecipeBean> rb=new ArrayList<>();

        // controllo delle allergie
        loop:   for(int i=0; i<recipe.size(); i++) {
            for(int j=0; j<login.getAll().size(); j++){
                for(int k=0; k<recipe.get(i).getAll().size(); k++){
                    if(login.getAll().get(j).equalsIgnoreCase(recipe.get(i).getAll().get(k))){
                        continue loop;
                    }
                }
            }
            rb.add(new RecipeBean(recipe.get(i).getName(), recipe.get(i).getChef(), recipe.get(i).getImage()));
        }
        return rb;
    }
}
