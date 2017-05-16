package util;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;

@SessionScoped
public class Auth implements Serializable {

    private Integer empId;
    private String empName;
    private Integer bossId;
    private Integer manager;
    private boolean authenticated;

    public void login(Integer empId, String empName, Integer bossId, Integer manager) {
        this.empId = empId;
        this.empName = empName;
        this.bossId = bossId;
        this.manager = manager;
        this.authenticated = true;
    }

    public void logout() {
        this.empId = null;
        this.empName = null;
        this.bossId = null;
        this.manager = null;
        this.authenticated = false;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Integer getBossId() {
        return bossId;
    }

    public void setBossId(Integer bossId) {
        this.bossId = bossId;
    }

    public Integer getManager() {
        return manager;
    }

    public void setManager(Integer manager) {
        this.manager = manager;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    
}
