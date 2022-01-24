package com.example.progettoispw;

public class Factory {
    public Meal createMealBreak(Recipe recipe) {
        return new MealBreak(recipe);
    }

    public Meal createMealLunch(Recipe recipe, Recipe rec, Recipe r) {
        return new MealLunch(recipe, rec, r);
    }

    public Meal createMealDinner(Recipe recipe) {
        return new MealDinner(recipe);
    }
}
