package bean;

import java.util.Map;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import db.*;
import entity.*;
import util.*;

/**
 * 申請作成画面バッキングビーン
 */
@Named
@ViewScoped
public class MakeBean extends SuperBean implements Serializable {

    private final Map<String, Integer> meansItems = new LinkedHashMap<>();  // 交通手段リスト
    private final Map<String, String> orderItems = new LinkedHashMap<>();   // 作業コードリスト
    private final List<Entry> entries = new ArrayList<>();                  // 申請明細リスト
    private Integer editId = null;  // 編集申請ID                
    
    @EJB
    private MMeansDb mMeansDb;
    @EJB
    private MOrderDb mOrderDb;
    @EJB
    private TApplicationDb tApplicationDb;
    
    /**
     * 初期処理
     */
    @PostConstruct
    public void init() {
        // リストの作成
        makeMeansItems();
        makeOrderItems();
        // フラッシュから情報を取得
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        if (flash.size() == 0) {
            // メニューから遷移：空の申請明細を１件追加
            entries.add(new Entry());
        } else {
            // 申請照会画面から遷移：画面に編集対象の申請内容を表示
            editId = (Integer)flash.get("editId");
            edit(editId);
        }
    }
    
    /**
     * 申請明細の追加
     * @return 次画面のURL
     */
    public String addEntry() {
        entries.add(new Entry());
        return null;
    }

    /**
     * 申請明細の削除
     * @param entry 申請情報
     * @return 次画面のURL
     */
    public String removeEntry(Entry entry) {
        entry.setDeleted(true);
        return null;
    }
    
    /**
     * 保存処理
     * @return 次画面のURL
     */
    public String save() {
        if (editId == null) {
            // 新規保存
            newSave();
        } else {
            // 既存データの保存
            updateSave();
        }
        // トップ画面に遷移
        return "top.xhtml?faces-redirect=true";
    }

    /**
     * 申請処理
     * @return 次画面のURL
     */
    public String apply() {
        if (editId == null) {
            // 新規申請
            newApply();
        } else {
            // 既存データの申請
            updateApply();
        }
        // トップ画面に遷移
        return "top.xhtml?faces-redirect=true";
    }
    
    /**
     * 新規保存処理
     */
    private void newSave() {
        int sortNo = 1;
        Long totalFare = 0L;
        List<TLine> lines = new ArrayList<>();
        for (Entry entry : entries) {
            if (!entry.isDeleted()) {
                TLine line = new TLine();
                lines.add(setLine(line, entry, sortNo));
                totalFare += entry.getFare();
                sortNo += 1;
            }
        }
        TApplication app = new TApplication();
        app.setStatus(1);
        app.setApplyId(auth.getEmpId());
        app.setApproveId(auth.getBossId());
        app.setTotalFare(totalFare);
        app.setLines(lines);
        tApplicationDb.insert(app);
    }
    
    /**
     * 新規申請処理
     */
    private void newApply() {
        int sortNo = 1;
        Long totalFare = 0L;
        List<TLine> lines = new ArrayList<>();
        for (Entry entry : entries) {
            if (!entry.isDeleted()) {
                TLine line = new TLine();
                lines.add(setLine(line, entry, sortNo));
                totalFare += entry.getFare();
                sortNo += 1;
            }
        }
        TApplication app = new TApplication();
        app.setStatus(2);
        app.setApplyId(auth.getEmpId());
        app.setApplyDate(new Date());
        app.setApproveId(auth.getBossId());
        app.setTotalFare(totalFare);
        app.setLines(lines);
        tApplicationDb.insert(app);
    }
   
    /**
     * 既存データの保存処理
     */
    private void updateSave() {
        List<TLine> addLines = new ArrayList<>();
        List<TLine> updLines = new ArrayList<>();
        List<TLine> delLines = new ArrayList<>(); 
        
        TApplication app = tApplicationDb.findApplicationById(editId);
        int sortNo = 1;
        Long totalFare = 0L;
        for (Entry entry : entries) {
            if (entry.getId() == null && !entry.isDeleted()) {
                //新規
                TLine addLine = new TLine();
                setLine(addLine, entry, sortNo);
                addLine.setApplicationId(editId);
                addLines.add(addLine);
                totalFare += addLine.getFare();
                sortNo += 1;
            }
            if (entry.getId() != null && !entry.isDeleted()) {
                //更新
                TLine updLine = tApplicationDb.findLineById(entry.getId());
                setLine(updLine, entry, sortNo);
                updLines.add(updLine);
                totalFare += updLine.getFare();
                sortNo += 1;
            }
            if (entry.getId() != null && entry.isDeleted()) {
                //削除
                TLine delLine = tApplicationDb.findLineById(entry.getId());
                delLines.add(delLine);
            }
        }
        app.setTotalFare(totalFare);
        tApplicationDb.insertAndUpdateAndDelete(app, addLines, updLines, delLines);
    }
    
