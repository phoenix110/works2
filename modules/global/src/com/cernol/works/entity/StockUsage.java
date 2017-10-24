package com.cernol.works.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.chile.core.annotations.MetaProperty;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s %s|stockItem,usedOn")
@Table(name = "WORKS_STOCK_USAGE")
@Entity(name = "works$StockUsage")
public class StockUsage extends StandardEntity {
    private static final long serialVersionUID = -2785258553216641222L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "STOCK_ITEM_ID")
    protected StockItem stockItem;

    @Temporal(TemporalType.DATE)
    @Column(name = "USED_ON", nullable = false)
    protected Date usedOn;

    @MetaProperty(datatype = PartsPer100Datatype.NAME, mandatory = true)
    @Column(name = "QUANTITY", nullable = false)
    protected BigDecimal quantity;

    public void setStockItem(StockItem stockItem) {
        this.stockItem = stockItem;
    }

    public StockItem getStockItem() {
        return stockItem;
    }

    public void setUsedOn(Date usedOn) {
        this.usedOn = usedOn;
    }

    public Date getUsedOn() {
        return usedOn;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }


}