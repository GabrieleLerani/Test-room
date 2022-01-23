package com.example.progettoispw;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RecipeTemplateControllerA {
    private RecipeBean rb;
    private RecipeTemplateDAO dao;
    private Recipe recipe;
    private FileInterDAO filedao;
    private ArrayList<RecipeBean> rbs;
    private ArrayList<Recipe> recipes;
    private ArrayList<Recipe> r;
    private Recipe rec;
    private ArrayList<Meal> meals;

    public RecipeTemplateControllerA(){
        filedao= FileInterDAO.getInstance();
        dao=RecipeTemplateDAO.getInstance();
        rbs=new ArrayList<>();
        r=new ArrayList<>();
        meals=new ArrayList<>();
    }

    public RecipeBean getRecipe() throws IOException, ClassNotFoundException {
        recipe=dao.getRecipe();
        rb=Convert.ConvertEntityToBean(recipe);
        return rb;
    }

    public ArrayList<RecipeBean> getArray() throws IOException, ClassNotFoundException {
        rbs.clear();
        recipes=filedao.readRecipe();
        for(int i=0; i<recipes.size(); i++){
            rbs.add(Convert.ConvertEntityToBean(recipes.get(i)));
        }
        return rbs;
    }

    public ArrayList<RecipeBean> getSaving() throws IOException, ClassNotFoundException {
        rbs.clear();
        recipes=filedao.readSaved();
        for(int i=0; i<recipes.size(); i++){
            rbs.add(Convert.ConvertEntityToBean(recipes.get(i)));
        }
        return rbs;
    }

    public RecipeBean getMeal(int choose) throws IOException, ClassNotFoundException {
        meals=filedao.readPlan(IndexTrace.dayget()-1);
        if(IndexTrace.timeget()==1 || IndexTrace.timeget()==3){
            Meal meal=meals.get(IndexTrace.timeget()-1);
            recipe=meal.getRecipe();
            rb=Convert.ConvertEntityToBean(recipe);
        }else if(IndexTrace.timeget()==2){
            MealLunch meal= (MealLunch) meals.get(IndexTrace.timeget()-1);
            recipe=meal.getRecipe(choose);
            rb=Convert.ConvertEntityToBean(recipe);
        }
        return rb;
    }

    public RecipeBean getRecipeChef() throws IOException, ClassNotFoundException {
        rbs.clear();
        recipes=filedao.readChef();
        rb=Convert.ConvertEntityToBean(recipes.get(IndexTrace.chefget()));
        return rb;
    }

    public void saveRecipe(RecipeBean rb) throws IOException, ClassNotFoundException {
        rec=Convert.ConvertBeanToEntity(rb);
        r=filedao.readSaved();
        if(r!=null) {
            for (int i = 0; i < r.size(); i++) {
                if (r.get(i).getName().equals(rec.getName()) && r.get(i).getChef().equals(rec.getChef())) {
                    return;
                }
            }
        }
        filedao.writeSaved(rec);
    }
}
