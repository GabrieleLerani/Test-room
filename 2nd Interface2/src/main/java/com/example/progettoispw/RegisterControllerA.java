package com.example.progettoispw;

import java.io.IOException;

public class RegisterControllerA {
    private FileInterDAO filedao;

    public void register(LogBean log) throws MyException {
        RegisterDAO dao=RegisterDAO.getInstance();
        dao.registerdao(log);
        filedao=FileInterDAO.getInstance();
    }

    public void initFile(LogBean log) throws IOException {
        Login login=new Login();
        login.setUser(log.getUser());
        login.setPass(log.getPass());
        login.setSpec(log.getCL());
        login.setCL(log.getCL());
        login.setEmail(log.getEmail());
        filedao.writeLog(login);
    }
}
