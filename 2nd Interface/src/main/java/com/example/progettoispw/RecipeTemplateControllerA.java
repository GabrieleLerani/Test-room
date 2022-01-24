package com.example.progettoispw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecipeTemplateControllerA {
    private RecipeBean rb;
    private RecipeTemplateDAO dao;
    private Recipe recipe;
    private FileInterDAO filedao;
    private List<RecipeBean> rbs;
    private List<Recipe> recipes;
    private List<Recipe> r;
    private List<Meal> meals;

    public RecipeTemplateControllerA(){
        filedao= FileInterDAO.getInstance();
        dao=RecipeTemplateDAO.getInstance();
        rbs=new ArrayList<>();
        r=new ArrayList<>();
        meals=new ArrayList<>();
    }

    public RecipeBean getRecipe() throws IOException, ClassNotFoundException {
        recipe=dao.getRecipe();
        rb=Convert.convertEntityToBean(recipe);
        return rb;
    }

    public List<RecipeBean> getArray() throws IOException, ClassNotFoundException {
        rbs.clear();
        recipes=filedao.readRecipe();
        for (Recipe value : recipes) {
            rbs.add(Convert.convertEntityToBean(value));
        }
        return rbs;
    }

    public List<RecipeBean> getSaving() throws IOException, ClassNotFoundException {
        rbs.clear();
        recipes=filedao.readSaved();
        for (Recipe value : recipes) {
            rbs.add(Convert.convertEntityToBean(value));
        }
        return rbs;
    }

    public RecipeBean getMeal(int choose) throws IOException, ClassNotFoundException {
        meals=filedao.readPlan(IndexTrace.dayget()-1);
        if(IndexTrace.timeget()==1 || IndexTrace.timeget()==3){
            Meal meal=meals.get(IndexTrace.timeget()-1);
            recipe=meal.getRecipe();
            rb=Convert.convertEntityToBean(recipe);
        }else if(IndexTrace.timeget()==2){
            MealLunch meal= (MealLunch) meals.get(IndexTrace.timeget()-1);
            recipe=meal.getRecipe(choose);
            rb=Convert.convertEntityToBean(recipe);
        }
        return rb;
    }

    public RecipeBean getRecipeChef() throws IOException, ClassNotFoundException {
        rbs.clear();
        recipes=filedao.readChef();
        rb=Convert.convertEntityToBean(recipes.get(IndexTrace.chefget()));
        return rb;
    }

    public void saveRecipe(RecipeBean rb) throws IOException, ClassNotFoundException {
        Recipe rec=Convert.convertBeanToEntity(rb);
        r=filedao.readSaved();
        if(r!=null) {
            for (Recipe value : r) {
                if (value.getName().equals(rec.getName()) && value.getChef().equals(rec.getChef())) {
                    return;
                }
            }
        }
        filedao.writeSaved(rec);
    }
}
