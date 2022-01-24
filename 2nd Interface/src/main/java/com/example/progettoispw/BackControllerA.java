package com.example.progettoispw;

import java.io.IOException;

public class BackControllerA {
    private FileInterDAO filedao;

    public BackControllerA(){
        filedao=FileInterDAO.getInstance();
    }

    public String getSpecialization() throws IOException, ClassNotFoundException {
        Login login=filedao.readLog();
        return login.getSpec();
    }
}
