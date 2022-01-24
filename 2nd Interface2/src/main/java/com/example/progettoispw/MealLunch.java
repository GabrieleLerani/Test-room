package com.example.progettoispw;

import java.io.Serial;
import java.io.Serializable;

public class MealLunch extends Meal implements Serializable {
    public MealLunch(Recipe main, Recipe side, Recipe dess){
        this.main=main;
        this.side=side;
        this.dess=dess;
    }

    public Recipe getRecipe(){
        return null;
    }

    public Recipe getRecipe(int choose){
        return switch (choose) {
            case 1 -> main;
            case 2 -> side;
            case 3 -> dess;
            default -> null;
        };
    }
}
