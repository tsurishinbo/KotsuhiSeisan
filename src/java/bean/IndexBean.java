package bean;

import javax.ejb.EJB;
import javax.inject.Named;
import db.*;
import entity.*;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import util.*;

/**
 * ログイン画面バッキングビーン
 */
@Named
@ViewScoped
public class IndexBean extends SuperBean implements Serializable {
    
    private Integer empNo;      // 社員番号
    private String password;    // パスワード
    private String message;     // メッセージ
    
    @EJB
    private MEmployeeDb mEmployeeDb;
    
    /**
     * ログイン処理
     * @return 次画面のURL
     */
    public String login() {
        MEmployee employee = mEmployeeDb.getEmployee(empNo, password);
        if (employee != null) {
            // 認証OK
            auth.login(
                employee.getId(),
                employee.getEmployeeName(),
                employee.getBossId(),
                employee.getManager()
            );
            // トップ画面に遷移
            return "top.xhtml?faces-redirect=true";
        } else {
            message = "社員番号またはパスワードが違います。";
            return null;
        }
    }

    public Integer getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Integer empNo) {
        this.empNo = empNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessage() {
        return message;
    }
}
