package com.example.progettoispw.RecipeModel;

import java.util.List;

public class Recipe {
    private String name;
    private String dishtype;
    private String preparation;
    private String details;
    private int cookingLevel;
    private int cookingTime;
    private List<Ingredient> ingredients;

    public Recipe(String name, String dishtype, String preparation, String details, int cookingLevel, int cookingTime, List<Ingredient> ingredients) {
        this.name = name;
        this.dishtype = dishtype;
        this.preparation = preparation;
        this.details = details;
        this.cookingLevel = cookingLevel;
        this.cookingTime = cookingTime;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDishType() {
        return dishtype;
    }

    public void setDishType(String type) {
        this.dishtype = type;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getCookingLevel() {
        return cookingLevel;
    }

    public void setCookingLevel(int cookingLevel) {
        this.cookingLevel = cookingLevel;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }


}