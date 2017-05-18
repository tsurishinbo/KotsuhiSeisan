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
import java.util.Date;
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
            newSave();
        } else {
            updateSave();
        }
        return "top.xhtml?faces-redirect=true";
    }

    public String apply() {
        if (editId == null) {
            newApply();
        } else {
            updateApply();
        }
        return "top.xhtml?faces-redirect=true";
    }
    
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
        tApplicationDb.make(app);
    }
    
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
        tApplicationDb.make(app);
    }
   
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
                TLine line = new TLine();
                addLines.add(setLine(line, entry, sortNo));
                totalFare += line.getFare();
                sortNo += 1;
            }
            if (entry.getId() != null && !entry.isDeleted()) {
                //更新
                TLine line = tApplicationDb.findLineById(entry.getId());
                updLines.add(setLine(line, entry, sortNo));
                totalFare += line.getFare();
                sortNo += 1;
            }
            if (entry.getId() != null && entry.isDeleted()) {
                //削除
                TLine line = tApplicationDb.findLineById(entry.getId());
                delLines.add(line);
            }
        }
        app.setTotalFare(totalFare);
        tApplicationDb.update(app, addLines, updLines, delLines);
    }
    
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
                TLine line = new TLine();
                addLines.add(setLine(line, entry, sortNo));
                totalFare += line.getFare();
                sortNo += 1;
            }
            if (entry.getId() != null && !entry.isDeleted()) {
                //更新
                TLine line = tApplicationDb.findLineById(entry.getId());
                updLines.add(setLine(line, entry, sortNo));
                totalFare += line.getFare();
                sortNo += 1;
            }
            if (entry.getId() != null && entry.isDeleted()) {
                //削除
                TLine line = tApplicationDb.findLineById(entry.getId());
                delLines.add(line);
            }
        }
        app.setStatus(2);
        app.setApplyDate(new Date());
        app.setTotalFare(totalFare);
        tApplicationDb.update(app, addLines, updLines, delLines);
    }
    
    
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
