package db;

import javax.ejb.Stateless;
import entity.*;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class MEmployeeDb {

    @PersistenceContext
    private EntityManager em;

    public MEmployeeDb() { }

    public MEmployee getEmployee(Integer id, String password) {
        Query query = em.createNamedQuery("MEmployee.findByIdAndPassword");
        query.setParameter("id", id);
        query.setParameter("password", password);
        MEmployee employee;
        try {
            employee = (MEmployee)query.getSingleResult();
        } catch (javax.persistence.NoResultException e ) {
            employee = null;
        }
        return employee;
    }
    
    public List<MEmployee> findAll() {
        Query query = em.createNamedQuery("MEmployee.findAll");
        List<MEmployee> employeeList = query.getResultList();
        return employeeList;
    }
    
    public List<MEmployee> findAllManager() {
        Query query = em.createNamedQuery("MEmployee.findByManager");
        query.setParameter("manager", 1);
        List<MEmployee> employeeList = query.getResultList();
        return employeeList;
    }
    
}
