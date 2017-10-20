package db;

import javax.ejb.Stateless;
import entity.*;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * 作業ビジネスロジッククラス
 */
@Stateless
public class MOrderDb {

    @PersistenceContext
    private EntityManager em;

    /**
     * コンストラクタ
     */
    public MOrderDb() { }
    
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
