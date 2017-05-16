package bean;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import db.*;
import entity.*;
import util.*;

@Named
@RequestScoped
public class IndexBean extends SuperBean {
    
    private Integer empNo;
    private String password;

    @EJB
    private MEmployeeDb mEmployeeDb;
    
    public String login() {
        MEmployee employee = mEmployeeDb.getEmployee(empNo, password);
        if (employee != null) {
            auth.login(
                employee.getId(),
                employee.getEmployeeName(),
                employee.getBossId(),
                employee.getManager()
            );
            return "top.xhtml?faces-redirect=true";
        } else {
            return null;
        }
    }

    public Integer getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Integer empNo) {
        this.empNo = empNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
