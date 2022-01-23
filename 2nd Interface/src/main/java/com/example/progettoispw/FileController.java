package com.example.progettoispw;

import java.io.IOException;

public class FileController {
    private FileInterDAO filedao;
    private Login login;
    private LogBean lb;

    public FileController(){
        filedao=FileInterDAO.getInstance();
    }

    public LogBean getLog() throws IOException, ClassNotFoundException {
        login=filedao.ReadLog();
        lb=Convert.ConvertEntityToBean(login);
        return lb;
    }
}
