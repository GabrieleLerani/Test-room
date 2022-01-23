package com.example.progettoispw;

import java.io.IOException;

public class BackControllerA {
    private FileInterDAO filedao;
    private Login login;

    public BackControllerA(){
        filedao=FileInterDAO.getInstance();
    }

    public String getSpecialization() throws IOException, ClassNotFoundException {
        login=filedao.ReadLog();
        return login.getSpec();
    }
}
