package dao;

import javax.ejb.Stateless;
import entity.*;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * 作業マスタDAO
 */
@Stateless
public class MOrderDao {

    @PersistenceContext
    private EntityManager em;

    /**
     * コンストラクタ
     */
    public MOrderDao() { }
    
    /**
     * 全ての作業を取得する
     * @return 作業エンティティのリスト
     */
    public List<MOrder> findAll() {
        Query query = em.createNamedQuery("MOrder.findAll");
        List<MOrder> orderList = query.getResultList();
        return orderList;
    }
}
