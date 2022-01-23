package com.example.progettoispw;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public abstract class SubjectSearchDAO {
    private List<ObserverRecipeDAO> observers;

    public SubjectSearchDAO() {
        this((ObserverRecipeDAO) null);
    }

    public SubjectSearchDAO(ObserverRecipeDAO obs) {
        this(new Vector<ObserverRecipeDAO>());
        if (obs != null)
            this.observers.add(obs);
    }

    public SubjectSearchDAO(List<ObserverRecipeDAO> list) {
        this.observers = list;
    }

    public void attach(ObserverRecipeDAO obs) {
        this.observers.add(obs);
    }

    public void detach(ObserverRecipeDAO obs) {
        this.observers.remove(obs);
    }

    protected void notifyObservers() {
        List<ObserverRecipeDAO> observersLocal = null;

        if (this.isThereAnythingToNotify())
            observersLocal = new ArrayList<ObserverRecipeDAO>(this.observers);

        if (observersLocal != null) {
            for (ObserverRecipeDAO obs : observersLocal) {
                System.out.println("Updating Observer from the Subject");
                obs.update();
            }
        }
    }

    protected abstract boolean isThereAnythingToNotify();

    protected abstract void doSomething();
}
