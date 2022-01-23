package com.example.progettoispw;

import com.example.progettoispw.RecipeModel.Ingredient;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable {
    private String chef=null;
    private String name;
    private String type=null;
    private String CookingLevel=null;
    private String description=null;
    private String aP=null;
    private String cT=null;
    private ArrayList<Ingredient> ingredient;
    private byte[] image=null;
    private ArrayList<String> all;

    public Recipe(String name){
        ingredient=new ArrayList<>();
        all=new ArrayList<>();
        this.name=name;
    }

    public Recipe(String name, String chef, byte[] image){
        ingredient=new ArrayList<>();
        this.name=name;
        this.chef=chef;
        this.image=image;
        all=new ArrayList<>();
    }

    public Recipe(String name, String chef, byte[] image, String type, String cl, String desc, String ct, ArrayList<Ingredient> ingr){
        ingredient=new ArrayList<>();
        this.name=name;
        this.chef=chef;
        this.image=image;
        this.type=type;
        this.CookingLevel=cl;
        this.description=desc;
        this.cT=ct;
        this.ingredient=ingr;
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

    public void addIngredient(ArrayList<Ingredient> ingr){ ingredient.addAll(ingr); }

    public ArrayList<Ingredient> getIngredient(){ return ingredient; }

    public void setImage(byte[] image){ this.image=image; }

    public byte[] getImage(){ return image; }

    public void setChef(String chef){ this.chef=chef; }

    public String getChef(){ return chef; }

    public void addAll(String all){ this.all.add(all); }

    public void addAll(ArrayList<String> all){ this.all.addAll(all); }

    public ArrayList<String> getAll(){ return all; }
}
