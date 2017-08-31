package com.cernol.works.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.Listeners;

@NamePattern("%s|stockItem")
@Table(name = "WORKS_STOCK_COUNT_ITEM")
@Entity(name = "works$StockCountItem")
public class StockCountItem extends StandardEntity {
    private static final long serialVersionUID = -6270941026715504839L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "STOCK_COUNT_ID")
    protected StockCount stockCount;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "STOCK_ITEM_ID")
    protected StockItem stockItem;

    @MetaProperty(datatype = CurrencyDatatype.NAME, mandatory = true)
    @Column(name = "CURRENT_VALUE", nullable = false)
    protected BigDecimal currentValue = BigDecimal.ZERO;

    @MetaProperty(datatype = PartsPer100Datatype.NAME, mandatory = true)
    @Column(name = "COUNTED_QUANTITY", nullable = false)
    protected BigDecimal countedQuantity = BigDecimal.ZERO;

    @MetaProperty(datatype = PartsPer100Datatype.NAME, mandatory = true)
    @Column(name = "CURRENT_QUANTITY", nullable = false)
    protected BigDecimal currentQuantity = BigDecimal.ZERO;

    @Transient
    @MetaProperty(datatype = PartsPer100Datatype.NAME, related = {"countedQuantity", "currentQuantity"})
    protected BigDecimal adjustedQuantity;

    @Transient
    @MetaProperty(datatype = CurrencyDatatype.NAME, related = {"adjustedQuantity", "currentValue"})
    protected BigDecimal adjustedValue;


    public void setStockCount(StockCount stockCount) {
        this.stockCount = stockCount;
    }

    public StockCount getStockCount() {
        return stockCount;
    }


    public void setStockItem(StockItem stockItem) {
        this.stockItem = stockItem;
    }

    public StockItem getStockItem() {
        return stockItem;
    }

    public void setCurrentValue(BigDecimal currentValue) {
        this.currentValue = currentValue;
    }

    public BigDecimal getCurrentValue() {
        return currentValue;
    }

    public void setCountedQuantity(BigDecimal countedQuantity) {
        this.countedQuantity = countedQuantity;
    }

    public BigDecimal getCountedQuantity() {
        return countedQuantity;
    }

    public void setCurrentQuantity(BigDecimal currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public BigDecimal getCurrentQuantity() {
        return currentQuantity;
    }

    public BigDecimal getAdjustedQuantity() {

        return (getCountedQuantity().subtract(getCurrentQuantity()));
    }

    public void setAdjustedValue(BigDecimal adjustedValue) {
        this.adjustedValue = adjustedValue;
    }

    public BigDecimal getAdjustedValue() {

        return (getAdjustedQuantity().multiply(getCurrentValue()));
    }

}