package db;

import entity.TLine;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class TLineDb {
    
    @PersistenceContext
    private EntityManager em;

    public TLineDb() { }
    
    public TLine findById(Integer id) {
        Query query = em.createNamedQuery("TLine.findById");
        query.setParameter("id", id);
        return (TLine) query.getSingleResult();
    }
    
    public void insert(TLine line) {
        em.persist(line);
    }
    
    public void update(TLine line) {
        em.merge(line);
    }
    
    public void delete(TLine line) {
        em.remove(em.merge(line));
    }
    
}
