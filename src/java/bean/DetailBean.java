package bean;

import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import java.util.List;
import db.*;
import entity.*;
import util.*;

@Named
@ViewScoped
public class DetailBean extends SuperBean implements Serializable {
    
    private Date dateFrom;      //申請日From
    private Date dateTo;        //申請日To
    private Integer applyId;    //申請者ID
    private Integer approveId;  //承認者ID
    private Integer status;     //ステータス
    private TApplication app;   //照会する申請
    
    @EJB
    private TApplicationDb tApplicationDb;

    /**
     * 初期処理
     */
    @PostConstruct
    public void init() {
        //フラッシュから情報を取得
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        dateFrom = (Date)flash.get("dateFrom");
        dateTo = (Date)flash.get("dateTo");
        applyId = (Integer)flash.get("applyId");
        approveId = (Integer)flash.get("approveId");
        status = (Integer)flash.get("status");
        Integer detailId = (Integer)flash.get("detailId");
        //照会する申請を取得
        app = tApplicationDb.findApplicationById(detailId);
    }
  
    /**
     * 編集処理
     * @return 
     */
    public String edit() {
        //フラッシュに情報を設定
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.put("editId", app.getId());   //編集する申請ID
        //申請作成画面に遷移
        return "make.xhtml?faces-redirect=true";
    }
    
    /**
     * 削除処理
     * @return 
     */
    public String delete() {
        //申請削除
        tApplicationDb.delete(app);
        //検索条件をフラッシュに設定
        setFlash();
        //申請検索画面に遷移
        return "search.xhtml?faces-redirect=true";
    }
    
    /**
     * 申請取消処理
     * @return 
     */
    public String cancel() {
        //申請取消
        app.setStatus(1);
        app.setApplyDate(null);
        tApplicationDb.cancel(app);
        //検索条件をフラッシュに設定
        setFlash();
        //申請検索画面に遷移
        return "search.xhtml?faces-redirect=true";
    }
    
    /**
     * 戻る処理
     * @return 
     */
    public String back() {
        //検索条件をフラッシュに設定
        setFlash();
        //申請検索画面に遷移
        return "search.xhtml?faces-redirect=true";
    }
    
    /**
     * 検索条件をフラッシュに設定
     */
    private void setFlash() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.put("dateFrom", dateFrom);
        flash.put("dateTo", dateTo);
        flash.put("applyId", applyId);
        flash.put("approveId", approveId);
        flash.put("status", status);
        flash.put("detailId", app.getId());
    }
    
    /**
     * 編集ボタンの使用不可を取得
     * @return 
     */
    public boolean isDisabledEdit() {
        boolean enabled = false;
        if (app.getApplyId() == auth.getEmpId() && 
                (app.getStatus() == 1 || app.getStatus() == 4)) {
            enabled = true;
        }
        return !enabled;
    }
    
    /**
     * 削除ボタンの使用不可を取得
     * @return 
     */
    public boolean isDisabledDelete() {
        boolean enabled = false;
        if (app.getApplyId() == auth.getEmpId() &&
                (app.getStatus() == 1 || app.getStatus() == 4)) {
            enabled = true;
        }
        return !enabled;
    }
    
    /**
     * 申請取消ボタンの使用不可を取得
     * @return 
     */
    public boolean isDisabledCancel() {
        boolean enabled = false;
        if (app.getApplyId() == auth.getEmpId() &&
                app.getStatus() == 2) {
            enabled = true;
        }
        return !enabled;
    }

    /**
     * 照会する申請の取得
     * @return 
     */
    public TApplication getApp() {
        return app;
    }
}
