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

@NamePattern("%s %s|supplier,documentOn")
@Table(name = "WORKS_STOCK_INTAKE")
@Entity(name = "works$StockIntake")
public class StockIntake extends StandardEntity {
    private static final long serialVersionUID = -5891539656207211115L;

    @Column(name = "SUPPLIER", nullable = false)
    protected String supplier;

    @Column(name = "DOCUMENT_NO", nullable = false)
    protected String documentNo;

    @Temporal(TemporalType.DATE)
    @Column(name = "DOCUMENT_ON", nullable = false)
    protected Date documentOn;

    @Column(name = "CURRENT_STATUS", nullable = false)
    protected String currentStatus;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "stockIntake")
    protected List<StockIntakeItem> stockIntakeItems;

    public void setStockIntakeItems(List<StockIntakeItem> stockIntakeItems) {
        this.stockIntakeItems = stockIntakeItems;
    }

    public List<StockIntakeItem> getStockIntakeItems() {
        return stockIntakeItems;
    }


    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getSupplier() {
        return supplier;
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

    public void setCurrentStatus(DocumentStatus currentStatus) {
        this.currentStatus = currentStatus == null ? null : currentStatus.getId();
    }

    public DocumentStatus getCurrentStatus() {
        return currentStatus == null ? null : DocumentStatus.fromId(currentStatus);
    }


}