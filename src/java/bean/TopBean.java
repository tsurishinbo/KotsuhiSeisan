package bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import db.*;
import util.*;

@Named
@RequestScoped
public class TopBean extends SuperBean {
    
    private Long rejectCount;
    private Long pendingCount;
    
    @EJB
    private TApplicationDb tApplicationDb;

    @PostConstruct
    public void init() {
        rejectCount = tApplicationDb.getRejectCount(auth.getEmpId());
        pendingCount = tApplicationDb.getPendingCount(auth.getEmpId());
    }

    public Long getRejectCount() {
        return rejectCount;
    }

    public void setRejectCount(Long rejectCount) {
        this.rejectCount = rejectCount;
    }

    public Long getPendingCount() {
        return pendingCount;
    }

    public void setPendingCount(Long pendingCount) {
        this.pendingCount = pendingCount;
    }
   
}
