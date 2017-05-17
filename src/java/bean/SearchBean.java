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
public class SearchBean extends SuperBean {
    
    private Date dateFrom;
    private Date dateTo;
    private Integer applyId;
    private Integer approveId;
    private Integer status;
    
    private final Map<String, Integer> applyItems = new LinkedHashMap<>();
    private final Map<String, Integer> approveItems = new LinkedHashMap<>();
      
    @EJB
    private MEmployeeDb mEmployeeDb;

    @PostConstruct
    public void init() {
        applyId = auth.getEmpId();
        approveId = auth.getBossId();
        status = 0;
        setApplyItems();
        setApproveItems();
    }
    
    public String search() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.put("dateFrom", dateFrom);
        flash.put("dateTo", dateTo);
        flash.put("applyId", applyId);
        flash.put("approveId", approveId);
        flash.put("status", status);
        return "result.xhtml?faces-redirect=true";
    }
    
    private void setApplyItems() {
        List<MEmployee> applyList = mEmployeeDb.findAll();
        applyItems.put("", null);
        for (MEmployee employee : applyList) {
            applyItems.put(employee.getEmployeeName(), employee.getId());
        }
    }
    
    private void setApproveItems() {
        List<MEmployee> approveList = mEmployeeDb.findAllManager();
        approveItems.put("", null);
        for (MEmployee employee : approveList) {
            approveItems.put(employee.getEmployeeName(), employee.getId());
        }
    }
    
    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public Integer getApproveId() {
        return approveId;
    }

    public void setApproveId(Integer approveId) {
        this.approveId = approveId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Map<String, Integer> getApplyItems() {
        return applyItems;
    }

    public Map<String, Integer> getApproveItems() {
        return approveItems;
    }
}
