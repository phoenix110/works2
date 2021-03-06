package com.cernol.works.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.chile.core.annotations.MetaProperty;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|rawMaterial")
@Table(name = "WORKS_SALES_ORDER_RAW_MATERIAL")
@Entity(name = "works$SalesOrderRawMaterial")
public class SalesOrderRawMaterial extends StandardEntity {
    private static final long serialVersionUID = 5210277391893266601L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SALES_ORDER_ID")
    protected SalesOrder salesOrder;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "RAW_MATERIAL_ID")
    protected RawMaterial rawMaterial;

    @MetaProperty(datatype = CurrencyDatatype.NAME, mandatory = true)
    @Column(name = "UNIT_PRICE", nullable = false)
    protected BigDecimal unitPrice = BigDecimal.ZERO;

    @Column(name = "QUANTITY", nullable = false)
    protected BigDecimal quantity = BigDecimal.ZERO;

    @Transient
    @MetaProperty(datatype = CurrencyDatatype.NAME, related = {"unitPrice", "quantity"})
    protected BigDecimal linePrice;

    public void setSalesOrder(SalesOrder salesOrder) {
        this.salesOrder = salesOrder;
    }

    public SalesOrder getSalesOrder() {
        return salesOrder;
    }

    public void setRawMaterial(RawMaterial rawMaterial) {
        this.rawMaterial = rawMaterial;
    }

    public RawMaterial getRawMaterial() {
        return rawMaterial;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getLinePrice() {

        return getUnitPrice().multiply(getQuantity());
    }


}