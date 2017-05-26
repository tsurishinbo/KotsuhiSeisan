package bean;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import db.*;
import entity.*;
import javax.annotation.PostConstruct;
import util.*;

@Named
@ViewScoped
public class ApproveBean extends SuperBean implements Serializable {
    
    private TApplication app;   //承認する申請
    
    @PostConstruct
    public void init() {
        
    }
    
    /**
     * 承認処理
     * @return 
     */
    public String approve() {
        return null;
    }
    
    /**
     * 差戻し処理
     * @return 
     */
    public String reject() {
        return null;
    }
    
    /**
     * 戻る処理
     * @return 
     */
    public String back() {
        return null;
    }
}
