package db;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import entity.*;

/**
 * 申請ビジネスロジッククラス
 */
@Stateless
public class TApplicationDb {

    @PersistenceContext
    private EntityManager em;

    /**
     * コンストラクタ
     */
    public TApplicationDb() { }
    
    /**
     * 申請を取得する
     * @param id 申請ID
     * @return 申請エンティティ
     */
    public TApplication findApplicationById(Integer id) {
        Query query = em.createNamedQuery("TApplication.findById");
        query.setParameter("id", id);
        TApplication app = (TApplication) query.getSingleResult();
        em.refresh(app);
        return app;
    }

    /**
     * 申請明細を取得する
     * @param id 申請明細ID
     * @return 申請明細エンティティ
     */
    public TLine findLineById(Integer id) {
        Query query = em.createNamedQuery("TLine.findById");
        query.setParameter("id", id);
        TLine line = (TLine) query.getSingleResult();
        em.refresh(line);
        return line;
    }

    /**
     * 未承認の申請を取得する
     * @param approveId 承認者ID
     * @return 申請エンティティのリスト
     */
    public List<TApplication> findUnapproved(Integer approveId) {
        Query query = em.createNamedQuery("TApplication.findUnapproved");
        query.setParameter("approveId", approveId);
        List<TApplication> appList = query.getResultList();
        for (TApplication app : appList) {
            em.refresh(app);
        }
        return appList;
    }
    
    /**
     * 差戻し状態の申請件数を取得する
     * @param empId 申請者ID
     * @return 差戻し状態の申請件数
     */
    public Long getRejectCount(Integer empId) {
        Query query = em.createNamedQuery("TApplication.getRejectCount");
        query.setParameter("applyId", empId);
        return (Long) query.getSingleResult();
    }
    
    /**
     * 承認待ち状態の申請件数を取得する
     * @param empId 承認者ID
     * @return 承認待ち状態の申請件数
     */
    public Long getPendingCount(Integer empId) {
        Query query = em.createNamedQuery("TApplication.getPendingCount");
        query.setParameter("approveId", empId);
        return (Long) query.getSingleResult();
    }
    
    /**
     * 検索条件に合致する申請を取得する
     * @param dateFrom  利用日(From)
     * @param dateTo    利用日(To)
     * @param applyId   申請者ID
     * @param approveId 承認者ID
     * @param status    申請の状態(1:未申請 2:承認待ち 3:承認済 4:差戻し)
     * @return 申請エンティティのリスト
     */
    public List<TApplication> getSearchResult(
            Date dateFrom, Date dateTo, Integer applyId, Integer approveId, Integer status) {

        StringBuilder jpql = new StringBuilder();
        jpql.append("select t ");
        jpql.append("from TApplication t ");
        jpql.append("where 0=0 ");
        if (dateFrom != null) {
            jpql.append("and t.applyDate >= :applyDateFrom ");
        }
        if (dateTo != null) {
            jpql.append("and t.applyDate <= :applyDateTo ");
        }
        if (applyId != null) {
            jpql.append("and t.applyId = :applyId ");
        }
        if (approveId != null) {
            jpql.append("and t.approveId = :approveId ");
        }
        if (status != 0) {
            jpql.append("and t.status = :status ");
        }
        jpql.append("order by t.id ");

        Query query = em.createQuery(jpql.toString(), TApplication.class);
        if (dateFrom != null) {
            query.setParameter("applyDateFrom", dateFrom);
        }
        if (dateTo != null) {
            query.setParameter("applyDateTo", dateTo);
        }
        if (applyId != null) {
            query.setParameter("applyId", applyId);
        }
        if (approveId != null) {
            query.setParameter("approveId", approveId);
        }
        if (status != 0) {
            query.setParameter("status", status);
        }

        List<TApplication> appList = query.getResultList();
        for (TApplication app : appList) {
            em.refresh(app);
        }
        return appList;
    }
    
    /**
     * 申請を追加する
     * @param app 追加する申請エンティティ
     */
    public void insert(TApplication app) {
        em.persist(app);
        em.flush();
        em.clear();
    }

    /**
     * 申請を更新する
     * @param app 更新する申請エンティティ
     */
    public void update(TApplication app) {
        em.merge(app);
        em.flush();
        em.clear();
    }
    
    /**
     * 申請を削除する
     * @param app 削除する申請エンティティ
     */
    public void delete(TApplication app) {
        em.remove(em.merge(app));
        em.flush();
        em.clear();
    }
    
    /**
     * 申請明細を追加・更新・削除する
     * @param app       申請エンティティ
     * @param addLines  追加する申請明細エンティティのリスト
     * @param updLines  更新する申請明細エンティティのリスト
     * @param delLines  削除する申請明細エンティティのリスト
     */
    public void insertAndUpdateAndDelete(TApplication app, List<TLine> addLines, List<TLine> updLines, List<TLine> delLines) {
        for (TLine addLine : addLines) {
            em.persist(addLine);
        }
        for (TLine updLine : updLines) {
            em.merge(updLine);
        }
        for (TLine delLine : delLines) {
            em.remove(em.merge(delLine));
        }
        em.merge(app);
        em.flush();
        em.clear();
    }
}
