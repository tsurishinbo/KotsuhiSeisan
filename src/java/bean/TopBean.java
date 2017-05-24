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
    
    private Long rejectCount;   //差戻し申請件数
    private Long pendingCount;  //承認待ち申請件数
    
    @EJB
    private TApplicationDb tApplicationDb;

    /**
     * 初期処理
     */
    @PostConstruct
    public void init() {
        //差戻し申請件数の取得
        rejectCount = tApplicationDb.getRejectCount(auth.getEmpId());
        //承認待ち申請件数の取得
        pendingCount = tApplicationDb.getPendingCount(auth.getEmpId());
    }

    /**
     * 差戻し申請件数の取得
     * @return 
     */
    public Long getRejectCount() {
        return rejectCount;
    }

    /**
     * 承認待ち申請件数の取得
     * @return 
     */
    public Long getPendingCount() {
        return pendingCount;
    }
}
