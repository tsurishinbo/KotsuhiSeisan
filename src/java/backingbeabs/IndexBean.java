package backingbeabs;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import utils.*;

@Named
@RequestScoped
public class IndexBean extends SuperBean {
    private Integer empNo;
    private String password;

    public String login() {
        auth.login(empNo, password);
        if (auth.isAuthenticated()) {
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
