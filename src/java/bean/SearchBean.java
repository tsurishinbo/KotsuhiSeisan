package bean;

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
import db.*;
import entity.*;
import util.*;

@Named
@ViewScoped
public class SearchBean extends SuperBean implements Serializable {
    
    private Date dateFrom;              //申請日From
    private Date dateTo;                //申請日To
    private Integer applyId;            //申請者ID
    private Integer approveId;          //承認者ID
    private Integer status;             //ステータス
    private List<TApplication> appList; //申請一覧
    private final Map<String, Integer> applyItems = new LinkedHashMap<>();      //申請者リスト
    private final Map<String, Integer> approveItems = new LinkedHashMap<>();    //承認者リスト
      
    @EJB
    private MEmployeeDb mEmployeeDb;
    @EJB
    private TApplicationDb tApplicationDb;

    /**
     * 初期処理
     */
    @PostConstruct
    public void init() {
        //リストの作成
        makeApplyItems();
        makeApproveItems();
        //フラッシュから情報を取得
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        if (flash.size() == 0) {
            //メニューから遷移：画面初期化
            dateFrom = null;
            dateTo = null;
            applyId = auth.getEmpId();
            approveId = auth.getBossId();
            status = 0;
            appList = null;
        } else {
            //申請照会画面から遷移：画面を遷移前の状態に戻す
            dateFrom = (Date)flash.get("dateFrom");
            dateTo = (Date)flash.get("dateTo");
            applyId = (Integer)flash.get("applyId");
            approveId = (Integer)flash.get("approveId");
            status = (Integer)flash.get("status");
            appList = tApplicationDb.getSearchResult(dateFrom, dateTo, applyId, approveId, status);
        }
    }
    
    /**
     * 検索処理
     * @return 
     */
    public String search() {
        appList = tApplicationDb.getSearchResult(dateFrom, dateTo, applyId, approveId, status);
        return null;
    }
    
    /**
     * 詳細処理
     * @param app
     * @return 
     */
    public String detail(TApplication app) {
        //フラッシュに情報を設定
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.put("dateFrom", dateFrom);    //申請日From
        flash.put("dateTo", dateTo);        //申請日To
        flash.put("applyId", applyId);      //申請者ID
        flash.put("approveId", approveId);  //承認者ID
        flash.put("status", status);        //ステータス
        flash.put("detailId", app.getId()); //照会する申請ID
        //申請照会画面に遷移
        return "detail.xhtml?faces-redirect=true";
    }
    
    /**
     * 申請者リストの作成
     */
    private void makeApplyItems() {
        List<MEmployee> applyList = mEmployeeDb.findAll();
        applyItems.put("", null);
        for (MEmployee employee : applyList) {
            applyItems.put(employee.getEmployeeName(), employee.getId());
        }
    }
    
    /**
     * 承認者リストの作成
     */
    private void makeApproveItems() {
        List<MEmployee> approveList = mEmployeeDb.findAllManager();
        approveItems.put("", null);
        for (MEmployee employee : approveList) {
            approveItems.put(employee.getEmployeeName(), employee.getId());
        }
    }

    /**
     * 申請日Fromの取得
     * @return 
     */
    public Date getDateFrom() {
        return dateFrom;
    }

    /**
     * 申請日Fromの設定
     * @param dateFrom 
     */
    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * 申請日Toの取得
     * @return 
     */
    public Date getDateTo() {
        return dateTo;
    }

    /**
     * 申請日Toの設定
     * @param dateTo 
     */
    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    /**
     * 申請者IDの取得
     * @return 
     */
    public Integer getApplyId() {
        return applyId;
    }

    /**
     * 申請者IDの設定
     * @param applyId 
     */
    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    /**
     * 承認者IDの取得
     * @return 
     */
    public Integer getApproveId() {
        return approveId;
    }

    /**
     * 承認者IDの設定
     * @param approveId 
     */
    public void setApproveId(Integer approveId) {
        this.approveId = approveId;
    }

    /**
     * ステータスの取得
     * @return 
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * ステータスの設定
     * @param status 
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 申請一覧の取得
     * @return 
     */
    public List<TApplication> getAppList() {
        return appList;
    }

    /**
     * 申請者リストの取得
     * @return 
     */
    public Map<String, Integer> getApplyItems() {
        return applyItems;
    }

    /**
     * 承認者リストの取得
     * @return 
     */
    public Map<String, Integer> getApproveItems() {
        return approveItems;
    }
}
