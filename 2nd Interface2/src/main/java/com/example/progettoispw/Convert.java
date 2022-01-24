package com.example.progettoispw;

import com.example.progettoispw.RecipeModel.Ingredient;

public class Convert {
    private Convert(){}

    public static Recipe convertBeanToEntity(RecipeBean rb){
        if(rb==null){
            return null;
        }
        Recipe recipe=new Recipe(rb.getName());
        if(rb.getAP()!=null) {
            recipe.setAP(rb.getAP());
        }
        if(rb.getCookingLevel()!=null){
            recipe.setCookingLevel(rb.getCookingLevel());
        }
        if(rb.getImage()!=null){
            recipe.setImage(rb.getImage());
        }
        if(rb.getIngredient()!=null){
            recipe.addIngredient(rb.getIngredient());
        }
        if(rb.getChef()!=null){
            recipe.setChef(rb.getChef());
        }
        if(rb.getCT()!=null){
            recipe.setCT(rb.getCT());
        }
        if(rb.getDescription()!=null){
            recipe.setDescription(rb.getDescription());
        }
        if(rb.getType()!=null){
            recipe.setType(rb.getType());
        }
        if(rb.getAll()!=null){
            recipe.addAll(rb.getAll());
        }
        recipe.setImage(rb.getImage());
        return recipe;
    }

    public static RecipeBean convertEntityToBean(Recipe rec){
        if(rec==null){
            return null;
        }
        RecipeBean recb=new RecipeBean(rec.getName());
        if(rec.getAP()!=null) {
            recb.setAP(rec.getAP());
        }
        if(rec.getCookingLevel()!=null){
            recb.setCookingLevel(rec.getCookingLevel());
        }
        if(rec.getImage()!=null){
            recb.setImage(rec.getImage());
        }
        if(rec.getIngredient()!=null){
            recb.addIngredient(rec.getIngredient());
        }
        if(rec.getChef()!=null){
            recb.setChef(rec.getChef());
        }
        if(rec.getCT()!=null){
            recb.setCT(rec.getCT());
        }
        if(rec.getDescription()!=null){
            recb.setDescription(rec.getDescription());
        }
        if(rec.getType()!=null){
            recb.setType(rec.getType());
        }
        if(rec.getAll()!=null){
            recb.addAll(rec.getAll());
        }
        recb.setImage(rec.getImage());
        return recb;
    }

    public static Login convertBeanToEntity(LogBean lb){
        if(lb==null){
            return null;
        }
        Login login = new Login(lb.getUser(), lb.getPass());
        if(lb.getUser()!=null) {
            login.setUser(lb.getUser());
        }
        if(lb.getPass()!=null){
            login.setPass(lb.getPass());
        }
        if(lb.getEmail()!=null){
            login.setEmail(lb.getEmail());
        }
        if(lb.getSpec()!=null){
            login.setSpec(lb.getSpec());
        }
        if(lb.getCL()!=null){
            login.setCL(lb.getCL());
        }
        if(lb.getCheck()>0){
            login.setCheck();
        }
        if(lb.getAP()!=null){
            login.setAP(lb.getAP());
        }
        if(lb.getPremium()){
            login.setPremium();
        }
        if(!lb.getAll().isEmpty()){
            for(int i=0; i<lb.getAll().size(); i++){
                login.addAll(lb.getAll().get(i));
            }
        }

        return login;
    }

    public static LogBean convertEntityToBean(Login login){
        if(login==null){
            return null;
        }
        LogBean logb = new LogBean(login.getUser(), login.getPass());
        if(login.getUser()!=null) {
            logb.setUser(login.getUser());
        }
        if(login.getPass()!=null){
            logb.setPass(login.getPass());
        }
        if(login.getEmail()!=null){
            logb.setEmail(login.getEmail());
        }
        if(login.getSpec()!=null){
            logb.setSpec(login.getSpec());
        }
        if(login.getCL()!=null){
            logb.setCL(login.getCL());
        }
        if(login.getCheck()>0){
            logb.setCheck();
        }
        if(login.getAP()!=null){
            logb.setAP(login.getAP());
        }
        if(login.getPremium()){
            logb.setPremium();
        }
        if(!login.getAll().isEmpty()){
            for(int i=0; i<login.getAll().size(); i++){
                logb.addAll(login.getAll().get(i));
            }
        }

        return logb;
    }

    public static Ingredient convertBeanToEntity(IngredientBean ib){
        if(ib==null){
            return null;
        }
        Ingredient ing = new Ingredient(ib.getName(),ib.getAmount());
        if(ib.getName()!=null) {
            ing.setName(ib.getName());
        }
        if(ib.getAmount()!=null){
            ing.setAmount(ib.getAmount());
        }
        return ing;
    }

    public static IngredientBean convertEntityToBean(Ingredient ing){
        if(ing==null){
            return null;
        }
        IngredientBean ib = new IngredientBean(ing.getName(), ing.getAmount());
        if(ing.getName()!=null) {
            ib.setName(ing.getName());
        }
        if(ing.getAmount()!=null){
            ib.setAmount(ing.getAmount());
        }
        return ib;
    }


}
