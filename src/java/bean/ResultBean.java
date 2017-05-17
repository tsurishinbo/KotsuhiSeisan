package bean;

import java.util.Date;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import db.*;
import entity.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import util.*;

@Named
@RequestScoped
public class ResultBean extends SuperBean {
    
    private List<TApplication> appList;
    @EJB
    private TApplicationDb tApplicationDb;

    @PostConstruct
    
    public void init() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        appList = tApplicationDb.getSearchResult(
            (Date)flash.get("dateFrom"),
            (Date)flash.get("dateTo"),
            (Integer)flash.get("applyId"),
            (Integer)flash.get("approveId"),
            (Integer)flash.get("status")
        );
    }
    
    public String edit(TApplication app) {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.put("editId", app.getId());
        
        return "make.xhtml?faces-redirect=true";
    }
    
    public String delete(TApplication app) {
        appList.remove(app);
        tApplicationDb.delete(app);
        
        return null;
    }
    
    public String cancel(TApplication app) {
        app.setStatus(1);
        app.setApplyDate(null);
        tApplicationDb.update(app);

        return null;
    }
    
    public boolean isDisabledEdit(TApplication app) {
        boolean enabled = false;
        if (app.getApplyId() == auth.getEmpId() && 
                (app.getStatus() == 1 || app.getStatus() == 4)) {
            enabled = true;
        }
        return !enabled;
    }
    
    public boolean isDisabledDelete(TApplication app) {
        boolean enabled = false;
        if (app.getApplyId() == auth.getEmpId() &&
                (app.getStatus() == 1 || app.getStatus() == 4)) {
            enabled = true;
        }
        return !enabled;
    }
    
    public boolean isDisabledCancel(TApplication app) {
        boolean enabled = false;
        if (app.getApplyId() == auth.getEmpId() &&
                app.getStatus() == 2) {
            enabled = true;
        }
        return !enabled;
    }

    public List<TApplication> getAppList() {
        return appList;
    }

    public void setAppList(List<TApplication> appList) {
        this.appList = appList;
    }
}
