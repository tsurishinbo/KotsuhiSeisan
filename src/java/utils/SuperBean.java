package utils;

import business.Auth;
import java.io.Serializable;
import javax.inject.Inject;

public abstract class SuperBean implements Serializable {
    @Inject
    protected Auth auth;

    public String checkAuthenticated() {
        if (!auth.isAuthenticated()) {
            return "/index.xhtml?faces-redirect=true";
        }
        return null;
    }
    
    public String logout() {
        auth.logout();
        return "index.xhtml?faces-redirect=true";
    }

    public Integer getLoginNo() {
        return auth.getEmpNo();
    }
    
    public String getLoginName() {
        return auth.getEmpName();
    }
    
    public Integer getLoginManager() {
        return auth.getManager();
    }
}
