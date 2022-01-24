package com.example.progettoispw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class WeeklyPlanControllerA {
    private Login login;
    private WeeklyPlanDAO dao;
    private FileInterDAO filedao;
    private ArrayList<Recipe> recipesmain;
    private ArrayList<Recipe> recipesside;
    private ArrayList<Recipe> recipesdess;
    private ArrayList<Recipe> recipesm;
    private ArrayList<Recipe> recipess;
    private ArrayList<Recipe> recipesd;
    private Factory fact;
    private ArrayList<Meal> meals;

    public WeeklyPlanControllerA() throws IOException, ClassNotFoundException {
        filedao=FileInterDAO.getInstance();
        login=filedao.ReadLog();
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
            System.out.println(login.getAP());
            System.out.println(login.getCL());
            if (login.getAP() == null || login.getCL() == null) {
                MyException e = new MyException("Parametri non validi");
                throw e;
            }
            recipesm = dao.getGen("main", login.getCL(), login.getUser(), login.getAP());
            recipess = dao.getGen("side", login.getCL(), login.getUser(), login.getAP());
            recipesd = dao.getGen("dess", login.getCL(), login.getUser(), login.getAP());

            recipesmain=this.checkAll(recipesm);
            recipesside=this.checkAll(recipess);
            recipesdess=this.checkAll(recipesd);

            System.out.println(recipesmain.size());
            System.out.println(recipesside.size());
            System.out.println(recipesdess.size());
    //        if(recipesmain.size()<4 || recipesside.size()<8 || recipesdess.size()<8){
    //            MyException e = new MyException("Ricette insufficienti");
    //            throw e;
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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean getFiles() throws IOException, ClassNotFoundException {
        if(filedao.readPlan(0)==null){
            return false;
        }else{
            return true;
        }
    }

    public ArrayList<Recipe> checkAll(ArrayList<Recipe> recipe){
        // controllo delle allergie
loop:   for(int i=0; i<recipe.size(); i++) {
            for(int j=0; j<login.getAll().size(); j++){
                for(int k=0; k<recipe.get(i).getAll().size(); k++){
                    if(login.getAll().get(j).equalsIgnoreCase(recipe.get(i).getAll().get(k))){
                        recipe.remove(i);
                        i=i-1;
                        continue loop;
                    }
                }
            }
        }
        return recipe;
    }
}
