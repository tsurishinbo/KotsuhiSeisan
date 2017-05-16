package business;

import entity.MEmployee;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.persistence.*;

@SessionScoped
public class Auth implements Serializable {

    @PersistenceContext
    private EntityManager em;
    
    private Integer empNo;
    private String empName;
    private Integer manager;
    private boolean authenticated;

    public void login(Integer empNo, String password) {
        List<MEmployee> employees = em.createNamedQuery("MEmployee.findByIdAndPassword", MEmployee.class)
                .setParameter("id", empNo)
                .setParameter("password", password)
                .getResultList();
        if (employees.size() > 0) {
            this.empNo = empNo;
            this.empName = employees.get(0).getEmployeeName();
            this.manager = employees.get(0).getManager();
            this.authenticated = true;
        }
    }

    public void logout() {
        this.empNo = null;
        this.empName = null;
        this.manager = null;
        this.authenticated = false;
    }

    public Integer getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Integer empNo) {
        this.empNo = empNo;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
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
