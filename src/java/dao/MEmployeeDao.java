package dao;

import javax.ejb.Stateless;
import entity.*;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * 社員マスタDAO
 */
@Stateless
public class MEmployeeDao {

    @PersistenceContext
    private EntityManager em;

    /**
     * コンストラクタ
     */
    public MEmployeeDao() { }

    /**
     * 社員を取得する
     * @param id        社員番号
     * @param password  パスワード
     * @return 社員エンティティ
     */
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
    
    /**
     * 全ての社員を取得する
     * @return 社員エンティティのリスト
     */
    public List<MEmployee> findAll() {
        Query query = em.createNamedQuery("MEmployee.findAll");
        List<MEmployee> employeeList = query.getResultList();
        return employeeList;
    }
    
    /**
     * 全ての管理職社員を取得する
     * @return 社員エンティティのリスト
     */
    public List<MEmployee> findAllManager() {
        Query query = em.createNamedQuery("MEmployee.findByManager");
        query.setParameter("manager", 1);
        List<MEmployee> employeeList = query.getResultList();
        return employeeList;
    }
}
