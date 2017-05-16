package db;

import javax.ejb.Stateless;
import entity.*;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class MMeansDb {

    @PersistenceContext
    private EntityManager em;

    public MMeansDb() { }
    
    public List<MMeans> findAll() {
        Query query = em.createNamedQuery("MMeans.findAll");
        List<MMeans> meansList = query.getResultList();
        return meansList;
    }
}
