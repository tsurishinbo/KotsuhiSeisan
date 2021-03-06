package dao;

import javax.ejb.Stateless;
import entity.*;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * 交通手段マスタDAO
 */
@Stateless
public class MMeansDao {

    @PersistenceContext
    private EntityManager em;

    /**
     * コンストラクタ
     */
    public MMeansDao() { }
    
    /**
     * 全ての交通手段を取得する
     * @return 交通手段エンティティのリスト
     */
    public List<MMeans> findAll() {
        Query query = em.createNamedQuery("MMeans.findAll");
        List<MMeans> meansList = query.getResultList();
        return meansList;
    }
}
