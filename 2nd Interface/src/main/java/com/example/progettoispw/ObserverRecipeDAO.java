package com.example.progettoispw;

public abstract class ObserverRecipeDAO {
    protected SearchDAO sd;

    protected void notifySubjectStatus(String message){
        System.out.println("---> " + message);
    }

    public void setSubject(SearchDAO subject) {
        this.sd = subject;
    }

    public abstract void update();
}
