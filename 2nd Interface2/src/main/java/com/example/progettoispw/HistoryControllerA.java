package com.example.progettoispw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HistoryControllerA {
    private FileInterDAO filedao;
    private ArrayList<RecipeBean> rbs;

    public HistoryControllerA(){
        rbs=new ArrayList<>();
        filedao=FileInterDAO.getInstance();
    }

    public List<RecipeBean> getRecipes() throws IOException, ClassNotFoundException {
        rbs.clear();
        List<Recipe> recipes=filedao.readRecipe();
        if(recipes==null){
            rbs=null;
        }else {
            for (int i = 0; i < recipes.size(); i++) {
                rbs.add(Convert.convertEntityToBean(recipes.get(i)));
            }
        }
        return rbs;
    }

    public void delete() throws IOException {
        filedao.deleteRecipes();
    }
}
