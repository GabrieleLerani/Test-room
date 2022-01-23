package com.example.progettoispw;

public class IngredientBean {
    private String name;
    private String amount;

    public IngredientBean(String name, String amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName(){
        return name;
    }

    public String getAmount() {return amount;}

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
