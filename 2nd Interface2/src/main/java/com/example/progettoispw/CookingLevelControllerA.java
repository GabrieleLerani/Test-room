package com.example.progettoispw;

import java.io.IOException;

public class CookingLevelControllerA {
    private CookingLevelDAO dao;
    private FileInterDAO filedao;

    public CookingLevelControllerA(){
        dao=CookingLevelDAO.getInstance();
        filedao=FileInterDAO.getInstance();
    }

    public void setCL(String cl) throws IOException, ClassNotFoundException {
        Login login=filedao.readLog();
        String username=login.getUser();
        dao.insertCL(cl, username);
        login.setCL(cl);
        filedao.writeLog(login);
    }
}

