package com.example.progettoispw;

import java.io.IOException;

public class AccountSettingsControllerA {
    private Login login;
    private AccountSettingsDAO dao;
    private FileInterDAO filedao;

    public AccountSettingsControllerA(){
        filedao= FileInterDAO.getInstance();
        dao=AccountSettingsDAO.getInstance();
    }

    public void confirmUser(String username) throws IOException, ClassNotFoundException {
        login=filedao.ReadLog();
        //si prende l'username dal file Login.dat e lo si sostituisce nel db con il nuovo nome
        dao.changeUser(username, login.getUser());
        //si cambia anche il file Login.dat
        login.setUser(username);
        filedao.WriteLog(login);
    }

    public int confirmPass(String pass) throws Exception {
        try {
            login = filedao.ReadLog();
            //si prende l'username dal file Login.dat e si sostituisce nel db la password con la nuova immessa
            dao.changePass(login.getUser(), pass);
            //si cambia anche il file Login.dat
            login.setPass(pass);
            filedao.WriteLog(login);
            return 0; //password cambiata
        }catch (ExceptionPass e){
            return 1; //password non valida
        }
    }

    public void deselect() throws IOException, ClassNotFoundException {
        login=filedao.ReadLog();
        login.desetCheck();
        filedao.WriteLog(login);
        filedao.deleteRecipes();
        filedao.deleteSaved();
        filedao.deleteCurrent();
    }
}
