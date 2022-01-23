package com.example.progettoispw;

import java.io.IOException;
import java.util.ArrayList;

public class HistoryControllerA {
    private FileInterDAO filedao;
    private ArrayList<Recipe> recipes;
    private ArrayList<RecipeBean> rbs;

    public HistoryControllerA(){
        rbs=new ArrayList<>();
        filedao=FileInterDAO.getInstance();
    }

    public ArrayList<RecipeBean> getRecipes() throws IOException, ClassNotFoundException {
        rbs.clear();
        recipes=filedao.readRecipe();
        if(recipes==null){
            rbs=null;
        }else {
            for (int i = 0; i < recipes.size(); i++) {
                rbs.add(Convert.ConvertEntityToBean(recipes.get(i)));
            }
        }
        return rbs;
    }

    public void delete() throws IOException {
        filedao.deleteRecipes();
    }
}
