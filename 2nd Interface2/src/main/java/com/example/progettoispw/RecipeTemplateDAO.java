package com.example.progettoispw;

import java.io.IOException;
import java.util.ArrayList;

public class RecipeTemplateDAO extends ObserverRecipeDAO{
    private static RecipeTemplateDAO instance=null;
    private ArrayList<Recipe> status;
    private SearchDAO subject;

    public RecipeTemplateDAO(){
        subject=SearchDAO.getInstance();
    }

    public static RecipeTemplateDAO getInstance(){
        if (RecipeTemplateDAO.instance == null)
            RecipeTemplateDAO.instance = new RecipeTemplateDAO();
        return instance;
    }

    public Recipe getRecipe() throws IOException, ClassNotFoundException {
        instance.setSubject(subject);
        subject.attach(instance);
        if(subject.isThereAnythingToNotify()){
            instance.update();
        }
        int i=IndexTrace.tempget();
        subject.detach(instance);
        FileInterDAO filedao=FileInterDAO.getInstance();
        filedao.writeRecipe(status.get(i));
        return status.get(i);
    }

    public void update(){
        if (this.sd == null){
            this.notifySubjectStatus("No subject Attached");
        }else{
            status = this.sd.getState();
            this.notifySubjectStatus("Subject notified");
        }
    }
}
