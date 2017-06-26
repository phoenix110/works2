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
@Table(name = "WORKS_STOCK_COUNT")
@Entity(name = "works$StockCount")
public class StockCount extends StandardEntity {
    private static final long serialVersionUID = 3173735794136665986L;

    @Column(name = "DOCUMENT_NO", nullable = false, unique = true)
    protected String documentNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DOCUMENT_ON", nullable = false)
    protected Date documentOn;

    @Column(name = "DESCRIPTION")
    protected String description;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "stockCount")
    protected List<StockCountItem> stockCountItems;

    public void setStockCountItems(List<StockCountItem> stockCountItems) {
        this.stockCountItems = stockCountItems;
    }

    public List<StockCountItem> getStockCountItems() {
        return stockCountItems;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }



}