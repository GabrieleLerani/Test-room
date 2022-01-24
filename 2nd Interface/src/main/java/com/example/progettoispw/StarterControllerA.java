package com.example.progettoispw;

import java.io.IOException;

public class StarterControllerA {
    private StarterDAO dao;
    private FileInterDAO filedao;

    public StarterControllerA(){
        dao=StarterDAO.getInstance();
        filedao=FileInterDAO.getInstance();
    }

    public LogBean getSpec() throws IOException, ClassNotFoundException {
        LogBean lb = Convert.convertEntityToBean(filedao.readLog());
        if(lb ==null){
            return null;
        }
        String spec = dao.getSpec(lb.getUser());
        if(spec ==null){
            return null;
        }
        lb.setSpec(spec);
        filedao.writeLog(Convert.convertBeanToEntity(lb));
        return lb;
    }
}
