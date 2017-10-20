package util;

import java.io.Serializable;
import javax.inject.Inject;

/**
 * バッキングビーンの基底クラス
 */
public abstract class SuperBean implements Serializable {
    
    /**
     * 認証情報
     */
    @Inject
    protected Auth auth;

    /**
     * 認証済か確認する
     * @return 認証していない場合はログイン画面のURL
     */
    public String checkAuthenticated() {
        if (!auth.isAuthenticated()) {
            return "index.xhtml?faces-redirect=true";
        }
        return null;
    }
    
    /**
     * ログアウトする
     * @return ログイン画面のURL
     */
    public String logout() {
        auth.logout();
        return "index.xhtml?faces-redirect=true";
    }

    public Integer getLoginId() {
        return auth.getEmpId();
    }
    
    public String getLoginName() {
        return auth.getEmpName();
    }

    public Integer getBossId() {
        return auth.getBossId();
    }
    
    public Integer getLoginManager() {
        return auth.getManager();
    }
}
