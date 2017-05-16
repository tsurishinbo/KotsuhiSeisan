package db;

import javax.ejb.Stateless;
import entity.*;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class MOrderDb {

    @PersistenceContext
    private EntityManager em;

    public MOrderDb() { }
    
    public List<MOrder> findAll() {
        Query query = em.createNamedQuery("MOrder.findAll");
        List<MOrder> orderList = query.getResultList();
        return orderList;
    }
}
