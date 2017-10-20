package util;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;

/**
 * 認証情報クラス
 */
@SessionScoped
public class Auth implements Serializable {

    private Integer empId;          // 社員番号
    private String empName;         // 氏名
    private Integer bossId;         // 上司の社員番号
    private Integer manager;        // 管理職フラグ(0:一般職 1:管理職)
    private boolean authenticated;  // 認証フラグ(true:認証済 false:未認証)

    /**
     * ログインする
     * @param empId     社員番号
     * @param empName   氏名
     * @param bossId    上司の社員番号
     * @param manager   管理職フラグ
     */
    public void login(Integer empId, String empName, Integer bossId, Integer manager) {
        this.empId = empId;
        this.empName = empName;
        this.bossId = bossId;
        this.manager = manager;
        this.authenticated = true;
    }

    /**
     * ログアウトする
     */
    public void logout() {
        this.empId = null;
        this.empName = null;
        this.bossId = null;
        this.manager = null;
        this.authenticated = false;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Integer getBossId() {
        return bossId;
    }

    public void setBossId(Integer bossId) {
        this.bossId = bossId;
    }

    public Integer getManager() {
        return manager;
    }

    public void setManager(Integer manager) {
        this.manager = manager;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}
