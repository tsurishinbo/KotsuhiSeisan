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
    
    public TApplication findById(Integer id) {
        Query query = em.createNamedQuery("TApplication.findById");
        query.setParameter("id", id);
        return (TApplication) query.getSingleResult();
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

        List<TApplication> result = query.getResultList();
        return result;
    }
    
    public void insert(TApplication app) {
        em.persist(app);
    }
    
    public void update(TApplication app) {
        em.merge(app);
    }
    
    public void delete(TApplication app) {
        em.remove(em.merge(app));
    }
    
}
