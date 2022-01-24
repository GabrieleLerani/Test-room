package com.example.progettoispw;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginControllerA {
    private FileInterDAO filedao;
    private static Logger logger=Logger.getLogger(LoginControllerA.class.getName());
    private int s=0;

    public LoginControllerA(){
        filedao=FileInterDAO.getInstance();
    }

    public int checkUserAndPass(LogBean a) throws IOException {
        LoginDAO dao=LoginDAO.getInstance();
        Login save= dao.enter(a.getUser());
        if(a.getUser().equals(save.getUser()) && a.getPass().equals(save.getPass())){
            filedao.writeLog(save);
            logger.log(Level.INFO, "Login eseguito");
            if(save.getSpec().equalsIgnoreCase("User") || save.getSpec().equalsIgnoreCase("Premium")) {
                s=1;
            }else if(save.getSpec().equalsIgnoreCase("Chef")){
                s=2;
            }
        }else{
            logger.log(Level.INFO, "Login fallito");
            s=0;
        }
        return s;
    }

    public void select() throws IOException, ClassNotFoundException {
        Login login=filedao.readLog();
        login.setCheck();
        filedao.writeLog(login);
    }
}
