package com.example.progettoispw;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MyListControllerA {
    private FileInterDAO filedao;
    private List<RecipeBean> rbs;

    public MyListControllerA(){
        filedao=FileInterDAO.getInstance();
        rbs=new ArrayList<>();
    }

    public List<RecipeBean> getRecipesChef() throws IOException, ClassNotFoundException {
        List<Recipe> recipes=filedao.readChef();
        if(recipes==null){
            return null;
        }
        rbs.clear();
        for (Recipe recipe : recipes) {
            rbs.add(Convert.convertEntityToBean(recipe));
        }
        return rbs;
    }
}
