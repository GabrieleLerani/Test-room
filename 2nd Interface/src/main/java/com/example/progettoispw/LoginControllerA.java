package com.example.progettoispw;

import java.io.IOException;

public class LoginControllerA {
    private LoginDAO dao;
    private FileInterDAO filedao;
    private Login save;
    private int s=0;

    public LoginControllerA(){
        filedao=FileInterDAO.getInstance();
    }

    public int checkUserAndPass(LogBean a) throws IOException {
        dao=LoginDAO.getInstance();
        save= dao.Login(a.getUser());
        if(a.getUser().equals(save.getUser()) && a.getPass().equals(save.getPass())){
            filedao.WriteLog(save);
            System.out.println("Login eseguito");
            if(save.getSpec().equalsIgnoreCase("User") || save.getSpec().equalsIgnoreCase("Premium")) {
                s=1;
            }else if(save.getSpec().equalsIgnoreCase("Chef")){
                s=2;
            }
        }else{
            System.out.println("Login fallito");
            s=0;
        }
        return s;
    }

    public void select() throws IOException, ClassNotFoundException {
        Login login=filedao.ReadLog();
        login.setCheck();
        filedao.WriteLog(login);
    }
}
