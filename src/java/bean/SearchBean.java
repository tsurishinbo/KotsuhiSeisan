package bean;

import dao.TApplicationDao;
import dao.MEmployeeDao;
import java.util.Date;
import java.util.Map;
import javax.inject.Named;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import entity.*;
import util.*;

/**
 * 申請検索画面バッキングビーン
 */
@Named
@ViewScoped
public class SearchBean extends SuperBean implements Serializable {
    
    private Date dateFrom;              // 申請日(From)
    private Date dateTo;                // 申請日(To)
    private Integer applyId;            // 申請者ID
    private Integer approveId;          // 承認者ID
    private Integer status;             // ステータス
    private List<TApplication> appList; // 申請一覧
    private final Map<String, Integer> applyItems = new LinkedHashMap<>();  // 申請者リスト
    private final Map<String, Integer> approveItems = new LinkedHashMap<>();// 承認者リスト
      
    @EJB
    private MEmployeeDao mEmployeeDao;
    @EJB
    private TApplicationDao tApplicationDao;

    /**
     * 初期処理
     */
    @PostConstruct
    public void init() {
        // リストの作成
        makeApplyItems();
        makeApproveItems();
        // フラッシュから情報を取得
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        if (flash.size() == 0) {
            // メニューから遷移：検索条件を初期化
            dateFrom = null;
            dateTo = null;
            applyId = auth.getEmpId();
            approveId = auth.getBossId();
            status = 0;
            appList = null;
        } else {
            // 申請照会画面から遷移：検索条件を申請照会画面遷移時に戻す
            dateFrom = (Date)flash.get("dateFrom");
            dateTo = (Date)flash.get("dateTo");
            applyId = (Integer)flash.get("applyId");
            approveId = (Integer)flash.get("approveId");
            status = (Integer)flash.get("status");
            appList = tApplicationDao.getSearchResult(dateFrom, dateTo, applyId, approveId, status);
        }
    }
    
    /**
     * 検索処理
     * @return 次画面のURL
     */
    public String search() {
        appList = tApplicationDao.getSearchResult(dateFrom, dateTo, applyId, approveId, status);
        return null;
    }
    
    /**
     * 詳細処理
     * @param id 申請ID
     * @return 次画面のURL
     */
    public String detail(Integer id) {
        // フラッシュに情報を設定
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.put("dateFrom", dateFrom);    // 申請日(From)
        flash.put("dateTo", dateTo);        // 申請日(To)
        flash.put("applyId", applyId);      // 申請者ID
        flash.put("approveId", approveId);  // 承認者ID
        flash.put("status", status);        // ステータス
        flash.put("detailId", id);          // 照会する申請ID
        // 申請照会画面に遷移
        return "detail.xhtml?faces-redirect=true";
    }
    
    /**
     * 申請者リストを作成する
     */
    private void makeApplyItems() {
        if (auth.getManager() == 1) {
            // 管理職は全ての社員を選択可
            List<MEmployee> applyList = mEmployeeDao.findAll();
            applyItems.put("", null);
            for (MEmployee employee : applyList) {
                applyItems.put(employee.getEmployeeName(), employee.getId());
            }
        } else {
            // 一般職は自分のみ選択可
            applyItems.put(auth.getEmpName(), auth.getEmpId());
        }
    }
    
    /**
     * 承認者リストを作成する
     */
    private void makeApproveItems() {
        List<MEmployee> approveList = mEmployeeDao.findAllManager();
        approveItems.put("", null);
        for (MEmployee employee : approveList) {
            approveItems.put(employee.getEmployeeName(), employee.getId());
        }
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public Integer getApproveId() {
        return approveId;
    }

    public void setApproveId(Integer approveId) {
        this.approveId = approveId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<TApplication> getAppList() {
        return appList;
    }

    public Map<String, Integer> getApplyItems() {
        return applyItems;
    }

    public Map<String, Integer> getApproveItems() {
        return approveItems;
    }
}
