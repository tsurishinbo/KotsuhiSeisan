package bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import db.*;
import entity.*;
import util.*;

/**
 * 未承認一覧画面バッキングビーン
 */
@Named
@ViewScoped
public class UnapprovedBean extends SuperBean implements Serializable {

    private Integer approveId;          // 承認者ID
    private List<TApplication> appList; // 未承認申請一覧
    private final Map<String, Integer> approveItems = new LinkedHashMap<>();// 承認者リスト

    @EJB
    private MEmployeeDb mEmployeeDb;
    @EJB
    private TApplicationDb tApplicationDb;

    /**
     * 初期処理
     */
    @PostConstruct
    public void init() {
        // リストの作成
        makeApproveItems();
        // フラッシュから情報を取得
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        if (flash.size() == 0) {
            // メニューから遷移：承認者を初期化（自分）
            approveId = auth.getEmpId();
        } else {
            // 申請承認画面から遷移：承認者を申請承認画面遷移時に戻す
            approveId = (Integer)flash.get("approveId");
        }
        // 未承認申請一覧の取得
        getUnapprovedList();
    }
    
    /**
     * 承認者変更処理
     * @param e
     */
    public void changeApprove(ValueChangeEvent e) {
        // 選択された承認者IDの取得
        approveId = (Integer)e.getNewValue();
        // 未承認申請一覧の取得
        getUnapprovedList();
    } 

    /**
     * 詳細処理
     * @param id 申請ID
     * @return 次画面のURL
     */
    public String detail(Integer id) {
        // フラッシュに情報を設定
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.put("approveId", approveId);  // 承認者ID
        flash.put("detailId", id);          // 照会する申請ID
        // 申請承認画面に遷移
        return "approve.xhtml?faces-redirect=true";
    }
    
    /**
     * 承認者リストを作成する
     */
    private void makeApproveItems() {
        List<MEmployee> approveList = mEmployeeDb.findAllManager();
        for (MEmployee employee : approveList) {
            approveItems.put(employee.getEmployeeName(), employee.getId());
        }
    }
    
    private void getUnapprovedList() {
        appList = tApplicationDb.findUnapproved(approveId);
    }

    public Integer getApproveId() {
        return approveId;
    }

    public void setApproveId(Integer approveId) {
        this.approveId = approveId;
    }

    public List<TApplication> getAppList() {
        return appList;
    }

    public Map<String, Integer> getApproveItems() {
        return approveItems;
    }
}
