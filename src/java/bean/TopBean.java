package bean;

import dao.TApplicationDao;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import util.*;

/**
 * トップ画面バッキングビーン
 */
@Named
@RequestScoped
public class TopBean extends SuperBean {
    
    private Long rejectCount;   // 差戻し申請件数
    private Long pendingCount;  // 承認待ち申請件数
    
    @EJB
    private TApplicationDao tApplicationDao;

    /**
     * 初期処理
     */
    @PostConstruct
    public void init() {
        // 差戻し申請件数の取得
        rejectCount = tApplicationDao.getRejectCount(auth.getEmpId());
        // 承認待ち申請件数の取得
        pendingCount = tApplicationDao.getPendingCount(auth.getEmpId());
    }

    public Long getRejectCount() {
        return rejectCount;
    }

    public Long getPendingCount() {
        return pendingCount;
    }
}
