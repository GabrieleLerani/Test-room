package com.example.progettoispw;

import java.io.IOException;

public class RegisterControllerA {
    private Login login;
    private RegisterDAO dao;
    private Login l;
    private FileInterDAO filedao;

    public void register(LogBean log) throws Exception{
        dao=RegisterDAO.getInstance();
        dao.registerdao(log);
        filedao=FileInterDAO.getInstance();
    }

    public void initFile(LogBean log) throws IOException {
        login=new Login();
        login.setUser(log.getUser());
        login.setPass(log.getPass());
        login.setSpec(log.getCL());
        login.setCL(log.getCL());
        login.setEmail(log.getEmail());
        filedao.WriteLog(login);
    }
}