    /**
     * 既存データの申請処理
     */
    private void updateApply() {
        List<TLine> addLines = new ArrayList<>();
        List<TLine> updLines = new ArrayList<>();
        List<TLine> delLines = new ArrayList<>(); 
        
        TApplication app = tApplicationDb.findApplicationById(editId);
        int sortNo = 1;
        Long totalFare = 0L;
        for (Entry entry : entries) {
            if (entry.getId() == null && !entry.isDeleted()) {
                //新規
                TLine addLine = new TLine();
                setLine(addLine, entry, sortNo);
                addLine.setApplicationId(editId);
                addLines.add(addLine);
                totalFare += addLine.getFare();
                sortNo += 1;
            }
            if (entry.getId() != null && !entry.isDeleted()) {
                //更新
                TLine updLine = tApplicationDb.findLineById(entry.getId());
                setLine(updLine, entry, sortNo);
                updLines.add(updLine);
                totalFare += updLine.getFare();
                sortNo += 1;
            }
            if (entry.getId() != null && entry.isDeleted()) {
                //削除
                TLine delLine = tApplicationDb.findLineById(entry.getId());
                delLines.add(delLine);
            }
        }
        app.setStatus(2);
        app.setApplyDate(new Date());
        app.setTotalFare(totalFare);
        tApplicationDb.insertAndUpdateAndDelete(app, addLines, updLines, delLines);
    }
    
    /**
     * 入力された申請情報を申請明細エンティティに設定する
     * @param line      申請明細エンティティ
     * @param entry     入力された申請情報
     * @param sortNo    ソート番号
     * @return 申請明細エンティティ
     */
    private TLine setLine(TLine line, Entry entry, int sortNo) {
        line.setId(entry.getId());
        line.setUsedDate(entry.getUsedDate());
        line.setOrderId(entry.getOrderId());
        line.setPlace(entry.getPlace());
        line.setPurpose(entry.getPurpose());
        line.setMeansId(entry.getMeansId());
        line.setSectionFrom(entry.getSectionFrom());
        line.setSectionTo(entry.getSectionTo());
        line.setIsRoundtrip((entry.getIsRoundTrip() ? 1 : 0));
        line.setFare(entry.getFare());
        line.setMemo(entry.getMemo());
        line.setSortNo(sortNo);
        return line;
    }
    
    /**
     * 申請エンティティの内容を編集用の申請情報に設定する
     * @param id 申請ID
     */
    private void edit(Integer id) {
        TApplication app = tApplicationDb.findApplicationById(id);
        for (TLine line : app.getLines()) {
            Entry entry = new Entry();
            entry.setId(line.getId());
            entry.setUsedDate(line.getUsedDate());
            entry.setOrderId(line.getOrderId());
            entry.setPlace(line.getPlace());
            entry.setPurpose(line.getPurpose());
            entry.setMeansId(line.getMeansId());
            entry.setSectionFrom(line.getSectionFrom());
            entry.setSectionTo(line.getSectionTo());
            entry.setIsRoundTrip((line.getIsRoundtrip()==1));
            entry.setFare(line.getFare());
            entry.setMemo(line.getMemo());
            entries.add(entry);
        }
    }

    /**
     * 交通手段リストを作成する
     */
    private void makeMeansItems() {
        List<MMeans> meansList = mMeansDb.findAll();
        for (MMeans means : meansList) {
            meansItems.put(means.getMeans(), means.getId());
        }
    }

    /**
     * 作業コードリストを作成する
     */
    private void makeOrderItems() {
        List<MOrder> orderList = mOrderDb.findAll();
        for (MOrder order : orderList) {
            orderItems.put(order.getId() + ":" + order.getOrderName(), order.getId());
        }
    }
    
    public Map<String, Integer> getMeansItems() {
        return meansItems;
    }

    public Map<String, String> getOrderItems() {
        return orderItems;
    }

    public List<Entry> getEntries() {
        return entries;
    }
}
