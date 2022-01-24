package com.example.progettoispw;

import java.io.IOException;

public class StarterControllerA {
    private StarterDAO dao;
    private FileInterDAO filedao;
    private LogBean lb;
    private String spec;

    public StarterControllerA(){
        dao=StarterDAO.getInstance();
        filedao=FileInterDAO.getInstance();
    }

    public LogBean getSpec() throws IOException, ClassNotFoundException {
        lb=Convert.ConvertEntityToBean(filedao.ReadLog());
        if(lb==null){
            return null;
        }
        spec=dao.getSpec(lb.getUser());
        if(spec==null){
            return null;
        }
        lb.setSpec(spec);
        filedao.WriteLog(Convert.ConvertBeanToEntity(lb));
        return lb;
    }
}
