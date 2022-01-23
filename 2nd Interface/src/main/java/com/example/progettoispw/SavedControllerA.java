package com.example.progettoispw;

import java.util.ArrayList;

public class SavedControllerA {
    private ArrayList<RecipeBean> recipes;
    private ArrayList<Recipe> rcp;
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
                recipes.add(Convert.ConvertEntityToBean(rcp.get(i)));
            }
        }else{
            return null;
        }
        return recipes;
    }
}
