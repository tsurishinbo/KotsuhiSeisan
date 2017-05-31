package bean;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import db.*;
import entity.*;
import java.util.Date;
import util.*;

@Named
@ViewScoped
public class ApproveBean extends SuperBean implements Serializable {
    
    private Integer approveId;  //承認者ID
    private TApplication app;   //承認する申請
    
    @EJB
    private TApplicationDb tApplicationDb;
    
    /**
     * 初期処理
     */
    @PostConstruct
    public void init() {
        //フラッシュから情報を取得
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        approveId = (Integer)flash.get("approveId");
        //照会する申請を取得
        app = tApplicationDb.findApplicationById((Integer)flash.get("detailId"));
    }
    
    /**
     * 承認処理
     * @return 
     */
    public String approve() {
        //申請承認
        app.setStatus(3);
        app.setApproveId(auth.getEmpId());
        app.setApproveDate(new Date());
        tApplicationDb.update(app);
        //戻る処理
        return back();
    }
    
    /**
     * 差戻し処理
     * @return 
     */
    public String reject() {
        //申請差戻し
        app.setStatus(4);
        app.setApplyDate(null);
        tApplicationDb.update(app);
        //戻る処理
        return back();
    }
    
    /**
     * 戻る処理
     * @return 
     */
    public String back() {
        //フラッシュに承認者IDを設定
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.put("approveId", approveId);
        //未承認一覧画面に遷移
        return "unapproved.xhtml?faces-redirect=true";
    }
}
