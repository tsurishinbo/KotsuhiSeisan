package bean;

import java.util.Map;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import db.*;
import entity.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import util.*;

@Named
@ViewScoped
public class MakeBean extends SuperBean implements Serializable {

    private final Map<String, Integer> meansItems = new LinkedHashMap<>();;
    private final Map<String, String> orderItems = new LinkedHashMap<>();
    private final List<Entry> entries = new ArrayList<>();
    private Integer editId = null;
    
    @EJB
    private MMeansDb mMeansDb;
    @EJB
    private MOrderDb mOrderDb;
    @EJB
    private TApplicationDb tApplicationDb;
    @EJB
    private TLineDb tLineDb;
    
    @PostConstruct
    public void init() {
        setMeansItems();
        setOrderItems();
        
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        if (flash.get("editId") != null) {
            editId = (Integer)flash.get("editId");
            flash.put("editId", null);
            edit(editId);
        } else {
            entries.add(new Entry());
        }
    }
    
    public String addEntry() {
        entries.add(new Entry());
        return null;
    }
    
    public String removeEntry(Entry entry) {
        entry.setDeleted(true);
        return null;
    }
    
    public String save() {
        if (editId == null) {
            saveNew();
        } else {
            saveEdit();
        }
        return "top.xhtml?faces-redirect=true";
    }

    public String apply() {
        if (editId == null) {
            applyNew();
        } else {
            applyEdit();
        }
        return "top.xhtml?faces-redirect=true";
    }
    
    private void saveNew() {
        List<TLine> lines = new ArrayList<>();
        Long totalFare = 0L;
        for (Entry entry : entries) {
            if (!entry.isDeleted()) {
                TLine line = new TLine();
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
                lines.add(line);
                totalFare += entry.getFare();
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
    
    private void saveEdit() {
        
    }

    private void applyNew() {
        
    }
    
    private void applyEdit() {
        
    }
    
    private void editLine() {
        for (Entry entry : entries) {
            if (entry.getId() == null) {
                if (!entry.isDeleted()) {
                    //新規
                    insertLine(entry);
                }
            } else {
                if (!entry.isDeleted()) {
                    //更新
                    updateLine(entry);
                } else {
                    //削除
                    deleteLine(entry);
                }
            }
        }
    }
    
    private void insertLine(Entry entry) {
        
        
    }
    
    private void updateLine(Entry entry) {
        
    }
        
    private void deleteLine(Entry entry) {
        
    }
    
    private void edit(Integer id) {
        TApplication app = tApplicationDb.findById(id);
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

    private void setMeansItems() {
        List<MMeans> meansList = mMeansDb.findAll();
        for (MMeans means : meansList) {
            meansItems.put(means.getMeans(), means.getId());
        }
    }
    
    private void setOrderItems() {
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
