package com.example.progettoispw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlimentarPreferencesControllerA {
    private AlimentarDAO dao;
    private Login login;
    private FileInterDAO filedao;

    public AlimentarPreferencesControllerA() throws IOException, ClassNotFoundException {
        dao=AlimentarDAO.getInstance();
        filedao=FileInterDAO.getInstance();
        login=filedao.readLog();
    }

    public void setPref(String pref, List<String> all) throws IOException {
        if(pref.equals("I have no particular preferences")){
            pref="None";
        }
        dao.insertAP(login.getUser(), pref, all);
        if(!pref.equalsIgnoreCase("")) {
            login.setAP(pref);
        }
        if(!all.isEmpty()) {
            login.deleteAll();
            for (int i = 0; i < all.size(); i++) {
                login.addAll(all.get(i));
            }
        }
        filedao.writeLog(login);
    }

    public String getPref(){
        return dao.getPref(login.getUser());
    }

    public List<String> getAll(){
        return dao.getAll(login.getUser());
    }

    public void clear(){
        dao.clear(login.getUser());
    }
}