package com.example.progettoispw;

import java.io.IOException;

public class FileController {
    private FileInterDAO filedao;

    public FileController(){
        filedao=FileInterDAO.getInstance();
    }

    public LogBean getLog() throws IOException, ClassNotFoundException {
        Login login=filedao.readLog();
        return Convert.convertEntityToBean(login);
    }
}
