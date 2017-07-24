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
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|stockItem")
@Table(name = "WORKS_PRICE_UPDATE_ITEM")
@Entity(name = "works$PriceUpdateItem")
public class PriceUpdateItem extends StandardEntity {
    private static final long serialVersionUID = -4723866986334440097L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRICE_UPDATE_ID")
    protected PriceUpdate priceUpdate;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "STOCK_ITEM_ID")
    protected StockItem stockItem;

    @MetaProperty(datatype = CurrencyDatatype.NAME, mandatory = true)
    @Column(name = "PRICE", nullable = false)
    protected BigDecimal price;

    public void setPriceUpdate(PriceUpdate priceUpdate) {
        this.priceUpdate = priceUpdate;
    }

    public PriceUpdate getPriceUpdate() {
        return priceUpdate;
    }

    public void setStockItem(StockItem stockItem) {
        this.stockItem = stockItem;
    }

    public StockItem getStockItem() {
        return stockItem;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }


}