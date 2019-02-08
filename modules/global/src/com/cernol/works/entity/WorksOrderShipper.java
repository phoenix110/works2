package com.cernol.works.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.chile.core.annotations.MetaProperty;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.Transient;

@NamePattern("%s|packing")
@Table(name = "WORKS_WORKS_ORDER_SHIPPER")
@Entity(name = "works$WorksOrderShipper")
public class WorksOrderShipper extends StandardEntity {
    private static final long serialVersionUID = 5304594572531776912L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "WORKS_ORDER_ID")
    protected WorksOrder worksOrder;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PACKING_ID")
    protected Packing packing;

    @NotNull
    @Column(name = "QUANTITY", nullable = false)
    protected BigDecimal quantity = BigDecimal.ZERO;

    @NotNull
    @MetaProperty(datatype = "currency", mandatory = true)
    @Column(name = "UNIT_COST", nullable = false)
    protected BigDecimal unitCost = BigDecimal.ZERO;

    @Transient
    @MetaProperty(datatype = "currency", related = {"packing","quantity", "unitCost"})
    protected BigDecimal lineCost = BigDecimal.ZERO;

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public BigDecimal getLineCost() {
        if (getPacking() == null) {
            return BigDecimal.ZERO;
        }
        return getUnitCost().multiply(getQuantity());
    }

    public void setWorksOrder(WorksOrder worksOrder) {
        this.worksOrder = worksOrder;
    }

    public WorksOrder getWorksOrder() {
        return worksOrder;
    }

    public void setPacking(Packing packing) {
        this.packing = packing;
    }

    public Packing getPacking() {
        return packing;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

}