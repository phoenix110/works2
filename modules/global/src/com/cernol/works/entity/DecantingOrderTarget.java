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

@NamePattern("%s|container")
@Table(name = "WORKS_DECANTING_ORDER_TARGET")
@Entity(name = "works$DecantingOrderTarget")
public class DecantingOrderTarget extends StandardEntity {
    private static final long serialVersionUID = 2685012680270912679L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DECANTING_ORDER_ID")
    protected DecantingOrder decantingOrder;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CONTAINER_ID")
    protected Container container;

    @MetaProperty(datatype = CurrencyDatatype.NAME, mandatory = true)
    @Column(name = "UNIT_COST", nullable = false)
    protected BigDecimal unitCost = BigDecimal.ZERO;

    @Column(name = "QUANTITY", nullable = false)
    protected Integer quantity = 0;

    @Column(name = "CUSTOMERS_OWN")
    protected Boolean customersOwn = Boolean.FALSE;

    @Column(name = "ADDITIONAL")
    protected Boolean additional = Boolean.FALSE;

    @Column(name = "LINE_CAPACITY")
    protected BigDecimal lineCapacity;

    @MetaProperty(datatype = CurrencyDatatype.NAME)
    @Column(name = "LINE_COST")
    protected BigDecimal lineCost;

    public BigDecimal getLineCapacity() {
        if (getContainer() == null) {
            return BigDecimal.ZERO;
        }

        if (getAdditional()) {
            return BigDecimal.ZERO;

        } else {
            return getContainer().getCapacity().multiply(BigDecimal.valueOf(getQuantity()));
        }
    }

    public BigDecimal getLineCost() {
        if (getContainer() == null) {
            return BigDecimal.ZERO;
        }

        if (getCustomersOwn()) {
            return BigDecimal.ZERO;
        } else {
            return getUnitCost().multiply(BigDecimal.valueOf(getQuantity()));
        }
    }

    public void setCustomersOwn(Boolean customersOwn) {
        this.customersOwn = customersOwn;
    }

    public Boolean getCustomersOwn() {
        return customersOwn;
    }

    public void setAdditional(Boolean additional) {
        this.additional = additional;
    }

    public Boolean getAdditional() {
        return additional;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }


    public void setDecantingOrder(DecantingOrder decantingOrder) {
        this.decantingOrder = decantingOrder;
    }

    public DecantingOrder getDecantingOrder() {
        return decantingOrder;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public Container getContainer() {
        return container;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }


}