package com.example.progettoispw;

import java.util.ArrayList;
import java.util.List;

public class SavedControllerA {
    private ArrayList<RecipeBean> recipes;
    private List<Recipe> rcp;
    private FileInterDAO filedao;

    public SavedControllerA(){
        recipes= new ArrayList<>();
        rcp=new ArrayList<>();
    }

    public ArrayList<RecipeBean> saved() throws Exception {
        filedao=FileInterDAO.getInstance();
        rcp=filedao.readSaved();
        recipes.clear();
        if(rcp!=null) {
            for (int i = 0; i < rcp.size(); i++) {
                recipes.add(Convert.convertEntityToBean(rcp.get(i)));
            }
        }else{
            return null;
        }
        return recipes;
    }
}
