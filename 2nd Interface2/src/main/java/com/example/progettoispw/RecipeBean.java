package com.example.progettoispw;

import com.example.progettoispw.recipeModel.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class RecipeBean {
    private String chef=null;
    private String name;
    private String type=null;
    private String CookingLevel=null;
    private String description=null;
    private String aP=null;
    private String cT=null;
    private List<Ingredient> ingredient;
    private byte[] image=null;
    private ArrayList<String> all;

    public RecipeBean(String name){
        ingredient=new ArrayList<>();
        this.name=name;
        all=new ArrayList<>();
    }

    public RecipeBean(String name, String chef, byte[] image){
        ingredient=new ArrayList<>();
        this.name=name;
        this.chef=chef;
        this.image=image;
        all=new ArrayList<>();
    }

    public void setName(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }

    public void setType(String type){
        this.type=type;
    }

    public String getType(){
        return type;
    }

    public void setCookingLevel(String cl){
        this.CookingLevel=cl;
    }

    public String getCookingLevel(){
        return CookingLevel;
    }

    public void setDescription(String ds){
        this.description=ds;
    }

    public String getDescription(){
        return description;
    }

    public void setAP(String aP){
        this.aP=aP;
    }

    public String getAP(){
        return aP;
    }

    public void setCT(String cT){
        this.cT=cT;
    }

    public String getCT(){
        return cT;
    }

    public void addIngredient(Ingredient ingr){ ingredient.add(ingr); }

    public void addIngredient(List<Ingredient> ingr){ ingredient.addAll(ingr); }

    public List<Ingredient> getIngredient(){ return ingredient; }

    public void setImage(byte[] image){ this.image=image; }

    public byte[] getImage(){ return image; }

    public void setChef(String chef){ this.chef=chef; }

    public String getChef(){ return chef; }

    public void addAll(String all){ this.all.add(all); }

    public void addAll(List<String> all){ this.all.addAll(all); }

    public List<String> getAll(){ return all; }
}
