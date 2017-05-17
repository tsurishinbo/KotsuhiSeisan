package util;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

public class Entry implements Serializable {

    private Integer id;
    private Date usedDate;
    private String orderId;
    private String place;
    private String purpose;
    private Integer meansId;
    private String sectionFrom;
    private String sectionTo;
    private Boolean isRoundTrip;
    private Long fare;
    private String memo;
    private boolean deleted = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Date getUsedDate() {
        return usedDate;
    }

    public void setUsedDate(Date usedDate) {
        this.usedDate = usedDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Integer getMeansId() {
        return meansId;
    }

    public void setMeansId(Integer meansId) {
        this.meansId = meansId;
    }

    public String getSectionFrom() {
        return sectionFrom;
    }

    public void setSectionFrom(String sectionFrom) {
        this.sectionFrom = sectionFrom;
    }

    public String getSectionTo() {
        return sectionTo;
    }

    public void setSectionTo(String sectionTo) {
        this.sectionTo = sectionTo;
    }

    public Boolean getIsRoundTrip() {
        return isRoundTrip;
    }

    public void setIsRoundTrip(Boolean isRoundTrip) {
        this.isRoundTrip = isRoundTrip;
    }

    public Long getFare() {
        return fare;
    }

    public void setFare(Long fare) {
        this.fare = fare;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
