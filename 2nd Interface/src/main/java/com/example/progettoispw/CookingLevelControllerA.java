package com.example.progettoispw;

import java.io.IOException;

public class CookingLevelControllerA {
    private Login login;
    private CookingLevelDAO dao;
    private FileInterDAO filedao;

    public CookingLevelControllerA(){
        dao=CookingLevelDAO.getInstance();
        filedao=FileInterDAO.getInstance();
    }

    public void setCL(String CL) throws IOException, ClassNotFoundException {
        login=filedao.ReadLog();
        String username=login.getUser();
        dao.insertCL(CL, username);
        login.setCL(CL);
        filedao.WriteLog(login);
    }
}
