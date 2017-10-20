package util;

import java.io.Serializable;
import java.util.Date;

/**
 * 申請情報クラス
 */
public class Entry implements Serializable {

    private Integer id;             // 申請ID
    private Date usedDate;          // 利用日
    private String orderId;         // 作業コード
    private String place;           // 出張場所
    private String purpose;         // 出張目的
    private Integer meansId;        // 交通手段ID
    private String sectionFrom;     // 出発地
    private String sectionTo;       // 到着地
    private Boolean isRoundTrip;    // 往復フラグ(0:片道 1:往復)
    private Long fare;              // 料金
    private String memo;            // 備考
    private boolean deleted = false;// 削除フラグ(true:削除)

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
