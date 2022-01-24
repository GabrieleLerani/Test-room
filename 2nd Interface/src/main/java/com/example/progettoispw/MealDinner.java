package com.example.progettoispw;

import java.io.Serializable;

public class MealDinner extends Meal implements Serializable {
    public MealDinner(Recipe side){
        this.side=side;
    }

    public Recipe getRecipe(){
        return side;
    }
}
