package com.example.progettoispw;

import java.io.Serializable;

public abstract class Meal implements Serializable {
    protected Recipe main;
    protected Recipe side;
    protected Recipe dess;

    public Meal(){}

    public abstract Recipe getRecipe();
}
