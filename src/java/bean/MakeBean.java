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
import util.*;

@Named
@ViewScoped
public class MakeBean extends SuperBean implements Serializable {

    private final Map<String, Integer> meansItems = new LinkedHashMap<>();;
    private final Map<String, String> orderItems = new LinkedHashMap<>();
    private final List<Entry> entries = new ArrayList<>();
    
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
        entries.add(new Entry());
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
        tApplicationDb.save(auth.getEmpId(), auth.getBossId(), entries);
        return "top.xhtml?faces-redirect=true";
    }
    
    public String apply() {
        
        return "top.xhtml?faces-redirect=true";
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
    
    private List<Entry> pickAliveEntries() {
        List<Entry> picked = new ArrayList<>();
        for (Entry entry : entries) {
            if (!entry.isDeleted()) {
                picked.add(entry);
            }
        }
        return picked;
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
