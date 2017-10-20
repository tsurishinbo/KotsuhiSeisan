package bean;

import dao.TApplicationDao;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import entity.*;
import java.util.Date;
import util.*;

/**
 * 申請承認画面バッキングビーン
 */
@Named
@ViewScoped
public class ApproveBean extends SuperBean implements Serializable {
    
    private Integer approveId;  // 承認者ID
    private TApplication app;   // 承認する申請
    
    @EJB
    private TApplicationDao tApplicationDao;
    
    /**
     * 初期処理
     */
    @PostConstruct
    public void init() {
        // フラッシュから情報を取得
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        approveId = (Integer)flash.get("approveId");
        // 承認する申請を取得
        app = tApplicationDao.findApplicationById((Integer)flash.get("detailId"));
    }
    
    /**
     * 承認処理
     * @return 次画面のURL
     */
    public String approve() {
        // 申請承認
        app.setStatus(3);
        app.setApproveId(auth.getEmpId());
        app.setApproveDate(new Date());
        tApplicationDao.update(app);
        // 戻る処理
        return back();
    }
    
    /**
     * 差戻し処理
     * @return 次画面のURL
     */
    public String reject() {
        // 申請差戻し
        app.setStatus(4);
        app.setApplyDate(null);
        tApplicationDao.update(app);
        // 戻る処理
        return back();
    }
    
    /**
     * 戻る処理
     * @return 次画面のURL
     */
    public String back() {
        // フラッシュに承認者IDを設定
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.put("approveId", approveId);
        // 未承認一覧画面に遷移
        return "unapproved.xhtml?faces-redirect=true";
    }

    public TApplication getApp() {
        return app;
    }
}
