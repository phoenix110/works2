package com.cernol.works.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import java.util.List;
import javax.persistence.OneToMany;

@NamePattern("%s|documentNo")
@Table(name = "WORKS_PRICE_UPDATE")
@Entity(name = "works$PriceUpdate")
public class PriceUpdate extends StandardEntity {
    private static final long serialVersionUID = 2070381575640836164L;

    @Column(name = "DOCUMENT_NO", nullable = false, unique = true)
    protected String documentNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DOCUMENT_ON", nullable = false)
    protected Date documentOn;

    @Column(name = "CURRENT_STATUS", nullable = false)
    protected String currentStatus;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "priceUpdate")
    protected List<PriceUpdateItem> priceUpdateItems;

    public void setCurrentStatus(DocumentStatus currentStatus) {
        this.currentStatus = currentStatus == null ? null : currentStatus.getId();
    }

    public DocumentStatus getCurrentStatus() {
        return currentStatus == null ? null : DocumentStatus.fromId(currentStatus);
    }


    public void setPriceUpdateItems(List<PriceUpdateItem> priceUpdateItems) {
        this.priceUpdateItems = priceUpdateItems;
    }

    public List<PriceUpdateItem> getPriceUpdateItems() {
        return priceUpdateItems;
    }


    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentOn(Date documentOn) {
        this.documentOn = documentOn;
    }

    public Date getDocumentOn() {
        return documentOn;
    }


}