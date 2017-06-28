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

@NamePattern("%s|stockItem")
@Table(name = "WORKS_STOCK_INTAKE_ITEM")
@Entity(name = "works$StockIntakeItem")
public class StockIntakeItem extends StandardEntity {
    private static final long serialVersionUID = 5436061985252812469L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "STOCK_INTAKE_ID")
    protected StockIntake stockIntake;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "STOCK_ITEM_ID")
    protected StockItem stockItem;

    @Column(name = "QUANTITY", nullable = false)
    protected BigDecimal quantity;

    @MetaProperty(datatype = CurrencyDatatype.NAME, mandatory = true)
    @Column(name = "UNIT_PRICE", nullable = false)
    protected BigDecimal unitPrice;

    @Transient
    @MetaProperty(datatype = CurrencyDatatype.NAME, related = {"quantity", "unitPrice"})
    protected BigDecimal lineValue;

    public void setStockIntake(StockIntake stockIntake) {
        this.stockIntake = stockIntake;
    }

    public StockIntake getStockIntake() {
        return stockIntake;
    }

    public void setStockItem(StockItem stockItem) {
        this.stockItem = stockItem;
    }

    public StockItem getStockItem() {
        return stockItem;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public BigDecimal getLineValue() {

        return getUnitPrice().multiply(getQuantity());
    }


}