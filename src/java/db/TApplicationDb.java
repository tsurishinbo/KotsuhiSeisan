package db;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import entity.*;
import java.util.ArrayList;
import util.*;

@Stateless
public class TApplicationDb {

    @PersistenceContext
    private EntityManager em;

    public TApplicationDb() { }
    
    public TApplication findApplicationById(Integer id) {
        Query query = em.createNamedQuery("TApplication.findById");
        query.setParameter("id", id);
        return (TApplication) query.getSingleResult();
    }

    public TLine findLineById(Integer id) {
        Query query = em.createNamedQuery("TLine.findById");
        query.setParameter("id", id);
        return (TLine) query.getSingleResult();
    }

    public List<TApplication> findUnapproved(Integer approveId) {
        Query query = em.createNamedQuery("TApplication.findUnapproved");
        query.setParameter("approveId", approveId);
        return query.getResultList();
    }
    
    public Long getRejectCount(Integer empId) {
        Query query = em.createNamedQuery("TApplication.getRejectCount");
        query.setParameter("applyId", empId);
        return (Long) query.getSingleResult();
    }
    
    public Long getPendingCount(Integer empId) {
        Query query = em.createNamedQuery("TApplication.getPendingCount");
        query.setParameter("approveId", empId);
        return (Long) query.getSingleResult();
    }
    
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

        return query.getResultList();
    }
    
    /**
     * 挿入
     * @param app
     */
    public void insert(TApplication app) {
        em.persist(app);
    }

    /**
     * 更新
     * @param app 
     */
    public void update(TApplication app) {
        em.merge(app);
    }
    
    /**
     * 削除
     * @param app 
     */
    public void delete(TApplication app) {
        em.remove(em.merge(app));
    }
    
    /**
     * 挿入・更新・削除
     * @param app
     * @param addLines
     * @param updLines
     * @param delLines 
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
        em.flush(); //強制的にDBに反映
        em.clear(); //エンティティを破棄（これをしないとエンティティが残る）
    }
}
