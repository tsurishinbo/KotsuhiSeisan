package bean;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import db.*;
import entity.*;
import util.*;

@Named
@RequestScoped
public class IndexBean extends SuperBean {
    
    private Integer empNo;      //社員番号
    private String password;    //パスワード

    @EJB
    private MEmployeeDb mEmployeeDb;
    
    /**
     * ログイン処理
     * @return 
     */
    public String login() {
        MEmployee employee = mEmployeeDb.getEmployee(empNo, password);
        if (employee != null) {
            //認証OK
            auth.login(
                employee.getId(),
                employee.getEmployeeName(),
                employee.getBossId(),
                employee.getManager()
            );
            //トップ画面に遷移
            return "top.xhtml?faces-redirect=true";
        } else {
            return null;
        }
    }

    /**
     * 社員番号の取得
     * @return 
     */
    public Integer getEmpNo() {
        return empNo;
    }

    /**
     * 社員番号の設定
     * @param empNo 
     */
    public void setEmpNo(Integer empNo) {
        this.empNo = empNo;
    }

    /**
     * パスワードの取得
     * @return 
     */
    public String getPassword() {
        return password;
    }

    /**
     * パスワードの設定
     * @param password 
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
