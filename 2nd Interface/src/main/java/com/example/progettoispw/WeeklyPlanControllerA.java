package com.example.progettoispw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeeklyPlanControllerA {
    private Login login;
    private WeeklyPlanDAO dao;
    private FileInterDAO filedao;
    private Factory fact;
    private ArrayList<Meal> meals;

    public WeeklyPlanControllerA() throws IOException, ClassNotFoundException {
        filedao=FileInterDAO.getInstance();
        login=filedao.readLog();
        dao=WeeklyPlanDAO.getInstance();
        fact=new Factory();
        meals=new ArrayList<>();
    }

    public boolean getPremiumUser(){
        if(login.getPremium()){
            return true;
        }else{
            if(dao.getFromDB(login.getUser())) {
                login.setPremium();
            }
            return login.getPremium();
        }
    }

    public boolean calc(){
        try {
            if (login.getAP() == null || login.getCL() == null) {
                throw new MyException("Parametri non validi");
            }
            List<Recipe> recipesm = dao.getGen("main", login.getCL(), login.getAP());
            List<Recipe> recipess = dao.getGen("side", login.getCL(), login.getAP());
            List<Recipe> recipesd = dao.getGen("dess", login.getCL(), login.getAP());

            List<Recipe> recipesmain=this.checkAll(recipesm);
            List<Recipe> recipesside=this.checkAll(recipess);
            List<Recipe> recipesdess=this.checkAll(recipesd);

    //        if(recipesmain.size()<4 || recipesside.size()<8 || recipesdess.size()<8){
    //            throw new MyException("Ricette insufficienti");
    //        }

            Random rand=new Random();
            int selected;
            int sel;
            int s;

            for(int i=0; i<7; i++){
                meals.clear();
                selected=rand.nextInt(recipesdess.size());
                Meal breakfast=fact.createMealBreak(recipesdess.get(selected));
                selected= rand.nextInt(recipesmain.size());
                sel= rand.nextInt(recipesside.size());
                s=rand.nextInt(recipesdess.size());
                Meal lunch=fact.createMealLunch(recipesmain.get(selected), recipesside.get(sel), recipesdess.get(s));
                selected= rand.nextInt(recipesside.size());
                Meal dinner= fact.createMealDinner(recipesside.get(selected));
                meals.add(breakfast);
                meals.add(lunch);
                meals.add(dinner);
                filedao.writePlan(i, meals);
            }
        }catch (MyException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean getFiles() throws IOException, ClassNotFoundException {
        return filedao.readPlan(0)!=null;
    }

    public List<Recipe> checkAll(List<Recipe> recipe){
        int h=0;
        int s=0;
        // controllo delle allergie
        for(int i=0; i<recipe.size(); i++) {
            for(int j=0; j<login.getAll().size(); j++){
                for(int k=0; k<recipe.get(i-s).getAll().size(); k++){
                    if(login.getAll().get(j).equalsIgnoreCase(recipe.get(i-s).getAll().get(k))){
                        h=1;
                        recipe.remove(i);
                        s++;
                        break;
                    }
                }
                if (h == 1) {
                    break;
                }
            }
            h=0;
        }
        return recipe;
    }
}
